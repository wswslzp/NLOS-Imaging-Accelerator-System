import spinal.core._
import spinal.lib._
import Core.PRsdGenCore
import Config._
import Util._

object PRsdGenCoreMain extends App {
  val wave_cfg = HComplexConfig(8, 8)
  val distance_cfg = HComplexConfig(8, 8)
  val timeshift_cfg = HComplexConfig(-4, 20)
  val coef_cfg = HComplexConfig(-4, 20)
  val imp_cfg = HComplexConfig(5, 11)

  val rsd_cfg = RsdKernelConfig(
    wave_cfg, distance_cfg,timeshift_cfg, coef_cfg, imp_cfg, 1, 2, 2
  )

  Synthesizable(false)

  SpinalConfig(
    targetDirectory = "../NLOS_RTL",
    headerWithDate = true
  ).generateVerilog(
    PRsdGenCore(rsd_cfg)
  )
}