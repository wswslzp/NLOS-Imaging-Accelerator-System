package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray.ExpFunc
import Util._
import spinal.core._

import java.io.File

object ExpFuncMain extends App{
  val cfg = HComplexConfig(
    16, 16
  )
  Synthesizable(false)
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "../fpga/ExpFunc/rtl",
    headerWithDate = true
  ).generateVerilog(
    ExpFunc(cfg, 32, 1)
  )
}
