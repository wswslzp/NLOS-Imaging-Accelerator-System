package Fpga.FFT2d

import spinal.core._
import spinal.lib._
import Config._
import Util._
import Core.FFT2d.MyFFT.fft
import FFTConfig._

case class FFT2dv1(cfg: FFTConfig) extends Component {

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
      val mode = in (FFTMode())
      val inverse = in Bool()
    }


  }

  case class ColAddrArea(use_pip: Boolean = true) extends Component {
    val row_addr_ov = in Bool()
    val fft_out_vld = in Bool() allowPruning()
    val col_addr = out UInt(log2Up(cfg.point) bit)
    val col_addr_vld = out Bool()

    if(use_pip){
      val col_addr_area = countUpFrom(row_addr_ov, 0 until cfg.point, "col_addr")
      col_addr := col_addr_area.cnt.value
      col_addr_vld := col_addr_area.cond_period
    } else {
      val cnt = Counter(0 until cfg.point, inc = row_addr_ov || fft_out_vld)
      cnt.setCompositeName(this, "col_addr_cnt")
      val cond_period_minus_1 = Reg(Bool()) setWhen row_addr_ov clearWhen cnt.willOverflow
      col_addr_vld := cond_period_minus_1 | row_addr_ov
      col_addr := cnt.value
    }
  }

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
    val mode = in Bool()
    val inverse = in Bool()
  }
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
