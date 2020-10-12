package Core.FFT2d

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

case class FFT2dCore(cfg: FFTConfig) extends Component {
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(cfg.hComplexConfig)))
    val data_out = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
  }



}
