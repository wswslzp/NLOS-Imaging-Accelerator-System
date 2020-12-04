package Fpga

import spinal.core._
import spinal.lib._
import Config._
import spinal.lib.bus.amba4.axi._
import Core.RsdGenCoreArray._

case class NlosFpgaSys(cfg: RsdKernelConfig)(implicit val axi_config: Axi4Config) extends Component {
  val io = new Bundle {
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val data_in = slave(Axi4WriteOnly(axi_config))
    val load_req = out Bits(4 bit)
  }

  // ************* RGCA ***************
  val rgca = RsdGenCoreArray(cfg, 0)
  rgca.data_in <> io.data_in
  rgca.io.dc := io.dc
  rgca.io.fc := io.fc
  io.load_req := rgca.io.load_req
}
