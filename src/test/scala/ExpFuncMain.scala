import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Core.RsdGenCoreArray.ExpFunc

object ExpFuncMain extends App{
  val cfg = HComplexConfig(
    16, 16
  )
  Synthesizable(false)
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "tb/ExpFunc_tb/rtl",
    headerWithDate = true
  ).generateVerilog(
    ExpFunc(cfg, 128, 1)
  )
}
