package Core

import spinal.core._
import spinal.lib._
import Config.ImgTimeShiftConfig
import Util.HComplex

// timeslice is about 1e-9.
case class TimeSlice
(
  cfg: ImgTimeShiftConfig,
  c_light: Int
) extends Component
{
  val io = new Bundle {
    val depth_in = slave ( // depth is unsigned fixed point number
      Flow(Vec(UInt(cfg.hComplexConfig.getDataWidth bit), cfg.depth_factor))
    )
    val slice_out= master(
      Flow(Vec(UInt(cfg.hComplexConfig.getDataWidth bit), cfg.depth_factor))
    )
  }

  val a = UInt(8 bits).tag(SQ(8, 8))
  val b = UFix(8 exp, -8 exp)



}
