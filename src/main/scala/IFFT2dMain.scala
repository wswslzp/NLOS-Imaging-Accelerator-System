import Config.FFTConfig
import Core.IFFT2d
import spinal.core._
import spinal.lib._

object IFFT2dMain extends App{
  val fft_config = FFTConfig(
    data_width = 32,
    fft_point = 256,
    fft_delay = 33,
    row = 256
  )
  val config = SpinalConfig(
    targetDirectory = "rtl"
  )
  SpinalVerilog(config)(
    IFFT2d(fft_config)
  ).printPruned()
}
