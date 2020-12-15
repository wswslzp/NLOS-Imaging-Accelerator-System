package FFT2dTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.FFT2d._
import Config._
import RsdKernelConfig._

import java.io.File
import scala.sys.process.Process

object FFT2dv1Main extends App{
  new File("rtl/FFT2dv1").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/FFT2dv1"
  ).generateVerilog(
    FFT2dv1(rsd_cfg.getFFT2dConfig)
  )
  Process("tar -zcvf FFT2dv1.tar rtl/FFT2dv1/ ").!
  Process("scp FFT2dv1.tar Zhengpeng@10.19.129.46:/home/Workspace/Zhengpeng/transfer").!
}
