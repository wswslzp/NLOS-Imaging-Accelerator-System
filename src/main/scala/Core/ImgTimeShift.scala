package Core

import spinal.core._
import spinal.lib._
import Config.ImgTimeShiftConfig
import Util.HComplex


case class ImgTimeShift(cfg: ImgTimeShiftConfig) extends Component {

  val out = new Bundle {
    val pixel = master (
      Flow(Vec(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor), cfg.freq_factor))
    )
  }

  if (cfg.do_time_shift) {
    val in = new Bundle {
      val depth = slave (
        Flow(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor))
      )
      val freq  = slave (
        Flow(Vec(HComplex(cfg.hComplexConfig), cfg.freq_factor))
      )
      val pixel = slave (
        Flow(Vec(HComplex(cfg.hComplexConfig), cfg.freq_factor))
      )
    }

  }

  else {
    val in = new Bundle {
      val time_shift = slave (
        Flow(Vec(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor), cfg.freq_factor))
      )
      val pixel = slave (
        Flow(Vec(HComplex(cfg.hComplexConfig), cfg.freq_factor))
      )
    }
  }

}
