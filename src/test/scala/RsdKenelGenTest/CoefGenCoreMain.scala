package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._

object CoefGenCoreMain extends App{
  import RsdKernelConfig._

  new java.io.File("rtl/CoefGenCore").mkdir()
  SpinalConfig(
    targetDirectory = "../rtl/CoefGenCore/",
    oneFilePerComponent = true
  ).generateVerilog(
    Core.RsdGenCoreArray.CoefGenCore(rsd_cfg)
  )

}
