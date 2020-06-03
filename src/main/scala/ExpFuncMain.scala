import Config.HComplexConfig
import spinal.core._
import Core._
import Util.Synthesizable

object ExpFuncMain extends App{
  val cfg = HComplexConfig(
    16, 16
  )
  Synthesizable.setSimulation(true)
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "tb/ExpFunc_tb/rtl"
  ).generateVerilog(
    ExpFunc(cfg, 128, 1)
  )
}
