package MacArrayTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.MacArray._

object RowMacMain extends App{

  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(
    RowMac(Config.RsdKernelConfig.rsd_cfg)
  )
}
