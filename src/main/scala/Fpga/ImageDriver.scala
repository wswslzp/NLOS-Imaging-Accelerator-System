package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Util._
import RsdKernelConfig._
import breeze.math.Complex

case class ImageDriver(cfg: RsdKernelConfig) extends Component {
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
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val fft_comp_end = in Bool()
    val sys_init = in Bool()
  }

  // ************** Memory for image ******
  val image_rom = Array.tabulate(cfg.freq_factor){freq=>
    Mem(Array.tabulate(cfg.kernel_size.product){idx=>
      val row = idx / cfg.rows
      val col = idx % cfg.rows
      complexToBits(uin(freq)(row, col), cfg.getUinConfig)
    })
  }

  // ************** driver logic **********************
  val pixel_index_cnt = Counter(0, cfg.kernel_size.product)
  val pixel_index_incr = Bool().setWhen(io.sys_init || io.fft_comp_end).clearWhen(pixel_index_cnt.willOverflow)
  when(io.dc === 0){
    when(pixel_index_incr){
      pixel_index_cnt.increment()
    }
    when(pixel_index_cnt.willOverflow){
      pixel_index_cnt.clear()
    }

    // TODO: Timing may be broken
    io.original_img.payload := io.fc.muxList(
      HC(0, 0, cfg.getUinConfig),
      mappings = for(f <- 0 until cfg.freq_factor) yield {
        val ret = HComplex(cfg.getUinConfig)
        ret := image_rom(f)(pixel_index_cnt.value)
        f -> ret
      }
    )
    io.original_img.valid := pixel_index_incr
  }
}
