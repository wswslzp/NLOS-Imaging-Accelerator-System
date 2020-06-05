import spinal.core._
import spinal.lib._
import Config._
import Core.CoefGenArray
import Util._

object CoefGenArrayMain extends App{

  val wave_cfg = HComplexConfig(8, 8)
  val distance_cfg = HComplexConfig(8, 8)
  val timeshift_cfg = HComplexConfig(-4, 20)
  val coef_cfg = HComplexConfig(-4, 20)
  val imp_cfg = HComplexConfig(5, 11)

  val rsd_cfg = RsdKernelConfig(
    wave_cfg, distance_cfg,timeshift_cfg, coef_cfg, imp_cfg, 1, 50, 50
  )

  Synthesizable.setSimulation(true)

  SpinalConfig(
    mode = Verilog,
//    targetDirectory = "tb/CoefGenCore_tb/rtl",
    targetDirectory = "rtl",
    headerWithDate = true
  ).generate(
    CoefGenArray(
      rsd_cfg,
      freq_num = 1
    )
  )//.printPruned()
}
