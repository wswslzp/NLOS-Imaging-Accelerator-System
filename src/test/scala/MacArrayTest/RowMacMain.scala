package MacArrayTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.MacArray._
import scala.sys.process._
import java.io.File

object RowMacMain extends App{

  new File("rtl/RowAcc").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/RowAcc"
  ).generateVerilog(
    RowAcc(Config.RsdKernelConfig.rsd_cfg.getMACDatConfig, Config.RsdKernelConfig.rsd_cfg.cols)
  )

  Process("tar -zcvf RowAcc.tar rtl/RowAcc/ ").!
  Process("scp RowAcc.tar Zhengpeng@10.19.129.46:/home/Workspace/Zhengpeng/transfer").!

}
