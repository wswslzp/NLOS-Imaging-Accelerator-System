package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi._
import RsdKernelConfig._
import breeze.math.Complex

case class NlosDriver(cfg: RsdKernelConfig) extends Component {
  private def complexToBits(dat: Complex, cfg: HComplexConfig): Bits = {
    val ret = Bits(cfg.getComplexWidth bit)
    val real_p = ( dat.real * scala.math.pow(2, cfg.fracw) ).toInt
    val imag_p = ( dat.imag * scala.math.pow(2, cfg.fracw) ).toInt
    ret := if(cfg.real_high){
      B(real_p) ## B(imag_p)
    }
    ret
  }

  val io = new Bundle {
    val original_img = master Flow HComplex(cfg.getUinConfig)
    val kernel_in = master(Axi4WriteOnly(axi_config))
    val dc = out UInt(log2Up(cfg.depth_factor) bit)
    val fc = out UInt(log2Up(cfg.freq_factor) bit)
    val load_req = in Bits(4 bit)
    val cnt_incr = in Bool()
    val fft_comp_end = in Bool()
    val sys_init = in Bool()
  }

  // ************** Counting for depth freq **************
  val depth_cnt = Counter(0, cfg.depth_factor)
  val freq_cnt = Counter(0, cfg.freq_factor)
  when(io.cnt_incr){
    freq_cnt.increment()
  }
  when(freq_cnt.willIncrement){
    depth_cnt.increment()
  }
  io.dc := depth_cnt
  io.fc := freq_cnt


}
