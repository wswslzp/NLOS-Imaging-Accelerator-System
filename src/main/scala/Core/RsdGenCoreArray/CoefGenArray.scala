package Core.RsdGenCoreArray

import Config._
import Util._
import spinal.core._
import spinal.lib._

// This module is deprecated due to new architecture
@deprecated
case class CoefGenArray
(
  cfg: RsdKernelConfig,
  freq_num: Int
) extends Component {

  val io = new Bundle {
    val wave_front = slave ( Flow(
      Vec(Vec(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp), cfg.depth_factor), cfg.radius_factor)
    ) )
    val distance = slave(Flow(
      Vec(Vec(SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp), cfg.depth_factor), freq_num)
    ))
    val timeshift = slave(Flow(
      Vec(Vec(HComplex(cfg.timeshift_cfg), cfg.depth_factor), freq_num)
    ))
    val coef = master(Flow(
      Vec(Vec(Vec(HComplex(cfg.coef_cfg), cfg.depth_factor), cfg.radius_factor), freq_num)
    ))
  }

  val wave_front = io.wave_front.toReg()
  val distance = io.distance.toReg()
  val timeshift = io.timeshift.toReg()

  for {
    f <- 0 until freq_num
    r <- 0 until cfg.radius_factor
    d <- 0 until cfg.depth_factor
  } {
    val core = CoefGenCore(cfg)
    core.io.wave := wave_front(r)(d)
    core.io.distance := distance(f)(d)
    core.io.timeshift := timeshift(f)(d)
    io.coef.payload(f)(r)(d) := core.io.coef
  }

  io.coef.valid := Delay(
    io.wave_front.valid & io.distance.valid & io.timeshift.valid,
    6
  )

}
