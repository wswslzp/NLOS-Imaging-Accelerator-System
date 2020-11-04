package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._

case class KernelMacArray(cfg: RsdKernelConfig) extends Component {
  val row_num = cfg.kernel_size.last
  val col_num = cfg.kernel_size.head
  val io = new Bundle {
    val fc_eq_0 = in Bool()
    val rsd_kernel = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val fft_out = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val mac_result = master(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
  }

  val rsd_kernel = io.rsd_kernel.toReg()
  val fft_out = io.fft_out.toReg()
  val valid = RegNext(io.rsd_kernel.valid & io.fft_out.valid, init = False)

  // Count up for the current column address
  val col_addr_area = countUpInside(valid, cfg.kernel_size.head)
  val col_addr = col_addr_area.cnt
  col_addr.setName("col_addr")

  // Pipeline the complex multiplication for good timing. Trade off between area and timing.
  val rsd_fft_prod = Array.fill(row_num){
    Vec.fill(col_num)(Reg(HComplex(cfg.getKernelConfig)))
  }
  for(row <- 0 until row_num){
    rsd_fft_prod(row)(col_addr.value.resized) := rsd_kernel(row) * fft_out(row)
  }

  // Store the median accumulation results.
  // TODO: Should I merge these registers with previous pipeline registers??
  val col_addr_1 = RegNext(col_addr.value)
  val acc_enable = RegNext(valid) init False
  val acc_regs = Array.fill(row_num){
    Vec.fill(col_num)(
      RegInit(HC(0, 0, cfg.getKernelConfig))
    )
  }
  when(acc_enable){
    for(row <- 0 until row_num) {
      acc_regs(row)(col_addr_1) := acc_regs(row)(col_addr_1) + rsd_fft_prod(row)(col_addr_1)
    }
  }

  // When fc == 0, Last accumulation result will be completely prepared and waiting for
  //  pipe out. From column 0 to column (col num - 1), one column per cycle.
  //  after one column has been piped out, this column will be clear.
  val fc_eq_0_rise = io.fc_eq_0.rise(False)
  val pipe_out_col_addr_area = countUpFrom(fc_eq_0_rise, 0 until col_num)
  val pipe_out_col_addr = pipe_out_col_addr_area.cnt
  pipe_out_col_addr.setName("pipe_out_col_addr")
  val pipe_out_enable = pipe_out_col_addr_area.cond_period
  pipe_out_enable.setName("pipe_out_enable")
  when(pipe_out_enable){
    for(row <- 0 until row_num) {
      acc_regs(row)(pipe_out_col_addr.value) := HC(0, 0, cfg.getKernelConfig)
    }
  }
  io.mac_result.valid := pipe_out_enable
  for(row <- 0 until row_num) {
    io.mac_result.payload(row) := acc_regs(row)(pipe_out_col_addr.value)
  }

}
