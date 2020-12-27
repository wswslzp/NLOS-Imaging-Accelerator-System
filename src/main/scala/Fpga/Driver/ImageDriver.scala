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
    val cnt_incr = in Bool()
    val img_push_start = in Bool()
  }

  // ************** Memory for image ******
  val image_part_rom = Array.tabulate(cfg.freq_factor) { freq =>
    val rom_init_content = Seq.tabulate(cfg.kernel_size.product) { idx =>
      val row = idx / cfg.cols
      val col = idx % cfg.cols
      complexToSInt(uin(freq)(row, col), cfg.getUinConfig)
    }
    Mem(rom_init_content.map(dat=> B(dat, cfg.getUinConfig.getComplexWidth bit)))
  }

  // ************** driver logic **********************
  val pixel_index_cnt = Counter(0 until cfg.kernel_size.product)
  // todo :
  val pixel_index_incr = RegInit(False)
  pixel_index_incr.setWhen(io.img_push_start.rise(False) || io.cnt_incr)
  pixel_index_incr.clearWhen(pixel_index_cnt.willOverflow || (io.dc =/= 0))
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
        val addr = pixel_index_cnt.value
        val dat = image_part_rom(f).readSync(addr.resized)
        ret := dat
        f -> ret
      }
    )
//    io.original_img.valid := RegNext(pixel_index_incr) init False
    io.original_img.valid := pixel_index_incr
  } otherwise {
    io.original_img.valid.clear()
    io.original_img.payload := HC(0, 0, cfg.getUinConfig)
  }
}
