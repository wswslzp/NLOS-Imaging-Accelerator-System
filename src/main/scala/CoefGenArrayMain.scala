import spinal.core._
import spinal.lib._
import Config._
import Core.CoefGenArray
import Util._

object CoefGenArrayMain extends App{

  val wave_cfg = HComplexConfig(8, 8)
  val distance_cfg = HComplexConfig(8, 8)
  val timeshift_cfg = HComplexConfig(0, 16)
  val coef_cfg = HComplexConfig(8, 8)

  SpinalConfig(
    mode = Verilog,
    targetDirectory = "rtl"
  ).generate(
    CoefGenArray(
      wave_cfg = wave_cfg,
      distance_cfg = distance_cfg,
      timeshift_cfg = timeshift_cfg,
      cfg = RsdKernelConfig(
        hComplexConfig = coef_cfg,
        deltaw_factor = 1, depth_factor = 1, radius_factor = 1
      ),
      expfunc_point = 128,
      expfunc_range = (-5d, 5d),
      freq_num = 1
    )
  )//.printPruned()
}
