package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._

case class KernelMacArray(cfg: RsdKernelConfig) extends Component {
  val row_num = cfg.kernel_size.last
  val io = new Bundle {
    val rsd_kernel = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val fft_out = slave(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
    val mac_result = master(Flow(Vec(HComplex(cfg.getKernelConfig), row_num)))
  }

  val rsd_kernel = io.rsd_kernel.toReg()
  val fft_out = io.fft_out.toReg()
  val valid = io.rsd_kernel.valid & io.fft_out.valid
  val acc_cnt_area = countUpInside(valid, cfg.kernel_size.head)

  val rsd_fft_prod = Array.tabulate(row_num){idx=>
    RegNext(rsd_kernel(idx) * fft_out(idx))
  }

  val median_regs = Array.fill(row_num)(
    RegInit(HC(0, 0, cfg.getKernelConfig))
  )

  io.mac_result.payload.zipWithIndex.foreach{ case (mac_res, i) =>
    mac_res := rsd_fft_prod(i) + median_regs(i)
  }
  median_regs.zipWithIndex.foreach{ case (complex, i) =>
    complex := io.mac_result.payload(i)
  }
  io.mac_result.valid := acc_cnt_area.last

}
