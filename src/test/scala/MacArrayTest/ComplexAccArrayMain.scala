package MacArrayTest

import java.io.File

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.MacArray.ComplexAccArray
import Fpga.MacArray.RowMacArray
import Config.RsdKernelConfig._

object ComplexAccArrayMain extends App{
  new File("rtl/ComplexAccArray").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/ComplexAccArray",
    oneFilePerComponent = true
  ).generateVerilog(RowMacArray(rsd_cfg))
}
