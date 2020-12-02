package Core.MacArray

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.lib._

/**
 * For fpga version, accumulation use simple complex arithmetic.
 * @param cfg rsd_kernel_cfg
 */
case class ComplexAccArray(cfg: RsdKernelConfig) extends Component {
  val row_num = cfg.kernel_size.last
  val col_num = cfg.kernel_size.head
  val io = new Bundle {
    val fc_overflow = in Bool()
    val rsd_kernel = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val fft_out = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val mac_result = master(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
  }
  val valid = io.rsd_kernel.valid & io.fft_out.valid

  // Pipeline the complex multiplication for good timing. Trade off between area and timing.
  val rsd_fft_prod = Vec.tabulate(row_num){idx=>
    RegNext(io.rsd_kernel.payload(idx) * io.fft_out.payload(idx))
  }
  val rsd_fft_prod_valid = RegNext(valid) init False

  // Count up for the current column address
  val col_addr_area = countUpInside(rsd_fft_prod_valid, row_num)
  val col_addr = col_addr_area.cnt
  col_addr.setName("col_addr")

  //*************** MAC array *********************
  val mac_array = Array.fill(row_num, col_num)(ComplexAcc(rsd_fft_prod.head.config))
  for(c <- cfg.colRange){
    for(r <- cfg.rowRange){
      mac_array(r)(c).io.data_in.valid := col_addr.value === c
      mac_array(r)(c).io.data_in.payload := rsd_fft_prod(r)
    }
  }

  //************** pipe out logic *******************
  val pipe_out_start = valid.fall(False) & io.fc_overflow.fall(False)
  val pipe_out_cnt_area = countUpFrom(pipe_out_start, 0 until row_num)
  val pipe_out_col_addr = RegNext(pipe_out_cnt_area.cnt.value)
  pipe_out_col_addr.setName("pipe_out_col_addr")
  val pipe_out_en = RegNext(pipe_out_cnt_area.cond_period) init False
  io.mac_result.valid := pipe_out_en
  for(r <- cfg.rowRange){
    io.mac_result.payload(r) := pipe_out_col_addr.muxList(
      HC(0, 0, rsd_fft_prod.head.config),
      for(i <- cfg.colRange) yield i -> mac_array(r)(i).io.data_out
    )
    for(c <- cfg.colRange){
      mac_array(r)(c).io.clear := pipe_out_en & (pipe_out_col_addr === c)
    }
  }

}
