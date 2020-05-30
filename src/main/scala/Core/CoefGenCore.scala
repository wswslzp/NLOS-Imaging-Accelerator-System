package Core
import spinal.core._
import spinal.lib._
import Config.{HComplexConfig, RsdKernelConfig}
import Util._

case class CoefGenCore
(
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  coef_cfg: HComplexConfig,
  expfunc_point: Int,
  expfunc_range: (Double, Double),
  pipeline_lv: Int
) extends Component with Pipeline {
  import Util.MySFix._

  val io = new Bundle {
    val wave = in SFix(wave_cfg.intw-1 exp, -wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(distance_cfg.intw-1 exp, -distance_cfg.fracw exp)
    val timeshift = in ( HComplex(timeshift_cfg) )
    val coef = out (HComplex(coef_cfg))
  }

  val func_table = expfunclut(wave_cfg, expfunc_point, expfunc_range)

  setPipelineLevel(pipeline_lv)
  openAllStage()

  val wave = stage(io.wave, 0)
  val distance = stage(io.distance, 0)
  val timeshift = stage(io.timeshift, 0 to 4)

  //TODO: quantize Q8.8 here may cause some loss in accuracy.
  val wd_prod = stage(wave * distance, 1).fixTo(SQ(wave_cfg.intw, wave_cfg.fracw))

  val lut_point = func_table.length
  val lut_interval = ( 1 << func_table(0).config.getDataWidth ) / lut_point
  val indx_table: Vector[UInt] = (0 until lut_point).toVector.map(_ * lut_interval).map(U(_, func_table(0).config.getDataWidth bit))
  val idx_comp: Vector[Bool] = indx_table.map(wd_prod.asBits.asUInt < _)
  val lzc = stage( countLeadingZeros(idx_comp), 2).resize(log2Up(lut_point))

//  val position = UInt(log2Up(wd_prod.getBitsWidth) bit)

  val exp_wd_prod_cfg = HComplexConfig(
    intw = wave_cfg.intw + distance_cfg.intw, fracw = wave_cfg.fracw + distance_cfg.fracw
  )
  val exp_wd_prod = HComplex(exp_wd_prod_cfg)
  when(lzc =/= U(0) && lzc < indx_table.length ) {
    val position = lzc - 1
    val x1_t = Vec(indx_table)(position)
    val x1 = cloneOf(wd_prod)
    x1.assignFromBits(x1_t.asBits)
    val x2_t = Vec(indx_table)(lzc)
    val x2 = cloneOf(wd_prod)
    x2.assignFromBits(x2_t.asBits)
    val y1 = func_table(position)
    val y2 = func_table(lzc)
    exp_wd_prod := stage( linearInterpolate(wd_prod,x1,x2, y1, y2), 3 )
  } elsewhen(lzc === U(0)) {
    exp_wd_prod := stage( func_table.head , 3 )
  } otherwise {
    exp_wd_prod := stage( func_table.last, 3 )
  }

  val exp_wd_prod_divw = stage( exp_wd_prod / stage(wave, 1 to 3) , 4)

  io.coef := stage( exp_wd_prod_divw * timeshift , 5)

}
