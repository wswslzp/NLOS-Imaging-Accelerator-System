package Fpga.MacArray

import spinal.core._
import spinal.lib._
import Config.RsdKernelConfig
import Util._

case class RowMac(cfg: RsdKernelConfig) extends Component {
  val complex_cfg = cfg.getMACDatConfig
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(complex_cfg)))
    val data_out = out(HComplex(complex_cfg))
    val clear = in Bool()
    val acc_in_addr = in UInt(log2Up(cfg.cols) bit)
    val pipe_out_addr = in UInt(log2Up(cfg.cols) bit)
  }

  // Note: Dual port SRAM/BRAM.
  val row_mem = Mem(Bits(complex_cfg.getComplexWidth bit), BigInt(cfg.cols)).init(Array.fill(cfg.cols)(B(0)))
  val prev_data = HComplex(complex_cfg)
  prev_data := row_mem(io.acc_in_addr)
  when(io.data_in.valid) {
    row_mem(io.acc_in_addr) := (prev_data + io.data_in.payload).fixTo(complex_cfg).asBits
  } elsewhen(io.clear){
    row_mem(io.pipe_out_addr) := 0
  }
  io.data_out := row_mem(io.pipe_out_addr)

}
