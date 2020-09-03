package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray.ExpFunc
import Util._
import spinal.core._

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
