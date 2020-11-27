package Core.RsdGenCoreArray

import Config._
import Util.MySFix._
import Util._
import spinal.core._
import spinal.lib._

// TODO: More pipeline to meet timing cons
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
//  val indx = io.data_in % fx_period
  val indx = stage(io.data_in % fx_period, 0)

  val (pindx_tb, pfunc_tb) = expfunclutInPeriod(cfg, samplePoint, period)

  val lut_point: Int = pfunc_tb.length
//  val idx_comp: Vector[Bool] = pindx_tb.map(indx < _)
  val idx_comp: Vector[Bool] = pindx_tb.map{pidx=>
    stage(indx < pidx, 1)
  }
  val idx_comp_vec: Bits = B(idx_comp.reverse)
  val lzc_t = stage(CountOne(~idx_comp_vec), 2) // TODO: NegSlack
  val lzc: UInt = lzc_t.resize(log2Up(lut_point))

  val exp_func_value = HComplex(cfg)
  when(lzc =/= U(0)) {
    val position = ( lzc - 1 ).setWeakName("position")
    val y1 = pfunc_tb(position).setWeakName("y1")
    exp_func_value := stage(y1, 3 to 4)
  } elsewhen(lzc_t.msb === True) {
    exp_func_value := stage( pfunc_tb.last, 3 to 4)
  } otherwise {
    exp_func_value := stage( pfunc_tb.head, 3 to 4)
  }

  io.data_out := exp_func_value

  val expLatency = LatencyAnalysis(io.data_in.raw, io.data_out.real.raw)

}

object ExpFunc {
  def exp(idat: SFix, samplePoint: Int = 128, period: Double = 1): HComplex = {
    val func_core = new ExpFunc(
      cfg = HComplexConfig(idat.maxExp + 1, -idat.minExp),
      samplePoint = samplePoint,
      period = period
    ).setWeakName("func_core")
    func_core.io.data_in <> idat
    func_core.io.data_out
  }
}
