package Fpga.MacArray

import spinal.core._
import spinal.lib._
import Config.RsdKernelConfig
import Util._

case class RowMac(cfg: RsdKernelConfig) extends Component {
  val complex_cfg = cfg.getMACDatConfig * cfg.getMACDatConfig
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(complex_cfg)))
    val data_out = out(HComplex(complex_cfg))
    val clear = in Bool()
    val acc_in_addr = in UInt(log2Up(cfg.cols) bit)
    val pipe_out_addr = in UInt(log2Up(cfg.cols) bit)
  }

  // TODO: Change the read during write policy
  val acc_in_addr_1 = RegNext(io.acc_in_addr) init 0
  val pipe_out_addr_1 = RegNext(io.pipe_out_addr) init 0

  // Note: Dual port SRAM/BRAM.
  val row_mem = Mem(Bits(complex_cfg.getComplexWidth bit), BigInt(cfg.cols)).init(Array.fill(cfg.cols)(B(0)))
  row_mem.addAttribute("ramstyle", "M20K")
  val prev_data = HComplex(complex_cfg)
  val data_in = io.data_in.toReg()

  prev_data := row_mem(acc_in_addr_1)
  when(io.data_in.valid) {
    row_mem(io.acc_in_addr) := (prev_data + data_in).fixTo(complex_cfg).asBits
  }
  when(io.clear){
    row_mem(io.pipe_out_addr) := B(0, complex_cfg.getComplexWidth bit)
  }
  io.data_out := row_mem(io.pipe_out_addr)

}
