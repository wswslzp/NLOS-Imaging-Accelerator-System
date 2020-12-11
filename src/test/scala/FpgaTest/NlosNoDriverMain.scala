package FpgaTest

import spinal.core._
import Config._
import RsdKernelConfig._
import Fpga._
import java.io.File

object NlosNoDriverMain extends App{
  new File("../rtl/NlosNoDriver").mkdir()

  SpinalConfig(
    targetDirectory = "../rtl/NlosNoDriver",
    oneFilePerComponent = true
  ).generateVerilog(
    NlosNoDriver(rsd_cfg)
  )
}
