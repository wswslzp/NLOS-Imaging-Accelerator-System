import Config._
import Core.FFT2d
import spinal.core._
import spinal.lib._

import scala.language.postfixOps

object FFTMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )
  val config = SpinalConfig(
    targetDirectory = "../fft/fft2d/rtl"
  )
  SpinalVerilog(config)(
    FFT2d(fft_config)
  ).printPruned()
}
