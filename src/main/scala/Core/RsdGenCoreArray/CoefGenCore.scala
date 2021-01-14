package Core.RsdGenCoreArray

import Config._
import Util._
import spinal.core._
import spinal.lib._
import spinal.core.sim._
import Util._

// NOTE: Only add pipeline stage at the output ports
//  Let DC automatically balance the pipeline!
case class CoefGenCore
(
  cfg: RsdKernelConfig
)(implicit val fpgaImpl: FpgaImpl) extends Component with Pipeline {
  implicit val hComplexMulStage = new HComplexMulStage(4)

  val io = new Bundle {
    val wave = in SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val coef = out (HComplex(cfg.coef_cfg))
  }
  var D2Clatency = 0

  def asicImplArea() = new Area {

    val wave = io.wave
    val distance = io.distance

    val wd_prod = wave * distance

    val hCfg = HComplexConfig(wd_prod.maxExp+1, -wd_prod.minExp)
    val exp_func_core = ExpFunc(
      cfg = hCfg, samplePoint = 32, period = 1
    )
    exp_func_core.io.data_in <> wd_prod
    val exp_wd_prod = exp_func_core.io.data_out
    val expLatency = exp_func_core.expLatency

    val exp_wd_prod_divw = exp_wd_prod / wave

    val timeshift = io.timeshift
    val prev_coef = exp_wd_prod_divw * timeshift
    prev_coef.simPublic()

    val output_coef_stage = prev_coef
    val divLatency = 15
    io.coef := stage(output_coef_stage, 0 to (7 + divLatency + expLatency + hComplexMulStage.stage))

    D2Clatency = LatencyAnalysis(io.distance.raw, io.coef.real.raw)

  }.setName("asic_impl")

  def fpgaImplArea() = new Area {

    var wave = io.wave
    wave = stage(wave, 0)
    var distance = io.distance
    distance = stage(distance, 0)

    var wd_prod = wave * distance
    wd_prod = stage(wd_prod, 1)

    val hCfg = HComplexConfig(wd_prod.maxExp+1, -wd_prod.minExp)
    val exp_func_core = ExpFunc(
      cfg = hCfg, samplePoint = 32, period = 1
    )
    exp_func_core.io.data_in <> wd_prod
    var exp_wd_prod = exp_func_core.io.data_out
    val expLatency = exp_func_core.expLatency
//    exp_wd_prod = stage(exp_wd_prod, 2 to (2+expLatency))
    exp_wd_prod = stage(exp_wd_prod, 2+expLatency)

    wave = stage(wave, 1 to (2+expLatency))
//    var exp_wd_prod_divw = exp_wd_prod / wave //todo divider is too long.
    var exp_wd_prod_divw = exp_wd_prod./(wave)(new Synthesizable(true))
    val divLatency = 8
    exp_wd_prod_divw = stage(exp_wd_prod_divw, 3+expLatency+divLatency)

    var timeshift = io.timeshift
    timeshift = stage(timeshift, 0 to 3+expLatency+divLatency)
    val prev_coef = exp_wd_prod_divw * timeshift
    prev_coef.simPublic()

    val output_coef_stage = prev_coef
    io.coef := stage(output_coef_stage, 4+expLatency+divLatency)

    D2Clatency = 4+expLatency+divLatency
  }.setName("fpga_impl")

  openAllStage()

  if(fpgaImpl) fpgaImplArea()
  else asicImplArea()

//  val D2Clatency = LatencyAnalysis(io.distance.raw, io.coef.real.raw)

  //  val sim = new Bundle {
//    val exp_wd_prod_s = out(cloneOf(exp_wd_prod))
//    val exp_wd_prod_divw_s = out(cloneOf(exp_wd_prod_divw))
//    val prev_coef_s = out(cloneOf(prev_coef))
//  }
//  sim.exp_wd_prod_s := exp_wd_prod
//  sim.exp_wd_prod_divw_s := exp_wd_prod_divw
//  sim.prev_coef_s := prev_coef
}
