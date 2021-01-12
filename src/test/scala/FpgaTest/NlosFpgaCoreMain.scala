package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import java.io.File
import Fpga._
import Config.RsdKernelConfig._
import Util._

object NlosFpgaCoreMain extends App{
  FpgaImpl(true)
  Synthesizable(false)
  new File("rtl/NlosFpgaCore").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/NlosFpgaCore",
    defaultConfigForClockDomains = ClockDomainConfig(resetKind = SYNC)
  ).generateVerilog(NlosFpgaCore(rsd_cfg))
}
