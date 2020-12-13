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
    val col_addr = in UInt(log2Up(cfg.cols) bit)
  }

  val row_mem = Mem(HComplex(complex_cfg), BigInt(cfg.cols))
  val prev_data = row_mem(io.col_addr)
  when(io.data_in.valid){
    row_mem.write(io.col_addr, prev_data + io.data_in.payload)
  } elsewhen(io.clear){
    row_mem.write(io.col_addr, HC(0, 0, complex_cfg))
  }
  io.data_out := row_mem(io.col_addr)

}
