package Core.PostProc

import spinal.core._
import spinal.lib._
import Config._
import Util.MyUFix._
import Util._

case class PixelQuant(cfg: HComplexConfig, quant_bw: Int)(implicit val fpgaImpl: FpgaImpl) extends Component {
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
  var pixel_quant_coef: UFix = null
  if(fpgaImpl){
    // pipeline with 16
    pixel_quant_coef = color_depth./(pix_inte_range)(new Synthesizable(true))
  } else {
//    pixel_quant_coef = color_depth / pix_inte_range
    pixel_quant_coef = Delay(color_depth/pix_inte_range, 8)
  }

  var pix_in_rela_inte = io.pix_in.translateWith(io.pix_in.payload - io.lower_bound).stage()
  for(_ <- 0 until 15){
    pix_in_rela_inte = pix_in_rela_inte.stage()
  }
  val out_pix = pix_in_rela_inte.payload * pixel_quant_coef
  val out_pix1 = out_pix.toUInt
  val out_pix2 = out_pix1.resize(quant_bw).asBits.asUInt
  io.pix_out << pix_in_rela_inte.translateWith(out_pix2).stage()

}
