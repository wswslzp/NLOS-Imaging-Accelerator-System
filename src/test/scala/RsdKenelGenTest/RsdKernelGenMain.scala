package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.RsdGenCoreArray._

object RsdKernelGenMain extends App{
  import Config.RsdKernelConfig._
  new java.io.File("rtl/RsdKernelGen").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/RsdKernelGen",
    oneFilePerComponent = true
  ).generateVerilog(
    RsdKernelGen(rsd_cfg)
  )
}
