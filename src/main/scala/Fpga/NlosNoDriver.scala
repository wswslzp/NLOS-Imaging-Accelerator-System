package Fpga

import Config._
import Config.RsdKernelConfig._
import Util.HComplex
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi.Axi4WriteOnly

case class NlosNoDriver(cfg: RsdKernelConfig) extends Component {
  val io = new Bundle {
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val cnt_incr = out Bool()
    val data_in = slave(Axi4WriteOnly(axi_config))
    val img_in = slave(Flow(HComplex(cfg.getUinConfig)))
    val load_req = out Bits(4 bit)
    val result = master(Stream(Vec(UInt(8 bit), 2)))
    val fft_comp_end = out Bool()
    val done = out Bool()
  }

  val nlos_core = NlosCore(cfg)
  val post_proc = PostProcess(cfg)

  def loadUnitAddress = nlos_core.loadUnitAddrs

  nlos_core.io.dc := io.dc
  nlos_core.io.fc := io.fc
  io.cnt_incr := nlos_core.io.cnt_incr
  io.data_in <> nlos_core.io.data_in
  nlos_core.io.img_in << io.img_in
  io.load_req := nlos_core.io.load_req
//  io.done := nlos_core.io.done
  io.done := post_proc.io.pp_done
  io.fft_comp_end := nlos_core.io.fft_comp_end

  post_proc.io.done := nlos_core.io.done
  post_proc.io.img_in << nlos_core.io.result
  io.result <> post_proc.io.img_out
}

