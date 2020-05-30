import Config.FFTConfig
import Core.IFFT2d
import spinal.core._
import spinal.lib._
import Config._
import Util._

object IFFT2dMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )
  val config = SpinalConfig(
    targetDirectory = "rtl"
  )
  SpinalVerilog(config)(
    IFFT2d(fft_config)
  ).printPruned()
}
