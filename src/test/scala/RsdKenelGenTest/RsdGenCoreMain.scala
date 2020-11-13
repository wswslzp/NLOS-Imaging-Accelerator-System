package RsdKenelGenTest

import spinal.core.sim._
import spinal.lib._
import Config._
import Core.RsdGenCoreArray._
import Sim.RsdGenCoreArray.{Computation, LoadData}
import breeze.linalg.{DenseMatrix, DenseVector, fliplr}
import breeze.math.Complex
import breeze.plot._
import org.jfree.chart.axis._
import java.io._
import sys.process._

import SimTest.NlosSystemSimTest.write_image
import breeze.signal._

import scala.util.Random._
import scala.collection.mutable

// TODO: Not so accurate. Test it with fft2d.
object RsdGenCoreMain extends App{
  import Sim.SimComplex._
  import Sim.SimFix._
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv") //(
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse: DenseMatrix[Complex] = LoadData.loadComplexMatrix( //(R, radius)
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
  )
  println(s"wave:(${wave.rows}, ${wave.cols})")
  println(s"distance:(${distance.rows}, ${distance.cols})")
  println(s"timeshift:(${timeshift.rows}, ${timeshift.cols})")
  println(s"impulse:(${impulse.rows}, ${impulse.cols})")
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows
  )
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  println(s"coef_depth = ${coef.length}, coef_freq = ${coef.head.rows}, coef_rad = ${coef.head.cols}")
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  println(rsd_cfg.toString)
  val hardware_rsd = Array.fill(rsd.length, rsd.head.length){
    DenseVector.fill(rsd.head.head.length)(Complex(0d, 0d))
  }

  val impulse_queue = mutable.Queue[Complex]()
  val coef_queue = mutable.Queue[Complex]()
  val kernel_queue = mutable.Queue[Complex]()
  val coef_x_impu_queue = mutable.Queue[Complex]()

  val withWave = true
  val waveDepth = 1
  val module_compiled = if(withWave) {
    SimConfig.allOptimisation.withWave(waveDepth).workspacePath("tb").compile(RsdGenCore(rsd_cfg))
  }else{
    SimConfig.allOptimisation.workspacePath("tb").compile(RsdGenCore(rsd_cfg))
  }

  def testCase(R_id: Int, d_id: Int, f_id: Int, visible: Boolean = false): Figure = {
    def testBench(dut: RsdGenCore, R_id: Int = 0, d_id: Int = 0, f_id: Int = 0): Unit = {
      dut.clockDomain.forkStimulus(2)
      dut.io.timeshift.valid #= false
      dut.io.distance.valid #= false
      dut.io.wave.valid #= false
      dut.io.ring_impulse.valid #= false

      dut.clockDomain.waitSampling()

      var distance_sent: Boolean = false
      forkJoin(
        // Driver & sequencer
        () => {
          dut.io.timeshift.payload #= timeshift(f_id, d_id) // df = (0, 0)
          dut.io.timeshift.valid #= true
          dut.clockDomain.waitSampling()
          dut.io.timeshift.valid #= false

          dut.io.distance.payload #= distance(f_id, d_id)
          dut.io.distance.valid #= true
          distance_sent = true
          dut.clockDomain.waitSampling()
          dut.io.distance.valid #= false
          distance_sent = false

          forkJoin(
            () => {
              for(r <- 0 until rsd_cfg.radius_factor) { // full r
                dut.io.wave.valid #= true
                dut.io.wave.payload #= wave(r, d_id)
                dut.clockDomain.waitSampling()
              }
              dut.io.wave.valid #= false
            }
            ,
            () => {
              dut.clockDomain.waitSampling(dut.prsd_core.W2CLatency)
              for(r <- 0 until rsd_cfg.radius_factor) { // full r
                dut.io.ring_impulse.valid #= true
                dut.io.ring_impulse.payload #= impulse(R_id, r) // first kernel R= 0
                dut.clockDomain.waitSampling()
              }
              dut.io.ring_impulse.valid #= false
            }
            ,
            () => {
              dut.clockDomain.waitSampling(dut.prsd_core.W2CLatency + 1)
              for(r <- 0 until rsd_cfg.radius_factor) { // full r
                // when coef come to 1e-4, it becomes less accuracy
                // But it should be OK for now
                // TODO: should we let machine configure coef automatically?
                //              println(s"true coef is ${coef(0)(0, r)}")
                //              println(s"current coef is ${dut.prsd_core.coef_gen_core.io.coef.toComplex}")
                coef_queue.enqueue(dut.prsd_core.coef_gen_core.prev_coef.toComplex)
                coef_x_impu_queue.enqueue(dut.prsd_core.delta_rsd_kernel_val.toComplex)
                dut.clockDomain.waitSampling()
              }
            }
          )

          dut.clockDomain.waitSampling(10)
          simSuccess()
        }
        ,
        // Monitor1: catch hardware kernel output
        () => {
          while(true) {
            dut.clockDomain.waitActiveEdgeWhere(dut.io.kernel.valid.toBoolean)
            hardware_rsd(d_id)(f_id)(R_id) = dut.io.kernel.payload.toComplex
//            println(s"hardware kernel is ${dut.io.kernel.payload.toComplex}")
          }
        }
        ,
        // Monitor2: catch hardware impulse input
        () => {
          while(true) {
            dut.clockDomain.waitActiveEdgeWhere(dut.io.ring_impulse.valid.toBoolean)
            impulse_queue.enqueue(dut.io.ring_impulse.payload.toComplex)
            //            println(s"delta_kernel = ${( dut.io.kernel.payload.toComplex - rsd(0)(0)(0) ).abs}")
          }
        }
        ,
        // Monitor3: catch hardware partial rsd kernel
        () => {
          dut.clockDomain.waitActiveEdgeWhere(dut.io.ring_impulse.valid.toBoolean)
          dut.clockDomain.waitSampling(2)
          for(_ <- 0 until rsd_cfg.radius_factor) {
            kernel_queue.enqueue(dut.io.kernel.payload.toComplex)
            dut.clockDomain.waitSampling()
          }
        }
      )
    }

    module_compiled.doSim("RsdGenCore_tb"){dut=>
      println("")
      testBench(dut, R_id, d_id, f_id)
    }

//    // TODO: ci is different from kernel_queue!!
//    //  Solution: given the hardware impulse the correct image part, than it will be same.
//    val coef_x_impu = Array.tabulate(rsd_cfg.radius_factor) {idx=> // idx = r
//      impulse_queue(idx) * coef_queue(idx)
//    }
//    val tcoef_x_impu = Array.tabulate(rsd_cfg.radius_factor) {idx=>
//      impulse_queue(idx) * coef(d_id)(f_id, idx)
//    }
//    val coef_x_timpu = Array.tabulate(rsd_cfg.radius_factor) {idx=>
//      impulse(R_id, idx) * coef_queue(idx)
//    }
//    val tcoef_x_timpu = Array.tabulate(rsd_cfg.radius_factor) {idx=>
//      impulse(R_id, idx) * coef(d_id)(f_id, idx)
//    }
//    def accmulatePRSD(xin: Array[Complex]): Array[Complex] = {
//      Array.tabulate(xin.length) {idx=>
//        var tmp = Complex(0, 0)
//        for(n <- 0 to idx) {
//          tmp += xin(n)
//        }
//        tmp
//      }
//    }
//    val ci_kernels = accmulatePRSD(coef_x_impu)
//    val tci_kernels = accmulatePRSD(tcoef_x_impu)
//    val cti_kernels = accmulatePRSD(coef_x_timpu)
//    val tcti_kernels = accmulatePRSD(tcoef_x_timpu)
//    val hard_ci_kernels = accmulatePRSD(coef_x_impu_queue.toArray)
//
    val f = Figure()
    f.visible = visible
//    val p1 = f.subplot(2, 3, 0)
//    val p2 = f.subplot(2, 3, 1)
//    val p5 = f.subplot(2, 3, 2)
//    val p3 = f.subplot(2, 3, 3)
//    val p4 = f.subplot(2, 3, 4)
//    val p6 = f.subplot(2, 3, 5)
//    p1 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(kernel_queue.toArray).map(_.real),
//      name = "hardware kernel"
//    )
//    p1 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(tcti_kernels).map(_.real),
//      name = "tcti_kernel"
//    )
//    p2 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(coef_queue.toArray).map(_.real),
//      name = "hardware coef"
//    )
//    p2 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = coef(d_id)(f_id, ::).t.map(_.real),
//      name = "true coef"
//    )
//    p3 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(kernel_queue.toArray).map(_.real),
//      name = "hardware kernels"
//    )
//    p3 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(hard_ci_kernels).map(_.real),
//      name = "hard_ci_kernels"
//    )
//    p4 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(coef_x_impu_queue.toArray).map(_.real),
//      name = "hardware coef_x_impu"
//    )
//    p4 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(coef_x_impu).map(_.real),
//      name = "coef_x_impu"
//    )
//    p5 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(ci_kernels).map(_.real),
//      name = "ci_kernels"
//    )
//    p5 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(tcti_kernels).map(_.real),
//      name = "tcti_kernels"
//    )
//    p6 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(ci_kernels).map(_.real),
//      name = "ci kernels"
//    )
//    p6 += plot(
//      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
//      y = DenseVector(hard_ci_kernels).map(_.real),
//      name = "hard_ci_kernels"
//    )
//    p1.legend = true
//    p1.xlabel = "accumulate time"
//    p1.ylabel = "kernel comparison"
//    p1.yaxis.setTickUnit(new NumberTickUnit(0.05))
//    p2.legend = true
//    p2.xlabel = "accumulate time"
//    p2.ylabel = "error"
//    p2.yaxis.setTickUnit(new NumberTickUnit(0.005))
//    p3.legend = true
//    p3.xlabel = "accumulate time"
//    p3.ylabel = "kernel comparison"
//    p3.yaxis.setTickUnit(new NumberTickUnit(0.05))
//    p4.legend = true
//    p4.xlabel = "accumulate time"
//    p4.ylabel = "prsd kernel"
//    p4.yaxis.setTickUnit(new NumberTickUnit(0.05))
//    p5.legend = true
//    p5.xlabel = "accumulate time"
//    p5.ylabel = "impulse"
//    p5.yaxis.setTickUnit(new NumberTickUnit(0.05))
//    p6.legend = true
//    p6.xlabel = "accumulate time"
//    p6.ylabel = "kernel comparison"
//    p6.yaxis.setTickUnit(new NumberTickUnit(0.05))
//
//    impulse_queue.clear()
//    coef_queue.clear()
//    kernel_queue.clear()
//    coef_x_impu_queue.clear()
//
    f

  }

  val uin = Array.tabulate(rsd_cfg.freq_factor){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/data/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/data/imag/uin_${idx+1}.csv"
    )
  }
  val uin_fft = uin.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  new File("tb/RsdGenCore/wave").mkdir()
  for{
    did <- 0 until rsd_cfg.depth_factor
    fid <- 0 until rsd_cfg.freq_factor
    len <- 0 until rsd_cfg.impulse_sample_point
  } {
    testCase(len, did, fid)
    Process("mv tb/RsdGenCore/*vcd tb/RsdGenCore/wave").run()
  }
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- 0 until rsd_cfg.freq_factor) {
      val rsd_kernel_rad = hardware_rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    iFourierTr(uout_f)
  }
  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
    var umax = 0d
    for(d <- 0 until rsd_cfg.depth_factor) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }
  println("Output image has been generated!")
  println(s"output size: cols = ${uout_abs_max.cols}")

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/RsdGenCore/nlos_out.jpg")


//  new File("tmp/fig2").mkdir()
//  for(f <- 0 until rsd_cfg.freq_factor) {
//      val fig = testCase(40, 30, f)
//      fig.saveas(s"tmp/fig2/res_R40_d30_f$f.png", dpi = 144)
//  }
//  new File("tmp/fig3").mkdir()
//  for(d <- 0 until rsd_cfg.depth_factor) {
//    val fig = testCase(40, d, 30)
//    fig.saveas(s"tmp/fig3/res_R40_d${d}_f30.png", dpi = 144)
//  }
}
