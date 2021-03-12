package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.PostProcess
import Config._
import RsdKernelConfig._

import java.io.File

object PostProcMain extends App{
  val rsd_cfg = RsdKernelConfig(
    //    wave_cfg = HComplexConfig(8, 16),
    //    distance_cfg = HComplexConfig(8, 16),
    //    timeshift_cfg = HComplexConfig(8, 16) >> 5,
    //    coef_cfg = HComplexConfig(8, 16) >> 6, // (-4 ,20) --> (-5, 21)
    //    imp_cfg = HComplexConfig(8, 16) >> 3,
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows,
    fpga_impl = true
  )

  new File("../rtl/PostProc").mkdir()
  SpinalConfig(
    targetDirectory = "../rtl/PostProc",
    oneFilePerComponent = true
  ).generateVerilog(PostProcess(rsd_cfg))
}
