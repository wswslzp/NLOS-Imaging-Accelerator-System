import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

object MyFFTMain extends App{
  SpinalConfig(
    targetDirectory = "../fft/myfft_tb/rtl"
  ).generateVerilog(
    MyFFT(
      length = 128,
      cfg = HComplexConfig(8, 8)
    )
  )
}
