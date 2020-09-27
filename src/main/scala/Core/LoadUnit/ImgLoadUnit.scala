package Core.LoadUnit

import Util.Axi4Slave
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi.Axi4Config

case class ImgLoadUnit
(
  override val axi_config: Axi4Config
) extends Component with Axi4Slave
{
  override val word_bit_count: Int = 32
  val timeout: Timeout = Timeout(4)
}
