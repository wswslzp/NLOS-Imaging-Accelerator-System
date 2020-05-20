package Core

import Config.FFTConfig
import Util.Conjugate
import spinal.core._

case class IFFT2d(fft_config:FFTConfig) extends Component {
  val io = new Bundle {
    val next_in = in Bool()
    val line_in = in(Vec(UInt(fft_config.data_width bit), fft_config.fft_point))
    val next_out = out Bool()
    val line_out = out(Vec(UInt(fft_config.data_width bit), fft_config.fft_point))
  }

  val fft2d_core = FFT2d(fft_config)
  fft2d_core.io.next <> io.next_in
  fft2d_core.io.next_out <> io.next_out

  // conjugate all the input & output
  fft2d_core.io.line_in <> Vec(
    io.line_in
      .map(Conjugate(_, fft_config.data_width-1 downto fft_config.data_width/2))
  )
  io.line_out <> Vec(
    fft2d_core.io.line_out
      .map(Conjugate(_, fft_config.data_width-1 downto fft_config.data_width/2))
  )
}
