package Core.FFT2d

import Config._
import Util._
import spinal.core._
import spinal.lib._

case class IFFT2d(cfg:FFTConfig) extends Component {
  import FFT2d.fft2
  val io = new Bundle {
    val line_in = slave(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val line_out= master(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
  }

  val int_line_in_conj: Flow[Vec[HComplex]] = io.line_in.translateWith(Vec( io.line_in.payload.map(_.conj) ))
  val int_line_out = fft2(int_line_in_conj, cfg.row)
  val int_line_out_conj: Flow[Vec[HComplex]] = int_line_out.translateWith(Vec( int_line_out.payload.map(_.conj) ))

  io.line_out <-< int_line_out_conj
}

object IFFT2d {
  def ifft2(input: Flow[Vec[HComplex]], row: Int): Flow[Vec[HComplex]] = {
    val hcfg = input.payload(0).config
    val length = input.payload.length
    val cfg = FFTConfig(
      hComplexConfig = hcfg, point = length, row = row
    )
    val ifft2_core = IFFT2d(cfg)
    ifft2_core.io.line_in << input
    ifft2_core.io.line_out
  }
}
