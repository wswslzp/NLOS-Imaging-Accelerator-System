package FFT2dTest

import Config._
import Core.FFT2d._
import spinal.core._

object IFFT2dMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )
  new java.io.File("../rtl/IFFT2d").mkdir()
  val config = SpinalConfig(
    targetDirectory = "../rtl/IFFT2d",
    oneFilePerComponent = true
  )
  SpinalVerilog(config)(
    IFFT2d(fft_config)
  ).printPruned()
}
