package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import RsdKernelConfig._
import Sim._
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import breeze.linalg._
import breeze.signal._
import breeze.math._
import java.io._
import Fpga._
import SimTest.NlosSystemSimTest.write_image

object PostProcTest extends App{
  // Generating input data
  val coef = Computation.generateCoef(wave, distance, timeshift)
  val rsd = Computation.generateRSDRadKernel(coef, impulse)
  val uin_fft = uin.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    loadComplexMatrix(
      real_part_filename = s"src/test/resource/uout/uout_${depth}_real.csv",
      imag_part_filename = s"src/test/resource/uout/uout_${depth}_imag.csv"
    )
//    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
//    for(f <- rsd_cfg.freqRange) {
//      val rsd_kernel_rad = rsd(depth)(f)
//      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
//      uout_f += rsd_kernel *:* uin_fft(f)
//    }
//    if(depth == 10){
//      csvwrite(
//        new File("tmp/soft_uoutf10.csv"),
//        uout_f.map(_.real)
//      )
//    }
//    iFourierTr(uout_f)
  }

  val ov = 1
  val withWave = false
  val waveDepth = 1

  val compiled = if (withWave) {
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(PostProcess(rsd_cfg, over_sample_factor = ov))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(PostProcess(rsd_cfg, over_sample_factor = ov))
  }

  val h_img_out = DenseMatrix.zeros[Int](rsd_cfg.rows*ov, rsd_cfg.cols*ov)
  var done = false

  compiled.doSim("PostProcess_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dut.io.img_in.valid #= false
    dut.io.img_out.ready #= true
    dut.io.done #= false
    dut.clockDomain.waitSampling()

    forkJoin(
      // Driver
      () => {
        for(d <- rsd_cfg.depthRange){
          println(s"Now d is $d")
          dut.io.img_in.valid #= true
          for(r <- rsd_cfg.rowRange){
            for(c <- rsd_cfg.colRange){
              dut.io.img_in.payload #= uout(d)(r, c)
              dut.clockDomain.waitSampling()
            }
          }
          dut.io.img_in.valid #= false
          if(d == rsd_cfg.depth_factor-1) {
            dut.io.done #= true
            dut.clockDomain.waitSampling()
            dut.io.done #= false
          }
          dut.clockDomain.waitSampling(1000)
        }
        waitUntil(done)
        simSuccess()
      }
      ,

      // Monitor
      () => {
        while(true){
          dut.clockDomain.waitActiveEdgeWhere(dut.io.img_out.valid.toBoolean)
          for(r <- 0 until rsd_cfg.rows*ov){
            for(c <- 0 until rsd_cfg.cols*ov/2){
              h_img_out(r, 2*c) = dut.io.img_out.payload(0).toInt
              h_img_out(r, 2*c+1) = dut.io.img_out.payload(1).toInt
              dut.clockDomain.waitSampling()
            }
          }
          done = true
        }
      }
    )
  }

  csvwrite(
    new File("tb/PostProcess/h_img_out.csv"),
    h_img_out.map(_.toDouble)
  )
  write_image(h_img_out.map(_.toDouble), "tb/PostProcess/h_img_out.jpg")

}
