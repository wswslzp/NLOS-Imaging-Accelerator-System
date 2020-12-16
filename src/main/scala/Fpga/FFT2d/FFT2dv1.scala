package Fpga.FFT2d

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import Util._
import FFTConfig._

case class FFT2dv1(cfg: FFTConfig) extends Component {


  val io = new Bundle {
    val col_line_in = slave(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val row_pix_in = slave(
      Flow(HComplex(cfg.hComplexConfig))
    )
    val col_line_out = master(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val row_pix_out = master(
      Flow(HComplex(cfg.hComplexConfig))
    )
    val mode = in Bool() // 0: row pixel in/col line out; 1: col line in/row pixel out
    val inverse = in Bool() // 0: FFT; 1: IFFT
  }

  val first_fft = PartialFFT(cfg)
  first_fft.io.col_line_in << io.col_line_in
  first_fft.io.row_pix_in << io.row_pix_in
  first_fft.io.mode := io.mode
  first_fft.io.conj_mode := io.inverse ? ConjMode.former_conj | ConjMode.no_conj

  val int_mem = IntMem(cfg)
  int_mem.io.col_line_in << first_fft.io.col_line_out
  int_mem.io.row_pix_in << first_fft.io.row_pix_out
  int_mem.io.mode := io.mode

  val last_fft = PartialFFT(cfg)
  last_fft.io.col_line_in << int_mem.io.col_line_out
  last_fft.io.row_pix_in << int_mem.io.row_pix_out
  io.col_line_out << last_fft.io.col_line_out
  io.row_pix_out << last_fft.io.row_pix_out
  last_fft.io.mode := ~io.mode
  last_fft.io.conj_mode := io.inverse ? ConjMode.back_conj | ConjMode.no_conj

}
