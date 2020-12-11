package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.PostProcess
import Config._
import RsdKernelConfig._

import java.io.File

object PostProcMain extends App{
  new File("../rtl/PostProc").mkdir()
  SpinalConfig(
    targetDirectory = "../rtl/PostProc",
    oneFilePerComponent = true
  ).generateVerilog(PostProcess(rsd_cfg))
}
