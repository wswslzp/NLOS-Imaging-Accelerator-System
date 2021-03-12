package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import java.io.File
import Fpga._
import Config.RsdKernelConfig._
import Util._
import eda.altera._

object NlosFpgaCoreMain extends App{
  FpgaImpl(true)
  Synthesizable(false)
  new File("rtl/NlosFpgaCore").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/NlosFpgaCore",
    defaultConfigForClockDomains = ClockDomainConfig(resetKind = SYNC),
    headerWithDate = true
  ).generateVerilog(NlosFpgaCore(rsd_cfg))

  val nlos_fpga_proj = new QuartusProject(
    quartusPath = "/opt/intelFPGA/20.3/quartus/bin/",
    workspacePath = "/home/Workspace/Zhengpeng/nlos/fpga/nlos_fpga_sys"
  )

  nlos_fpga_proj.compile()
}
