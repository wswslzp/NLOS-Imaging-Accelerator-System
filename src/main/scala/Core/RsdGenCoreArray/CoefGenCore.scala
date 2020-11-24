package Core.RsdGenCoreArray

import Config._
import Util._
import spinal.core._
import spinal.lib._
import spinal.core.sim._

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

  openAllStage()

  val wave = stage(io.wave, 0) //A
  val distance = stage(io.distance, 0) //B
  val timeshift = stage(io.timeshift, 0 to 4) //C
//  val timeshift = stage(io.timeshift, 0 to 3) //C

  val wd_prod = stage(wave * distance, 1)

  val exp_wd_prod = ExpFunc(wd_prod, samplePoint = 32) // with 2 stage pipeline inside.
  exp_wd_prod.setWeakName("exp_wd_prod")

  val exp_wd_prod_divw = stage( exp_wd_prod / stage(wave, 1 to 3) , 4) // D
//  val exp_wd_prod_divw = stage( exp_wd_prod / stage(wave, 1 to 2) , 3) // D

//  val prev_coef = exp_wd_prod_divw * timeshift // TODO: Timing path too long
  val prev_coef = exp_wd_prod_divw *\* timeshift
  prev_coef.simPublic()

  io.coef := stage( prev_coef , 5)

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
