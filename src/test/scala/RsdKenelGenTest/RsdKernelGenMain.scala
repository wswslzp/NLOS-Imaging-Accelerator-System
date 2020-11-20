package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray._
import spinal.core._
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import Sim.SimFix._
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import spinal.core.sim._
import Config.RsdKernelConfig._
import breeze.signal._
import java.io._

import SimTest.NlosSystemSimTest.write_image

object RsdKernelGenMain extends App{
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)
  val hardware_rsd = Array.fill(rsd.length, rsd.head.length){
    DenseVector.fill(rsd.head.head.length)(Complex(0d, 0d))
  }

  val withWave = false
  val waveDepth = 1
  val module_compiled = if(withWave){
    SimConfig
      .withWave(waveDepth)
      .allOptimisation
      .workspacePath("tb")
      .workspaceName("RsdKernelGen_tb")
      .compile(RsdKernelGen(rsd_cfg))
  }else{
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .workspaceName("RsdKernelGen_tb")
      .compile(RsdKernelGen(rsd_cfg))
  }

  def testCase(d_id: Int, f_id: Int): Unit = {
    def testBench(dut: RsdKernelGen): Unit = {
      dut.clockDomain.forkStimulus(2)
      dut.io.timeshift.valid #= false
      dut.io.distance.valid #= false
      dut.io.wave.valid #= false
      dut.io.ring_impulse.valid #= false

      dut.clockDomain.waitSampling()

      var distance_sent: Boolean = false
      forkJoin(
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
              for(r <- 0 until rsd_cfg.radius_factor){
                dut.io.wave.valid #= true
                dut.io.wave.payload #= wave(r, d_id)
                dut.clockDomain.waitSampling()
              }
              dut.io.wave.valid #= false
            }
            ,
            () => {
              dut.clockDomain.waitSampling(dut.pRsdKernelGen.W2CLatency)
              for(r <- 0 until rsd_cfg.radius_factor){
                dut.io.ring_impulse.valid #= true
                dut.io.ring_impulse.payload.zipWithIndex.foreach{ case (dat, i) =>
                  dat #= impulse(i, r)
                }
                dut.clockDomain.waitSampling()
              }
              dut.io.ring_impulse.valid #= false
            }
          )

          dut.clockDomain.waitSampling(10)
          simSuccess()
        }
        ,
        // Monitor1 catch hardware kernel output
        () => {
          while (true){
            dut.clockDomain.waitActiveEdgeWhere(dut.io.kernel.valid.toBoolean)
            for(idx <- dut.io.kernel.payload.indices){
              hardware_rsd(d_id)(f_id)(idx) = dut.io.kernel.payload(idx).toComplex
            }
          }
        }
      )

    }

    module_compiled.doSim(s"RsdKernelGen_d${d_id}_f${f_id}_tb")(testBench)
  }


  val uin_fft = uin.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  new File("tb/RsdKernelGen_tb/rad").mkdirs()
  for(did <- rsd_cfg.depthRange){
    val rad_d = DenseMatrix.fill(rsd_cfg.freq_factor, rsd_cfg.impulse_sample_point)(0d)
    for(fid <- rsd_cfg.freqRange){
      testCase(did, fid)
      rad_d(fid, ::) := hardware_rsd(did)(fid).map(_.abs).t
    }
    csvwrite(
      new File(s"tb/RsdKernelGen_tb/rad_$did.csv"),
      rad_d
    )
  }
  new File("tb/RsdKernelGen_tb/uout").mkdirs()
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- 0 until rsd_cfg.freq_factor) {
      val rsd_kernel_rad = hardware_rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    val uout_d = iFourierTr(uout_f)
    //    csvwrite(
    //      new File(s"tb/RsdGenCore/uout/uout_d${depth}_real.csv"),
    //      uout_d.map(_.real)
    //    )
    uout_d
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

}
