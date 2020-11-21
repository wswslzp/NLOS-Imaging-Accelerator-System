package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Config._
import Util._

// TODO: impulse will be real
case class RsdKernelGen(cfg: RsdKernelConfig) extends Component {
  val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg
  val Rlength = cfg.impulse_sample_point
  val io = new Bundle {
    val ring_impulse = slave(Flow(
      Vec(SFix(cfg.imp_cfg.intw-1 exp, -cfg.imp_cfg.fracw exp), Rlength)
    ))
    val wave = slave(Flow(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)))
    val distance = slave(Flow(SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)))
    val timeshift = slave(Flow(HComplex(cfg.timeshift_cfg)))
    val kernel = master(Flow(
      Vec(HComplex(kernel_cfg), Rlength)
    ))
  }

  val pRsdKernelGen = PRsdKernelGen(cfg)
  pRsdKernelGen.io.ring_impulse := io.ring_impulse.toReg()
  pRsdKernelGen.io.wave := io.wave.toReg()
  pRsdKernelGen.io.timeshift := io.timeshift.toReg()
  pRsdKernelGen.io.distance := io.distance.toReg()
  pRsdKernelGen.io.rsd_prev := pRsdKernelGen.io.rsd_next
  pRsdKernelGen.io.impulse_valid := RegNext(io.ring_impulse.valid, False)
  io.kernel.payload := pRsdKernelGen.io.rsd_next
  io.kernel.valid := Delay( io.ring_impulse.valid.fall(False), 1, init = False )

  val sim = new Bundle {
    val coef = out(HComplex(pRsdKernelGen.sim.coef.config))
    val rsd_prev = out(cloneOf(pRsdKernelGen.sim.rsd_prev))
  }
  sim.coef := pRsdKernelGen.sim.coef
  sim.rsd_prev := pRsdKernelGen.sim.rsd_prev

}
