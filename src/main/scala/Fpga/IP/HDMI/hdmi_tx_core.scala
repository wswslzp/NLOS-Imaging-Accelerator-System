package Fpga.IP.HDMI

import spinal.core._
import spinal.lib._

case class hdmi_tx_core() extends BlackBox {
  val io = new Bundle {
    val reset = in Bool()
    val reset_vid = in Bool()
    val vid_clk = in Bool()
    val ls_clk = in Bool()
    val tx_clk = in Bool()
    val mode = in Bool()

    val vid = new Bundle {
      val de = in Bits(2 bit)
      val data = in Bits(96 bit)
      val hsync = in Bool()
      val vsync = in Bool()
    }

    val os = in Bits(2 bit)

    val out_b = out Bits(20 bit)
    val out_r = out Bits(20 bit)
    val out_g = out Bits(20 bit)
    val out_c = out Bits(20 bit)

    val aux = new Bundle {
      val ready = out Bool()
      val valid = in Bool()
      val data = in Bits(72 bit)
      val sop = in Bool()
      val eop = in Bool()
    }

    val gcp = in Bits(6 bit)

    val info = new Bundle {
      val avi = in Bits(113 bit)
      val vsi = in Bits(62 bit)
    }

    val audio = new Bundle {
      val clk = in Bool()
      val CTS = in Bits(20 bit)
      val N = in Bits(20 bit)
      val data = in Bits(256 bit)
      val de = in Bool()
      val mute = in Bool()
      val info_ai = in Bits(49 bit)
      val metadata = in Bits(166 bit)
      val format = in Bits(5 bit)
    }

    val in_lock = in Bool()
    val mgmt_clk = in Bool()
    val tx_hpd = in Bool()
    val tx_hpd_req = out Bool()
    val scrambler_enable = in Bool()
    val tmds_bit_clock_ratio = in Bool()
    val ctrl = in Bits(12 bit)

  }

  noIoPrefix()
  io.audio.allowPruning()
  io.aux.allowPruning()
}
