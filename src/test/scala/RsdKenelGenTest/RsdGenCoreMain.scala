package RsdKenelGenTest

import spinal.core.sim._
import spinal.lib._
import Config._
import Core.RsdGenCoreArray._
import Sim.RsdGenCoreArray.{Computation, LoadData}
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.math.Complex
import breeze.plot._
import org.jfree.chart.axis._
import java.io._

import scala.collection.mutable

// TODO: Not so accurate. Test it with fft2d.
//  coef are accurate for the first thirty part.
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

  val impulse_queue = mutable.Queue[Complex]()
  val coef_queue = mutable.Queue[Complex]()
  val kernel_queue = mutable.Queue[Complex]()

  val module_compiled = SimConfig.allOptimisation.workspacePath("tb").compile(RsdGenCore(rsd_cfg))

  def testCase(R_id: Int, d_id: Int, f_id: Int): Figure = {
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

//    println(s"true kernel = ${rsd(d_id)(f_id)(R_id)}")
    module_compiled.doSim("RsdGenCore_tb"){dut=>
      println("")
      testBench(dut, R_id, d_id, f_id)
    }
//    kernel_queue.zipWithIndex.foreach{ case (dat, idx) =>
//      println(s"Hardware kernel($idx) = ${dat.toString()}")
//    }
//    println("=======================")

    val coef_x_impu = Array.tabulate(impulse_queue.length) {idx=> // idx = r
      impulse_queue(idx) * coef_queue(idx)
    }
    val tcoef_x_impu = Array.tabulate(impulse_queue.length) {idx=>
      impulse_queue(idx) * coef(d_id)(f_id, idx)
    }
    val coef_x_timpu = Array.tabulate(impulse_queue.length) {idx=>
      impulse(R_id, idx) * coef_queue(idx)
    }
    val tcoef_x_timpu = Array.tabulate(impulse_queue.length) {idx=>
      impulse(R_id, idx) * coef(d_id)(f_id, idx)
    }
    def accmulatePRSD(xin: Array[Complex]): Array[Complex] = {
      Array.tabulate(xin.length) {idx=>
        var tmp = Complex(0, 0)
        for(n <- 0 to idx) {
          tmp += xin(n)
        }
        tmp
      }
    }
    val ci_kernels = accmulatePRSD(coef_x_impu)
    val tci_kernels = accmulatePRSD(tcoef_x_impu)
    val cti_kernels = accmulatePRSD(coef_x_timpu)
    val tcti_kernels = accmulatePRSD(tcoef_x_timpu)
//    tcti_kernels.zipWithIndex.foreach { case (complex, i) =>
//      println(s"True kernel($i) = $complex")
//    }

    val tcti_kernel_max = breeze.linalg.max(DenseVector(tcti_kernels.map(_.abs)))
//    println(s"tcti_max = $tcti_kernel_max")
    val coef_max = breeze.linalg.max(coef(d_id)(f_id, ::).t.map(_.abs))
//    println(s"coef_max = $coef_max")

    val delta_kernels = DenseVector.tabulate(tcti_kernels.length) {idx=>
      (tcti_kernels(idx) - kernel_queue(idx)).abs
    }
    val delta_coefs: DenseVector[Double] = DenseVector.tabulate(coef_queue.length) { idx=>
      ( coef(d_id)(f_id, idx) - coef_queue(idx) ).abs
    }
    val f = Figure()
    f.visible = false
    val p1 = f.subplot(2, 2, 0)
    val p2 = f.subplot(2, 2, 1)
    val p3 = f.subplot(2, 2, 2)
    val p4 = f.subplot(2, 2, 3)
    p1 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(kernel_queue.toArray).map(_.real),
      name = "ci_kernel"
    )
    p2 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(coef_queue.toArray).map(_.real),
      name = "hardware coef"
      //    y = DenseVector(tci_kernels).map(_.real),
      //    name = "tci_kernel"
    )
    p3 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(cti_kernels).map(_.real),
      name = "cti_kernel"
    )
    p1 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(tcti_kernels).map(_.real),
      name = "tcti_kernel"
    )
    p2 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = coef(d_id)(f_id, ::).t.map(_.real),
      name = "true coef"
      //    y = DenseVector(tcti_kernels).map(_.real),
      //    name = "tcti_kernel"
    )
    p3 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(tcti_kernels).map(_.real),
      name = "tcti_kernel"
    )
    p4 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(coef_x_impu).map(_.real),
      name = "coef_x_impu"
      //    y = DenseVector(impulse_queue.toArray.map(_.real)),
      //    name = "hard_impulse"
      //    y = DenseVector(coef_queue.toArray).map(_.real),
      //    name = "hard_coef"
    )
    p4 += plot(
      x = DenseVector(tcti_kernels.indices.toArray.map(_.toDouble)),
      y = DenseVector(tcoef_x_timpu).map(_.real),
      name = "tcoef_x_timpu"
      //    y = impulse(0, ::).t.map(_.real),
      //    name = "soft_impulse"
      //    y = coef(0)(0, ::).t.map(_.real),
      //    name = "soft_coef"
    )
    p1.legend = true
    p1.xlabel = "accumulate time"
    p1.ylabel = "kernel comparison"
    p1.yaxis.setTickUnit(new NumberTickUnit(0.005))
    p2.legend = true
    p2.xlabel = "accumulate time"
    p2.ylabel = "error"
    p2.yaxis.setTickUnit(new NumberTickUnit(0.005))
    p3.legend = true
    p3.xlabel = "accumulate time"
    p3.ylabel = "kernel comparison"
    p3.yaxis.setTickUnit(new NumberTickUnit(0.005))
    p4.legend = true
    p4.xlabel = "accumulate time"
    p4.ylabel = "impulse"
    p4.yaxis.setTickUnit(new NumberTickUnit(0.005))

    impulse_queue.clear()
    coef_queue.clear()
    kernel_queue.clear()

    f

  }

  for {
    did <- 0 until rsd_cfg.depth_factor/2
    fid <- 0 until rsd_cfg.freq_factor/2
  } {
    val dir = new File(s"tmp/fig/d${did}f${fid}")
    dir.mkdir()
    for(Rid <- 0 until rsd_cfg.impulse_sample_point) {
      val f = testCase(Rid, did, fid)

      f.saveas(s"tmp/fig/d${did}f${fid}/res_R${Rid}_d${did}_f${fid}.png")
    }
  }

}
