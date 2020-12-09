package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Util._

/**
 * Post-process of the final result. Pipe out two row per cycle because
 * up-sampling by 2. Output is 8-bit gray pixel.
 * @param cfg rsd_kernel_cfg
 */
case class PostProcess(cfg: RsdKernelConfig) extends Component{
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last
  val io = new Bundle {
    val ifft_in = slave(Flow(
      Vec(HComplex(cfg.getResultConfig), col_num)
    ))
    val result_out1 = master(Flow(
      Vec(UInt(8 bit), col_num)
    ))
    val result_out2 = master(Flow(
      Vec(UInt(8 bit), col_num)
    ))
  }

  val ifft_in_1 = io.ifft_in.stage()
  val ifft_in_abs = ifft_in_1.translateWith(Vec.tabulate(ifft_in_1.payload.length){idx=>
    val realp = ifft_in_1.payload(idx).real
    val imagp = ifft_in_1.payload(idx).imag
    val abs_real = (realp < 0) ? (-realp.asBits.asSInt) | realp.asBits.asSInt
    val abs_imag = (imagp < 0) ? (-imagp.asBits.asSInt) | imagp.asBits.asSInt
    abs_real + abs_imag
  })

}
