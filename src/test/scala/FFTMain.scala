import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

object FFTMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )
  Synthesizable(true)
  val config = SpinalConfig(
    targetDirectory = "rtl", headerWithDate = true, oneFilePerComponent = true
  )
  SpinalVerilog(config)(
    FFT2d(fft_config)
  ).printPruned()
}
