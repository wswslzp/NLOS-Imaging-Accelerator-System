package Core.RsdGenCoreArray

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.lib._

// Partial RSD kernel generator core
case class PRsdGenCore(cfg: RsdKernelConfig) extends Component {
  import spinal.core.sim._
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val io = new Bundle {
    val ring_impulse = in(SFix(cfg.imp_cfg.intw-1 exp, -cfg.imp_cfg.fracw exp))
    val rsd_prev = in (HComplex(kernel_cfg))
    val rsd_next = out (HComplex(kernel_cfg))
    val wave = in SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)
    val distance = in SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val impulse_valid = in Bool()
  }

  val coef_gen_core = CoefGenCore(cfg)
  coef_gen_core.io.wave <> io.wave
  coef_gen_core.io.distance <> io.distance
  coef_gen_core.io.timeshift <> io.timeshift
  coef_gen_core.io.coef.simPublic()

  // Get the latency between timeshift.valid and coef
  val W2CLatency = LatencyAnalysis(io.wave.raw, coef_gen_core.io.coef.real.raw) + 1

  val delta_rsd_kernel_val = coef_gen_core.io.coef * io.ring_impulse
  delta_rsd_kernel_val.simPublic()
  val delta_rsd_kernel_val_r = RegNext(delta_rsd_kernel_val) init HC(0, 0, io.rsd_prev.config)

  val rsd_prev_r = RegNextWhen(
    next = io.rsd_prev,
    cond = RegNext(io.impulse_valid, False),
    init = HC(0, 0, io.rsd_prev.config)
  )
  io.rsd_next := rsd_prev_r + delta_rsd_kernel_val_r

}
