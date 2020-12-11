package Core.PostProc

import spinal.core._
import spinal.lib._

//TODO: co-design with HDMI interface logic
case class OverSampling(color_width: Int, over_sampling_factor: Int = 2) extends Component {
  val io = new Bundle {

  }
}
