package Core.RsdGenCoreArray

import Config._
import Util._
import spinal.core._
import spinal.lib._
import spinal.core.sim._

// NOTE: Only add pipeline stage at the output ports
//  Let DC automatically balance the pipeline!
case class CoefGenCore
(
  cfg: RsdKernelConfig
) extends Component with Pipeline {
  implicit val hComplexMulStage = new HComplexMulStage(4)

  val io = new Bundle {
    val wave = in SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val coef = out (HComplex(cfg.coef_cfg))
  }

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
    // TODO: For FPGA, No pipeline retiming technology.
    //  All pipeline should be handcrafted.
    io.coef := stage(output_coef_stage, 0 to (7 + expLatency + hComplexMulStage.stage))

    val D2Clatency = LatencyAnalysis(io.distance.raw, io.coef.real.raw)
  }.setName("asic_impl")

  def fpgaImplArea() = new Area {

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
    // TODO: For FPGA, No pipeline retiming technology.
    //  All pipeline should be handcrafted.
    io.coef := stage(output_coef_stage, 0 to (7 + expLatency + hComplexMulStage.stage))

    val D2Clatency = LatencyAnalysis(io.distance.raw, io.coef.real.raw)

  }.setName("fpga_impl")

  openAllStage()

  if(cfg.fpga_impl) fpgaImplArea()
  else asicImplArea()

  val D2Clatency = LatencyAnalysis(io.distance.raw, io.coef.real.raw)

//  val sim = new Bundle {
//    val exp_wd_prod_s = out(cloneOf(exp_wd_prod))
//    val exp_wd_prod_divw_s = out(cloneOf(exp_wd_prod_divw))
//    val prev_coef_s = out(cloneOf(prev_coef))
//  }
//  sim.exp_wd_prod_s := exp_wd_prod
//  sim.exp_wd_prod_divw_s := exp_wd_prod_divw
//  sim.prev_coef_s := prev_coef
}
