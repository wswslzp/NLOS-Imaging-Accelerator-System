package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi._
import RsdKernelConfig._
import breeze.math.Complex
import Driver._
import Fpga.Driver._

case class NlosDriver(cfg: RsdKernelConfig, loadUnitAddrs: Vector[Int]) extends Component {
  val io = new Bundle {
    val original_img = master Flow HComplex(cfg.getUinConfig)
    val kernel_in = master(Axi4WriteOnly(axi_config))
    val dc = out UInt(log2Up(cfg.depth_factor) bit)
    val fc = out UInt(log2Up(cfg.freq_factor) bit)
    val load_req = in Bits(4 bit)
    val cnt_incr = in Bool()
    val fft_comp_end = in Bool()
    val sys_init = in Bool()
  }

  // ************** Counting for depth freq **************
  val depth_cnt = Counter(0, cfg.depth_factor)
  val freq_cnt = Counter(0, cfg.freq_factor)
  when(io.cnt_incr){
    freq_cnt.increment()
  }
  when(freq_cnt.willOverflow){
    depth_cnt.increment()
  }
  io.dc := depth_cnt
  io.fc := freq_cnt


  // *************** Image driver **************
  val img_drver = ImageDriver(cfg)
  img_drver.io.sys_init := io.sys_init
  img_drver.io.fft_comp_end := io.fft_comp_end
  img_drver.io.dc := depth_cnt
  img_drver.io.fc := freq_cnt
  img_drver.io.original_img >> io.original_img

  val kernel_driver = KernelDriver(cfg, loadUnitAddrs)
  kernel_driver.io.load_req := io.load_req
  kernel_driver.io.kernel_data_out >> io.kernel_in

}
