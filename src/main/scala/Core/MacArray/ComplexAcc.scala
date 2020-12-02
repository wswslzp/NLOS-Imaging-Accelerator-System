package Core.MacArray

import spinal.core._
import spinal.lib._
import Util._
import Config.HComplexConfig

case class ComplexAcc(cfg: HComplexConfig) extends Component {
  val io = new Bundle {
    val data_in = slave(Flow(HComplex(cfg)))
    val data_out = out(HComplex(cfg))
    val clear = in Bool()
  }
  val result_reg = Reg(cloneOf(io.data_in.payload)) init HC(0, 0, cfg)
  when(io.data_in.valid){
    result_reg := result_reg + io.data_in.payload
  } elsewhen(io.clear) {
    result_reg := HC(0, 0, cfg)
  }
  io.data_out := result_reg

}
