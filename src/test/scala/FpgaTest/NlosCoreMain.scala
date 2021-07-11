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
//    oneFilePerComponent = true
  ).generateVerilog{
    val core = NlosCore(rsd_cfg)
    core.rework{ // Useless
      core.io.result.allowDirectionLessIo
      core.io.result.setAsDirectionLess()
      val of = out Bool()
      val result_stream = core.io.result.toStream(of)
      master(result_stream)
    }
    core
  }
}
