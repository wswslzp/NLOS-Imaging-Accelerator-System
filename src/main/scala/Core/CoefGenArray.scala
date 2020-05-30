package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._

// The fixed point configuration is different.
// A(wave_front) and B(distance) is fine in Q8.8, but C(timeshift) is too small to fit in Q8.8, instead in Q-4.20.
// D is fine in Q8.8 whereas the output coefs is too small, fits in Q0.16~Q-4.20

case class CoefGenArray
(
  cfg: RsdKernelConfig,
  freq_num: Int,
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  expfunc_point: Int,
  expfunc_range: (Double, Double)
) extends Component {

  val io = new Bundle {
    val wave_front = slave ( Flow(
      Vec(Vec(SFix(wave_cfg.intw-1 exp, -wave_cfg.fracw exp), cfg.depth_factor), cfg.radius_factor)
    ) )
    val distance = slave(Flow(
      Vec(Vec(SFix(distance_cfg.intw-1 exp, -distance_cfg.fracw exp), cfg.depth_factor), freq_num)
    ))
    val timeshift = slave(Flow(
      Vec(Vec(HComplex(timeshift_cfg), cfg.depth_factor), freq_num)
    ))
    val coef = master(Flow(
      Vec(Vec(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor), cfg.radius_factor), freq_num)
    ))
  }

  val wave_front = io.wave_front.toReg()
  val distance = io.distance.toReg()
  val timeshift = io.timeshift.toReg()

  // TODO: how many points is the size of expfunc lut?
  // TODO: What range is appropriate?

  for {
    f <- 0 until freq_num
    r <- 0 until cfg.radius_factor
    d <- 0 until cfg.depth_factor
  } {
    val core = CoefGenCore(
      wave_cfg = wave_cfg,
      distance_cfg = distance_cfg,
      timeshift_cfg = timeshift_cfg,
      coef_cfg = cfg.hComplexConfig,
      expfunc_point = expfunc_point,
      expfunc_range = expfunc_range,
      pipeline_lv = 6
    )
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
