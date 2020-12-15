package Fpga.FFT2d

import spinal.core._
import spinal.lib._
import Config._
import Util._
import Core.FFT2d.MyFFT.fft
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
  last_fft.io.mode := io.mode
  last_fft.io.conj_mode := io.inverse ? ConjMode.back_conj | ConjMode.no_conj


//
//  // do the row fft
//  val fft_row: Flow[Vec[HComplex]] = fft(io.line_in, cfg.row_pipeline)
//  fft_row.setName("fft_row")
//
//  // declare a reg array, and push the data into it
//  val img_reg_array = Reg(
//    Vec(Vec(HComplex(cfg.hComplexConfig), cfg.point), cfg.row)
//  )
//  val row_addr: Counter = Counter(0 until cfg.row, inc = fft_row.valid)
//  when(fft_row.valid) {
//    img_reg_array(row_addr) := fft_row.payload
//  }
//
//  val col_addr_area = ColAddrArea(cfg.col_pipeline)
//  col_addr_area.row_addr_ov := row_addr.willOverflow
//
//  val col_addr_vld = col_addr_area.col_addr_vld
//  val col_addr = RegNext( col_addr_area.col_addr )
//  val fft_col_in = Flow(
//    Vec(HComplex(cfg.hComplexConfig), cfg.row)
//  )
//  fft_col_in.payload.zipWithIndex.foreach { case(dat, idx) =>
//    dat := img_reg_array(idx)(col_addr)
//  }
//  fft_col_in.valid := RegNext(col_addr_vld) init False
//
//  val fft_col_out: Flow[Vec[HComplex]] = fft(fft_col_in, cfg.col_pipeline)
//  col_addr_area.fft_out_vld := fft_col_out.valid
//  fft_col_out.setName("fft_col_out")
//  fft_col_out >-> io.line_out

}
