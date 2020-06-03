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

  Synthesizable.setSimulation(true)

  SpinalConfig(
    mode = Verilog,
//    targetDirectory = "tb/CoefGenCore_tb/rtl",
    targetDirectory = "rtl",
    headerWithDate = true
  ).generate(
    CoefGenArray(
      wave_cfg = wave_cfg,
      distance_cfg = distance_cfg,
      timeshift_cfg = timeshift_cfg,
      cfg = RsdKernelConfig(
        hComplexConfig = coef_cfg,
        deltaw_factor = 1, depth_factor = 1, radius_factor = 1
      ),
      freq_num = 1
    )
  )//.printPruned()
}
