package Core.RsdGenCoreArray

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.core.sim._
import spinal.lib._

//TODO: impulse will be real
case class PRsdKernelGen(cfg: RsdKernelConfig) extends Component {
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val Rlength = cfg.impulse_sample_point
  val io = new Bundle {
    val ring_impulse = in (
      Vec(SFix(cfg.imp_cfg.intw-1 exp, -cfg.imp_cfg.fracw exp), Rlength)
    )
    val wave = in(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp))
    val distance = in(SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp))
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val rsd_prev = in (Vec(HComplex(kernel_cfg), Rlength))
    val rsd_next = out (Vec(HComplex(kernel_cfg), Rlength))
    val impulse_valid = in Bool()
  }

  val coef_gen_core = CoefGenCore(cfg)
  coef_gen_core.io.wave <> io.wave
  coef_gen_core.io.distance <> io.distance
  coef_gen_core.io.timeshift <> io.timeshift
  coef_gen_core.io.coef.simPublic()

  val W2CLatency = LatencyAnalysis(io.wave.raw, coef_gen_core.io.coef.real.raw) + 1

  val delta_rsd_kernel_val = Vec.tabulate(io.ring_impulse.length){idx=>
    coef_gen_core.io.coef * io.ring_impulse(idx)
  }
  delta_rsd_kernel_val.simPublic()
  val delta_rsd_kernel_val_r = RegNext(delta_rsd_kernel_val)
  delta_rsd_kernel_val_r.foreach(_.init(HC(0, 0, io.rsd_prev.head.config)))

//  val rsd_prev_r = RegNextWhen(
//    next = io.rsd_prev,
//    cond = RegNext(io.impulse_valid, False)
//  )
  val rsd_prev_r = Reg(cloneOf(io.rsd_prev))
  when(RegNext(io.impulse_valid, False)){
    rsd_prev_r := io.rsd_prev
  }otherwise {
    rsd_prev_r.foreach(_ := HC(0, 0, io.rsd_prev.head.config))
  }
  rsd_prev_r.foreach(_.init(HC(0, 0, io.rsd_prev.head.config)))
  for(idx <- io.rsd_next.indices){
    io.rsd_next(idx) := rsd_prev_r(idx) + delta_rsd_kernel_val_r(idx)
  }

  val sim = new Bundle {
    val coef = out(HComplex(coef_gen_core.io.coef.config))
    // TODO: watch the rsd_prev_r
    val rsd_prev = out(cloneOf(rsd_prev_r))
  }
  sim.coef := coef_gen_core.io.coef
  sim.rsd_prev := rsd_prev_r
}
