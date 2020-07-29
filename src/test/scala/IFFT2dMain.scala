import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

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
