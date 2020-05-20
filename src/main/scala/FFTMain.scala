import Config.FFTConfig
import Core.FFT2d
import spinal.core._
import spinal.lib._

import sys.process._
import scala.language.postfixOps

object FFTMain extends App{
  val fft_config = FFTConfig(
    data_width = 32,
    fft_point = 128,
    fft_delay = 28,
    row = 128
  )
  val config = SpinalConfig(
    targetDirectory = "rtl"
  )
  SpinalVerilog(config)(
    FFT2d(fft_config)
  ).printPruned()
  println("change the signal name")
  "sed 's/next_1_/next/g' rtl/FFT2d.v -i.bak" !
}
