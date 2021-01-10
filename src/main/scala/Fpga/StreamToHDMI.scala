package Fpga

import spinal.core._
import spinal.lib._

case class StreamToHDMI() extends Component {
  val io = new Bundle {
    val dat_in = slave Stream(UInt(8 bit))
    
  }
}
