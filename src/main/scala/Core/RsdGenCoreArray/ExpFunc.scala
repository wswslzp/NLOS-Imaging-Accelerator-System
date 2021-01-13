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
)(implicit val fpgaImpl: FpgaImpl) extends Component with Pipeline
{
  val io = new Bundle {
    val data_in = in(SFix(cfg.maxExp, cfg.minExp))
    val data_out = out(HComplex(cfg))
  }

  def asicImplArea() = new Area {

    val fx_period = SF(period, cfg.maxExp, cfg.minExp)
//    val indx = io.data_in % fx_period
    val indx = io.data_in %% cfg.fracw

    val (pindx_tb, pfunc_tb) = expfunclutInPeriod(cfg, samplePoint, period)

    val lut_point: Int = pfunc_tb.length
    val idx_comp = pindx_tb.map(indx < _)
    val idx_comp_vec: Bits = B(idx_comp.reverse)
    val lzc_t = CountOne(~idx_comp_vec)
    val lzc: UInt = lzc_t.resize(log2Up(lut_point))

    val exp_func_value = HComplex(cfg)
    when(lzc =/= U(0)) {
      val position = ( lzc - 1 ).setWeakName("position")
      val y1 = pfunc_tb(position).setWeakName("y1")
      exp_func_value := y1
    } elsewhen(lzc_t.msb === True) {
      exp_func_value := pfunc_tb.last
    } otherwise {
      exp_func_value := pfunc_tb.head
    }

    io.data_out := exp_func_value

  }.setName("asic_impl")

  def fpgaImplArea() = new Area {

    val fx_period = SF(period, cfg.maxExp, cfg.minExp)
    val data_in = stage(io.data_in, 0)
//    var indx = io.data_in % fx_period // todo: mod can be replace
    var indx = io.data_in %% cfg.fracw
    indx = stage(indx, 1)

    val (pindx_tb, pfunc_tb) = expfunclutInPeriod(cfg, samplePoint, period)

    val lut_point: Int = pfunc_tb.length
    val idx_comp = pindx_tb.map(indx < _)
    var idx_comp_vec: Bits = B(idx_comp.reverse)
    idx_comp_vec = stage(idx_comp_vec, 2)
    val lzc_t = CountOne(~idx_comp_vec)
    var lzc: UInt = lzc_t.resize(log2Up(lut_point))
    lzc = stage(lzc, 3)

    val exp_func_value = HComplex(cfg)
    when(lzc =/= U(0)) {
      val position = ( lzc - 1 ).setWeakName("position")
      val y1 = pfunc_tb(position).setWeakName("y1")
      exp_func_value := y1
    } elsewhen(lzc_t.msb === True) {
      exp_func_value := pfunc_tb.last
    } otherwise {
      exp_func_value := pfunc_tb.head
    }

    io.data_out := stage(exp_func_value, 4)

  }.setName("fpga_impl")

  if(fpgaImpl) fpgaImplArea()
  else asicImplArea()

  val expLatency = 5

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
