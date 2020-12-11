package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._

import java.io.File
import Fpga.NlosCore
import Config._
import RsdKernelConfig._

object NlosCoreMain extends App{
  new File("../rtl/NlosCore").mkdir()
  SpinalConfig(
    targetDirectory = "../rtl/NlosCore",
    oneFilePerComponent = true
  ).generateVerilog(
    NlosCore(rsd_cfg)
  )
}
