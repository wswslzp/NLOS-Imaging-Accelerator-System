package Core.RsdGenCoreArray

import Config.RsdKernelConfig
import Util.HComplex
import spinal.core._

//TODO: This module needs to be modified for the new architecture.
case class RsdKernelGen(cfg: RsdKernelConfig) extends Component {
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)
  val Rlength = 1 << log2Up(
    Math.sqrt(
      Math.pow(row_num/2, 2) + Math.pow(col_num/2, 2)
    ).toInt
  )

  val io = new Bundle {
    val ring_impulse = in (
      Vec(HComplex(cfg.imp_cfg), Rlength)
    )
    val wave = in(Vec( SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp) , Rlength))
    val distance = in(SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp))
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
//    val kernel = out( HComplex(kernel_cfg) )
    val kernel_array = out(Vec(HComplex(kernel_cfg), Rlength))
  }

  val prsd_core = Vector.fill(Rlength)(PRsdGenCore(cfg))

  // Unfold in the Rlength dim
  prsd_core.zipWithIndex.foreach { case(core, idx) =>
    core.io.ring_impulse <> io.ring_impulse(idx)
    core.io.wave <> io.wave(idx) // Rlength waves share the same value
    core.io.distance <> io.distance
    core.io.timeshift <> io.timeshift

//    if (idx == 0) {
//      core.io.rsd_prev := 0
//    } else {
//      core.io.rsd_prev <> prsd_core(idx-1).io.rsd_next
//    }
    core.io.rsd_prev <> core.io.rsd_next
    core.io.rsd_next <> io.kernel_array(idx)
  }

//  io.kernel <> prsd_core.last.io.rsd_next

}
