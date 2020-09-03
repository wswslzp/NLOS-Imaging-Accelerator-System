package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray.CoefGenArray
import spinal.core._

object CoefGenArrayMain extends App{

  val wave_cfg = HComplexConfig(8, 8)
  val distance_cfg = HComplexConfig(8, 8)
  val timeshift_cfg = HComplexConfig(-4, 20)
  val coef_cfg = HComplexConfig(-4, 20)
  val imp_cfg = HComplexConfig(5, 11)

//  val rsd_cfg = RsdKernelConfig(
//    wave_cfg, distance_cfg,timeshift_cfg, coef_cfg, imp_cfg, 1, 2
//  )

  val rsd_cfg = RsdKernelConfig(
    wave_cfg = wave_cfg,
    distance_cfg = distance_cfg,
    timeshift_cfg = timeshift_cfg,
    coef_cfg = coef_cfg,
    imp_cfg = imp_cfg,
    depth_factor = 1,
    radius_factor = 60
  )

  SpinalConfig(
    mode = Verilog,
//    targetDirectory = "tb/CoefGenCore_tb/rtl",
//    targetDirectory = "rtl",
    targetDirectory = "../NLOS_RTL",
    headerWithDate = true,
    oneFilePerComponent = true,
    rtlHeader = "Designer\t: Zhengpeng Liao"
  ).generate(
//    CoefGenCore(rsd_cfg)
    CoefGenArray(
      rsd_cfg,
      freq_num = 1
    )
  )//.printPruned()
}
