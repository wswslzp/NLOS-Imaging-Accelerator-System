package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Core.RsdGenCoreArray.RsdKernelGen
import Util._

// TODO: What's the fpga version?
case class RsdGenCoreArray(cfg: RsdKernelConfig) extends Component{
  val Rlength = cfg.impulse_sample_point

  val io = new Bundle {
    val wave = master(Flow(
      SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)
    ))
    val timeshift = master (Flow(
      HComplex(cfg.timeshift_cfg)
    ))
    val distance = master (Flow(
      SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    ))
    val impulse = master (
      Flow(
        Vec(SFix(cfg.imp_cfg.intw-1 exp, -cfg.imp_cfg.fracw exp), Rlength)
      )
    )
  }

  val rsd_kernel_gen = RsdKernelGen(cfg)
  rsd_kernel_gen.io.wave <> io.wave
  rsd_kernel_gen.io.timeshift <> io.timeshift
  rsd_kernel_gen.io.distance <> io.distance
  rsd_kernel_gen.io.ring_impulse <> io.impulse
}
