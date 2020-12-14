package Fpga.MacArray

import spinal.core._
import spinal.lib._
import Config.RsdKernelConfig
import Util._

case class RowMacArray(cfg: RsdKernelConfig) extends Component {
  val io = new Bundle {
    val fc_overflow = in Bool()
    val rsd_kernel = slave(Flow(Vec(HComplex(cfg.getKernelConfig), cfg.rows)))
    val fft_out = slave(Flow(Vec(HComplex(cfg.getFUinConfig), cfg.rows)))
    val mac_result = master(Flow(Vec(HComplex(cfg.getMACDatConfig), cfg.rows)))
  }
  val valid = io.rsd_kernel.valid & io.fft_out.valid

  // Pipeline the complex multiplication for good timing. Trade off between area and timing.
  val rsd_fft_prod = Vec.tabulate(cfg.rows){idx=>
    val tmp = io.rsd_kernel.payload(idx) * io.fft_out.payload(idx)
    RegNext(tmp)
  }
  val rsd_fft_prod_valid = RegNext(valid) init False

  // Count up for the current column address
  val col_addr_area = countUpInside(rsd_fft_prod_valid, cfg.cols)
  val col_addr = col_addr_area.cnt
  col_addr.setName("col_addr")

  // *************** MAC array ***********************
  // Different from register array. When rsd fft prod valid is asserted,
  //  mac array is also pipe out old data. So it needs two read out port and two write port.
  //  One pair of WR port, is used to read previous data and write the accumulate data into
  //  the same place. Another pair of WR port, is used to read out the data and clear the data
  //  at the same time/place. The two pair of transaction happens in different phase in most of time
  //  but at f0, they overlaps.
  val mac_array = Array.fill(cfg.rows)(RowAcc(cfg.getMACDatConfig, cfg.cols))
  for(r <- cfg.rowRange){
    mac_array(r).io.data_in.valid := rsd_fft_prod_valid
    mac_array(r).io.data_in.payload := rsd_fft_prod(r)
    when(rsd_fft_prod_valid){
      mac_array(r).io.acc_in_addr := col_addr.value
    } otherwise {
      mac_array(r).io.acc_in_addr := 0
    }
  }

  // **************** pipe out logic ****************
  val pipe_out_start = io.fc_overflow.fall(False)
  val pipe_out_cnt_area = countUpFrom(pipe_out_start, cfg.colRange)
  val pipe_out_col_addr = RegNext(pipe_out_cnt_area.cnt.value)
  pipe_out_col_addr.setName("pipe_out_col_addr")
  val pipe_out_en = RegNext(pipe_out_cnt_area.cond_period) init False
  io.mac_result.valid := pipe_out_en

  for(r <- cfg.rowRange){
    when(pipe_out_en){
      mac_array(r).io.pipe_out_addr := pipe_out_col_addr
    } otherwise {
      mac_array(r).io.pipe_out_addr := 0
    }

    mac_array(r).io.clear := pipe_out_en
    io.mac_result.payload(r) := mac_array(r).io.data_out
  }
  val sim = new Bundle {
    val dc = in UInt(8 bit)
    val fc = in UInt(8 bit)
  }
}
