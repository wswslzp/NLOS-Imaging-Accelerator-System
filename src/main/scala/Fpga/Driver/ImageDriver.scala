package Fpga.Driver

import Config.RsdKernelConfig._
import Config._
import Util._
import breeze.math.Complex
import spinal.core._
import spinal.lib._

// TODO: Need verification
case class ImageDriver(cfg: RsdKernelConfig) extends Component with DataTransform {
  val io = new Bundle {
    val original_img = master Flow HComplex(cfg.getUinConfig)
    val dc = in UInt (log2Up(cfg.depth_factor) bit)
    val fc = in UInt (log2Up(cfg.freq_factor) bit)
    val fft_comp_end = in Bool()
    val sys_init = in Bool()
  }

  // ************** Memory for image ******
  val image_rom = Array.tabulate(cfg.freq_factor) { freq =>
    val rom_init_content = Seq.tabulate(cfg.kernel_size.product) { idx =>
      val row = idx / cfg.cols
      val col = idx % cfg.cols
      complexToBits(uin(freq)(row, col), cfg.getUinConfig)
    }
    Mem(rom_init_content).addAttribute("ramstyle", "M20K")
  }

  // ************** driver logic **********************
  val pixel_index_cnt = Counter(0, cfg.kernel_size.product)
  val pixel_index_incr = Reg(Bool()).init(False).setWhen(io.sys_init || io.fft_comp_end).clearWhen(pixel_index_cnt.willOverflow)
  when(io.dc === 0) {
    when(pixel_index_incr) {
      pixel_index_cnt.increment()
    }
    when(pixel_index_cnt.willOverflow) {
      pixel_index_cnt.clear()
    }

    // TODO: Timing may be broken
    io.original_img.payload := io.fc.muxList(
      HC(0, 0, cfg.getUinConfig),
      mappings = for (f <- 0 until cfg.freq_factor) yield {
        val ret = HComplex(cfg.getUinConfig)
        ret := image_rom(f).readSync(pixel_index_cnt.value)
        f -> ret
      }
    )
    io.original_img.valid := RegNext(pixel_index_incr) init False
  }
}
