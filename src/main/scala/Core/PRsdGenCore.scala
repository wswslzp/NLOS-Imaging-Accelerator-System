package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._

// Partial RSD kernel generator core
case class PRsdGenCore(cfg: RsdKernelConfig) extends Component {
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val io = new Bundle {
    val ring_impulse = in (HComplex(cfg.imp_cfg))
    val rsd_prev = in (HComplex(kernel_cfg))
    val wave = in SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val rsd_next = out (HComplex(kernel_cfg))
  }

  val coef_gen_core = CoefGenCore(cfg)
  coef_gen_core.io.wave <> io.wave
  coef_gen_core.io.distance <> io.distance
  coef_gen_core.io.timeshift <> io.timeshift

  val delta_rsd_kernel_val = RegNext( coef_gen_core.io.coef * io.ring_impulse )
  io.rsd_next := RegNext( io.rsd_prev ) + delta_rsd_kernel_val

}

