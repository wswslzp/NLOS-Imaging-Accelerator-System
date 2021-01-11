package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import Util._

object CoefGenCoreMain extends App{
  import RsdKernelConfig._

  FpgaImpl(true)
  new java.io.File("../rtl/CoefGenCore").mkdirs()
  SpinalConfig(
    targetDirectory = "../fpga/CoefGenCore/rtl",
    oneFilePerComponent = true
  ).generateVerilog(
    Core.RsdGenCoreArray.CoefGenCore(rsd_cfg)
  )

}
