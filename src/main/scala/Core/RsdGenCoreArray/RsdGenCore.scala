package Core.RsdGenCoreArray

import Config._
import Util.HComplex
import spinal.core._
import spinal.lib._

case class RsdGenCore(cfg: RsdKernelConfig) extends Component {
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val io = new Bundle {
    val ring_impulse = slave(Flow(HComplex(cfg.imp_cfg)))
    val wave = slave(Flow(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)))
    val distance = slave(Flow(SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)))
    val timeshift = slave(Flow(HComplex(cfg.timeshift_cfg)))
    val kernel = master(Flow(HComplex(kernel_cfg)))
  }

  val prsd_core = PRsdGenCore(cfg)

  prsd_core.io.wave := io.wave.toReg()
  prsd_core.io.ring_impulse := io.ring_impulse.toReg()
  prsd_core.io.distance := io.distance.toReg()
  prsd_core.io.timeshift := io.timeshift.toReg()
  prsd_core.io.rsd_prev := prsd_core.io.rsd_next
  prsd_core.io.impulse_valid := RegNext(io.ring_impulse.valid, False)
  io.kernel.payload := prsd_core.io.rsd_next
  // The impulse valid last for whole radius_factor cycles
  // So its falling edge pulse locate exactly on the kernel valid (another cycle introduce by impulse*coef)
  io.kernel.valid := Delay( io.ring_impulse.valid.fall(False), 1, init = False )

//  val W2KLatency = LatencyAnalysis(io.ring_impulse.payload.real.raw, )

}
