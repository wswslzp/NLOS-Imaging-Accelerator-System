package Core.RsdGenCoreArray

import Config.RsdKernelConfig
import Util.HComplex
import spinal.core._

case class RsdKernelGen(config: RsdKernelConfig) extends Component {
  val kernel_cfg = config.coef_cfg * config.imp_cfg
  val io = new Bundle {
    val ring_impulse = in (
      Vec(HComplex(config.imp_cfg), config.radius_factor)
    )
    val wave = in(Vec( SFix(config.wave_cfg.intw-1 exp, -config.wave_cfg.fracw exp) , config.radius_factor))
    val distance = in(SFix(config.distance_cfg.intw-1 exp, -config.distance_cfg.fracw exp))
    val timeshift = in (
      HComplex(config.timeshift_cfg)
    )
    val kernel       = out(
      HComplex(kernel_cfg)
    )
  }

  val prsd_core = Vector.fill(config.radius_factor)(PRsdGenCore(config))

  prsd_core.zipWithIndex.foreach { case(core, idx) =>
    core.io.ring_impulse <> io.ring_impulse(idx)
    core.io.wave <> io.wave(idx)
    core.io.distance <> io.distance
    core.io.timeshift <> io.timeshift

    if (idx == 0) {
      core.io.rsd_prev := 0
    } else {
      core.io.rsd_prev <> prsd_core(idx-1).io.rsd_next
    }
  }

  io.kernel <> prsd_core.last.io.rsd_next

}
