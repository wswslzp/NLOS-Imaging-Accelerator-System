package Fpga

import spinal.core._
import spinal.lib._
import spinal.core.sim._
import Config._
import spinal.lib.bus.amba4.axi._
import Core.RsdGenCoreArray._
import Core.MacArray._
import Fpga.MacArray._
import Util._

/**
 * The nlos core still needs to be redesigned.
 *  1. The design next could try to store the wave, timeshift and distance inside SRAM.
 *  After all these three things have been loaded, only the master have to do is to pipe in
 *  the image.
 *
 *  2. TODO
 * @param cfg RSD Kernel configuration
 * @param axi_config Configuration for AXI4 write only bus
 */
case class NlosCore(cfg: RsdKernelConfig)(implicit val axi_config: Axi4Config) extends Component {
  val io = new Bundle {
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val cnt_incr = out Bool()
    val data_in = slave(Axi4WriteOnly(axi_config))
    val img_in = slave(Flow(HComplex(cfg.getUinConfig)))
    val load_req = out Bits(4 bit)
//    val result = master(Flow(Vec(HComplex(cfg.getResultConfig), cfg.kernel_size.last)))
    val result = master(Flow(HComplex(cfg.getResultConfig)))
    val fft_comp_end = out Bool()
    val done = out Bool()
  }

  // ************* RGCA ***************
  val rgca = RsdGenCoreArray(cfg, 0)
  rgca.data_in <> io.data_in
  rgca.io.dc := io.dc
  rgca.io.fc := io.fc
  io.load_req := rgca.io.load_req
  io.cnt_incr := rgca.io.cnt_incr

  // ************ FFT2dCore ************
//  val fft2d_core = FFT2dCore(
//    rsd_cfg = cfg,
//    depth_factor = cfg.depth_factor,
//    freq_factor = cfg.freq_factor
//  )
  val fft2d_core = FFT2dCore_v1(
    rsd_cfg = cfg,
    depth_factor = cfg.depth_factor,
    freq_factor = cfg.freq_factor
  ) // todo check
  fft2d_core.io.dc := io.dc
  fft2d_core.io.fc := io.fc
  fft2d_core.io.push_start := rgca.io.push_start // todo
  fft2d_core.io.push_ending := rgca.io.push_ending // todo
  rgca.io.fft2d_out_sync := fft2d_core.io.fft2d_out_sync
  fft2d_core.io.data_in << io.img_in
  io.result << fft2d_core.io.data_to_final
  io.fft_comp_end := fft2d_core.io.fft2d_comp_done

  // ************ MacArray *************
//  val mac_array = ComplexAccArray(cfg)
  val mac_array = RowMacArray(cfg)
  val fc_ov = io.fc === (cfg.freq_factor-1)
  mac_array.io.fc_overflow := fc_ov
  mac_array.io.push_ending := rgca.io.push_ending
  mac_array.io.dc_eq_0 := (io.dc === 0) // todo
  mac_array.io.ifft2d_done := fft2d_core.io.ifft2d_comp_done // todo
  rgca.io.clear_confirm := mac_array.io.clear_confirm
  mac_array.io.fft_out << fft2d_core.io.data_to_mac
  fft2d_core.io.data_to_mac.simPublic()
  mac_array.io.rsd_kernel << rgca.io.rsd_kernel
  rgca.io.rsd_kernel.simPublic()
  fft2d_core.io.data_from_mac << mac_array.io.mac_result
  mac_array.io.mac_result.simPublic()

//  io.done := (io.dc === ((1 << io.dc.getWidth)-1)) && (io.fc === ((1<<io.fc.getWidth)-1)) && io.result.valid.fall(False)
  io.done := ( io.dc === cfg.depth_factor ) && io.result.valid.fall(False)
  fft2d_core.io.done := io.done // todo
  val loadUnitAddrs = rgca.loadUnitAddrs
}
