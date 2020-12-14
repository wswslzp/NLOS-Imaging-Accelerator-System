package Fpga.MacArray

import spinal.core._
import spinal.lib._
import Config.RsdKernelConfig
import Util._

case class RowAcc(cfg: RsdKernelConfig) extends Component {
  var complex_cfg = cfg.getMACDatConfig * cfg.getMACDatConfig
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(complex_cfg)))
    val data_out = out(HComplex(complex_cfg))
    val clear = in Bool()
    val acc_in_addr = in UInt(log2Up(cfg.cols) bit)
    val pipe_out_addr = in UInt(log2Up(cfg.cols) bit)
  }
  val mem_sel = Reg(Bool()) init False
  val acc_in_addr_1 = RegNext(io.acc_in_addr) init 0
  val pipe_out_addr_1 = RegNext(io.pipe_out_addr) init 0

  // Note: Dual port SRAM/BRAM.
  val row_mem_0 = Mem(Bits(complex_cfg.getComplexWidth bit), BigInt(cfg.cols)).init(Array.fill(cfg.cols)(B(0)))
  val row_mem_1 = Mem(Bits(complex_cfg.getComplexWidth bit), BigInt(cfg.cols)).init(Array.fill(cfg.cols)(B(0)))
  row_mem_0.addAttribute("ramstyle", "M20K")
  row_mem_1.addAttribute("ramstyle", "M20K")

  // Get the previous data
  val prev_data = HComplex(complex_cfg)
  prev_data := row_mem_0(io.acc_in_addr)
  prev_data := mem_sel ? row_mem_1(io.acc_in_addr) | row_mem_0(io.acc_in_addr)

  val sum = ( prev_data + io.data_in.payload ).fixTo(complex_cfg).asBits
  val sum_1 = RegNext(sum) init 0
  val data_in_valid_1 = RegNext(io.data_in.valid) init False

  when(data_in_valid_1) {
    row_mem_0(acc_in_addr_1) := sum_1
  }

  val clear_1 = RegNext(io.clear) init False
  when(clear_1){
    row_mem_0(pipe_out_addr_1) := B(0, complex_cfg.getComplexWidth bit)
  }
  io.data_out := row_mem_0(io.pipe_out_addr)

}
