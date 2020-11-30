package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Config._
import Core.LoadUnit._
import spinal.lib.bus.amba4.axi.Axi4Config

case class RsdGenCoreArray(cfg: RsdKernelConfig, initAddr: Int)(implicit val axi_config: Axi4Config) extends Component {

}
