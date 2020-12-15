package Fpga.FFT2d

import Util.countUpFrom
import Config._
import spinal.core._
import spinal.lib._

case class ColAddrArea(cfg: FFTConfig, use_pip: Boolean = true) extends Component {
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

