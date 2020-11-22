package Core.RsdGenCoreArray

import Config._
import Util.MySFix._
import Util._
import spinal.core._
import spinal.lib._

case class ExpFunc
(
  cfg: HComplexConfig,
  samplePoint: Int,
  period: Double
) extends Component with Pipeline
{
  val io = new Bundle {
    val data_in = in(SFix(cfg.maxExp, cfg.minExp))
    val data_out = out(HComplex(cfg))
  }

  val fx_period = SF(period, cfg.maxExp, cfg.minExp)
  val indx = io.data_in % fx_period

  val (pindx_tb, pfunc_tb) = expfunclutInPeriod(cfg, samplePoint, period)

  val lut_point: Int = pfunc_tb.length
  val idx_comp: Vector[Bool] = pindx_tb.map(indx < _)
  val idx_comp_vec: Bits = B(idx_comp.reverse)
  val lzc_t = stage(CountOne(~idx_comp_vec), 0)
  val lzc: UInt = lzc_t.resize(log2Up(lut_point))

  val exp_func_value = HComplex(cfg)
  when(lzc =/= U(0)) {
    val position = ( lzc - 1 ).setWeakName("position")
    val x1_t = Vec(pindx_tb)(position)
    val x1 = cloneOf(indx).setWeakName("x1")
    x1.assignFromBits(x1_t.asBits)
    val x2_t = Vec(pindx_tb)(lzc)
    val x2 = cloneOf(indx).setWeakName("x2")
    x2.assignFromBits(x2_t.asBits)
    val y1 = pfunc_tb(position).setWeakName("y1")
    val y2 = pfunc_tb(lzc).setWeakName("y2")
    val indx_r = stage(indx, 1)
//    exp_func_value := stage( linearInterpolate(indx_r,x1,x2, y1, y2).setWeakName("pre_exp_indx"), 2 )
//    exp_func_value := stage( nearestInterpolate(indx_r,x1,x2, y1, y2).setWeakName("pre_exp_indx"), 2 )
    exp_func_value := stage( meanApprox(y1, y2), 2 )
  } elsewhen(lzc_t.msb === True) {
    exp_func_value := stage( pfunc_tb.last, 1 to 2 )
  } otherwise {
    exp_func_value := stage( pfunc_tb.head, 1 to 2 )
  }

  io.data_out := exp_func_value

}

object ExpFunc {
  def apply(idat: SFix, samplePoint: Int = 128, period: Double = 1): HComplex = {
    val func_core = new ExpFunc(
      cfg = HComplexConfig(idat.maxExp + 1, -idat.minExp),
      samplePoint = samplePoint,
      period = period
    ).setWeakName("func_core")
    func_core.io.data_in <> idat
    func_core.io.data_out
  }
}
