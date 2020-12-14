package FpgaTest

import spinal.core._
import Config._
import RsdKernelConfig._
import Fpga._

import java.io.File
import scala.sys.process.Process

object NlosNoDriverMain extends App{
  new File("rtl/NlosNoDriver").mkdir()

  SpinalConfig(
    targetDirectory = "rtl/NlosNoDriver",
    oneFilePerComponent = true
  ).generateVerilog(
    NlosNoDriver(rsd_cfg)
  )
  Process("tar -zcvf NlosNoDriver.tar rtl/NlosNoDriver/ ").!
  Process("scp NlosNoDriver.tar Zhengpeng@10.19.129.46:/home/Workspace/Zhengpeng/transfer").!
}
