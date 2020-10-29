package FFT2dTest

import Config.{HComplexConfig, RsdKernelConfig}
import RsdKenelGenTest.RsdGenCoreMain.{distance, wave}
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.FFT2d._
import java.io._

object FFt2dCoreMain extends App{
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = 51,
    radius_factor = 68,
    freq_factor = 69
  )

//  new File("rtl/fft2d_core").mkdir()
//  SpinalConfig(
//    oneFilePerComponent = true,
//    targetDirectory = "rtl/fft2d_core"
//  ).generateVerilog(
//    FFT2dCore(rsd_cfg.getFFT2dConfig, rsd_cfg.freq_factor, rsd_cfg.depth_factor)
//  )

  val compiled_fft2d_core = SimConfig.allOptimisation.workspacePath("tb").withWave.compile(
    Core.FFT2d.FFT2dCore(rsd_cfg.getFFT2dConfig, rsd_cfg.freq_factor, rsd_cfg.depth_factor)
  )

  compiled_fft2d_core.doSim("FFT2dCoreMain_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
  }

}
