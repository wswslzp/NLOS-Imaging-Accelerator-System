package Core.PostProc

import spinal.core._
import spinal.lib._
import Config._
import Util.MyUFix._

case class PixelQuant(cfg: HComplexConfig, quant_bw: Int) extends Component {
  val pixel_width = cfg.getComplexWidth
  val pixel_maxExp = Math.max(cfg.intw, quant_bw+1) exp
  val pixel_minExp = -cfg.fracw exp
  val color_depth = UF(1 << quant_bw, pixel_maxExp, pixel_minExp)

  val io = new Bundle {
    val pix_in = slave Flow UFix(pixel_maxExp, pixel_minExp)
    val upper_bound = in UFix(pixel_maxExp, pixel_minExp)
    val lower_bound = in UFix(pixel_maxExp, pixel_minExp)
    val pix_out = master Flow UInt(quant_bw bit)
  }

  val pix_inte_range = io.upper_bound - io.lower_bound //+ UF(1, pixel_maxExp, pixel_minExp)
  val pixel_quant_coef = color_depth / pix_inte_range

  val pix_in_rela_inte = io.pix_in.translateWith(io.pix_in.payload - io.lower_bound).stage()
  val out_pix = pix_in_rela_inte.payload * pixel_quant_coef
  val out_pix1 = out_pix.toUInt
  val out_pix2 = out_pix1.resize(quant_bw).asBits.asUInt
  io.pix_out << pix_in_rela_inte.translateWith(out_pix2).stage()

}
