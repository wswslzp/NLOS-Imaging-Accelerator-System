package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import java.io.File
import Fpga._
import Config.RsdKernelConfig._

object NlosFpgaCoreMain extends App{
  new File("rtl/NlosFpgaSys").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/NlosFpgaSys"
  ).generateVerilog(NlosFpgaCore(rsd_cfg))
}
