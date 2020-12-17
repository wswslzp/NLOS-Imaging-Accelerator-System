package Fpga.FFT2d

import Config.FFTConfig
import Core.FFT2d.MyFFT.fft
import Util.{HComplex, countUpFrom, countUpInside}
import spinal.core._
import spinal.lib._
import FFTConfig._

case class PartialFFT(cfg: FFTConfig) extends Component {
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
    val mode = in (Bool()) // 0: col line in/col line out ; 1: row pixel in/row pixel out
    val conj_mode = in (ConjMode()) // 0: former ; 1: Back
  }

  val s2p_row = cloneOf(io.col_line_in)
  s2p_row.valid := countUpInside(io.row_pix_in.valid, cfg.point).last
  s2p_row.payload := Vec(History(io.row_pix_in.payload, cfg.point, io.row_pix_in.valid).reverse)
  val line_in = io.mode ? s2p_row | io.col_line_in
  val fft_in = line_in.translateWith(Vec(line_in.payload.map{dat=>
    (io.conj_mode === ConjMode.former_conj) ? dat.conj | dat
  }))

  val fft_out = fft(fft_in)
  val fft_out_conj = fft_out.translateWith(
    Vec(fft_out.payload.map{dat=>
      ( io.conj_mode === ConjMode.back_conj ) ? dat.conj | dat
    })
  )
  io.col_line_out.valid := (~io.mode ) & fft_out_conj.valid
  io.col_line_out.payload := fft_out_conj.payload

  // When mode = 1, fft is in row pixel out mode, fft out conj valid assert one cycle
  //  per column, with interval of `point` cycles. so col addr area should count up from
  //  the valid for `point` cycles just in this mode
  val col_addr_area = countUpFrom(fft_out_conj.valid & io.mode, 0 until cfg.point)
  io.row_pix_out.valid := col_addr_area.cond_period & io.mode
  io.row_pix_out.payload := fft_out_conj.payload(col_addr_area.cnt.value)

}

