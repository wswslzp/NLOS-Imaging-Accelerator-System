package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.RsdGenCoreArray._
import Config.RsdKernelConfig._

object RsdGenCoreArrayMain_old extends App {
  new java.io.File("../rtl/RsdGenCoreArray").mkdirs()
  SpinalConfig(
    targetDirectory = "../rtl/RsdGenCoreArray",
    oneFilePerComponent = true
  ).generateVerilog(
    RsdGenCoreArray_old(rsd_cfg, init_addr = 0)
  )
}
