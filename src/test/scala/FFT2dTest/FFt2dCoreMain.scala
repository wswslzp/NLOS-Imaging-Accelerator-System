package FFT2dTest

import Config.{HComplexConfig, RsdKernelConfig}
import RsdKernelConfig._
import spinal.core._
import spinal.core.sim._
import spinal.lib._
//import Core.FFT2d._
import Fpga.FFT2dCore
import java.io._
import breeze.math._

object FFt2dCoreMain extends App{
  import Sim.SimFix._
  import Sim.SimComplex._
  import Config.RsdKernelConfig.rsd_cfg

  new File("rtl/fft2d_core").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/fft2d_core",
    oneFilePerComponent = true
  ).generateVerilog(
    FFT2dCore(
      rsd_cfg,
      rsd_cfg.freq_factor,
      rsd_cfg.depth_factor
    )
  )

//  val compiled_fft2d_core = SimConfig.allOptimisation.workspacePath("tb").withWave.compile(
//    Core.FFT2d.FFT2dCore(rsd_cfg.getFFT2dConfig, rsd_cfg.freq_factor, rsd_cfg.depth_factor)
//  )
//
//  compiled_fft2d_core.doSim("FFT2dCoreMain_tb"){dut=>
//    dut.clockDomain.forkStimulus(2)
//    dut.io.dc #= 0
//    dut.io.fc #= 0
//    dut.io.push_ending #= false
//    dut.io.data_in.valid #= false
//    dut.clockDomain.waitSampling()
//
//    // begin test bench
//    fork {
//      for{
//        d <- 0 until rsd_cfg.depth_factor
//        f <- 0 until rsd_cfg.freq_factor
//      } {
//        dut.io.dc #= d
//        dut.io.fc #= f
//
//        if (d == 0 || (d > 1 && f > 1)) {
//          // pipe in the data when d == 0
//          dut.io.data_in.valid #= true
//          dut.io.data_in.payload #= Complex(d.toDouble, f.toDouble)
//          dut.clockDomain.waitSampling()
//        } else {
//          dut.io.data_in.valid #= false
//          dut.clockDomain.waitSampling()
//        }
//      }
//    }
//
//    // Monitor 1: Catch the data inside int mem
////    fork{
////      ???
////    }
//  }

}
