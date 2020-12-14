package Fpga.MacArray

import spinal.core._
import spinal.lib._
import Config._
import Util._

case class RowAcc(cfg: HComplexConfig, cols: Int) extends Component {
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(cfg)))
    val data_out = out(HComplex(cfg))
    val clear = in Bool()
    val acc_in_addr = in UInt(log2Up(cols) bit)
    val pipe_out_addr = in UInt(log2Up(cols) bit)
  }
  val acc_in_addr_1 = RegNext(io.acc_in_addr) init 0
  val pipe_out_addr_1 = RegNext(io.pipe_out_addr) init 0

  // Note: Dual port SRAM/BRAM.
  val row_mem = Mem(Bits(cfg.getComplexWidth bit), BigInt(cols)).init(Array.fill(cols)(B(0)))
  row_mem.addAttribute("ramstyle", "M20K")

  // Get the previous data
  val prev_data = HComplex(cfg)
  prev_data := row_mem(io.acc_in_addr)

  val sum = ( prev_data + io.data_in.payload ).fixTo(cfg).asBits
  val sum_1 = RegNext(sum) init 0
  val data_in_valid_1 = RegNext(io.data_in.valid) init False

  when(data_in_valid_1) {
    row_mem(acc_in_addr_1) := sum_1
  }

  // When clear assert, all the pixel in row clears
  val clear_addr_area = countUpFrom(io.clear.rise(False), 0 until cols)
  val clear_addr = clear_addr_area.cnt.value
  when(clear_addr_area.cond_period){
    row_mem(clear_addr) := B(0, cfg.getComplexWidth bit)
  }

  // Output data out
  io.data_out := row_mem(io.pipe_out_addr)

}
