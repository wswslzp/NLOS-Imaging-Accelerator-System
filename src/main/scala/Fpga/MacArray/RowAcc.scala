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
  val acc_in_addr_1 = RegNext(io.acc_in_addr) init U(0)

  // Note: Dual port SRAM/BRAM.
  val row_mem = Mem(Bits(cfg.getComplexWidth bit), BigInt(cols)).init(Array.fill(cols)(B(0)))

  // Get the previous data
  val mem_out = HComplex(cfg)
  val read_addr = io.data_in.valid ? io.acc_in_addr | io.pipe_out_addr
  mem_out := row_mem.readSync(read_addr)

  val data_in = io.data_in.toReg(HC(0, 0, cfg))
  val sum = (mem_out + data_in).fixTo(cfg)
  val sum_b = sum.asBits
  val data_in_valid_1 = Delay(io.data_in.valid, 1, init = False)

  // When clear assert, all the pixel in row clears
  val clear_addr_area = countUpFrom(io.clear.rise(False), 0 until cols)
  val clear_addr = clear_addr_area.cnt.value
  val clear_valid = clear_addr_area.cond_period

  val write_addr = cloneOf(clear_addr).setAllTo(clear_valid) & clear_addr | cloneOf(acc_in_addr_1).setAllTo(data_in_valid_1) & acc_in_addr_1
  val write_data = cloneOf(sum_b).setAllTo(data_in_valid_1) & sum_b
  when(clear_valid | data_in_valid_1) {
    row_mem(write_addr) := write_data
  }

  // Output data out
  io.data_out := mem_out

}
