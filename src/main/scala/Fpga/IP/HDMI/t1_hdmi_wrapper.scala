package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

case class t1_hdmi_wrapper(cfg: hdmi_config) extends Component {

  val io = new Bundle {
    val iopll_0_refclk_clk = in Bool()
    val tx_hpd = in Bool() // Hot Plug detect; connect to pin
    val tx_hpd_req = out Bool()
    val scrambler_enable = in Bool() // 0: not perform scramble ; 1: Perform
    val tmds_bit_clock_ratio = in Bool() // 0:
    val ctrl = in Bits(12 bit)
    val mode = in Bool() // 0: DVI; 1: HDMI
    val tx_serial_data = out Bits(4 bit)
    val os = in Bits(2 bit) // over sampling control

    val vid = new Bundle {
      val clk = out Bool()
      val de = in Bits(cfg.pixel_per_clock bit)
      val hsync = in Bits(cfg.pixel_per_clock bit)
      val vsync = in Bits(cfg.pixel_per_clock bit)
      val data = in Bits(cfg.pixel_per_clock * 48 bit)
    }
  }

  val t1_hdmi_ex_inst = t1_hdmi_ex(cfg.pixel_per_clock)

  val crser = clock_crosser(2)
  crser.io.din := io.os
  crser.io.out_clk := t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.clkout_clk(0)
  crser.io.in_reset := False
  crser.io.out_reset := False

  t1_hdmi_ex_inst.io.iopll_0_refclk_clk := io.iopll_0_refclk_clk
  t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.pll_refclk0_clk := io.iopll_0_refclk_clk

  // reset controller
  t1_hdmi_ex_inst.io.xcvr.reset_control_s10_0.pll_locked_pll_locked := t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.pll_locked_pll_locked
  t1_hdmi_ex_inst.io.xcvr.reset_control_s10_0.pll_select_pll_select := False
  t1_hdmi_ex_inst.io.xcvr.reset_control_s10_0.tx_cal_busy_tx_cal_busy :=
    Vec.fill(4)(t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.pll_cal_busy.pll_cal_busy).asBits

  // hdmi ip
  t1_hdmi_ex_inst.io.hdmi_0.phy_interface.os := crser.io.dout
  t1_hdmi_ex_inst.io.hdmi_0.mode_mode := io.mode
  t1_hdmi_ex_inst.io.hdmi_0.in_lock.in_lock :=
    t1_hdmi_ex_inst.io.xcvr.reset_control_s10_0.tx_ready_tx_ready.andR && t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.pll_locked_pll_locked && t1_hdmi_ex_inst.io.iopll_0_locked_export
  t1_hdmi_ex_inst.io.hdmi_0.tx_hpd.tx_hpd := io.tx_hpd
  io.tx_hpd_req := t1_hdmi_ex_inst.io.hdmi_0.tx_hpd.tx_hpd_req
  t1_hdmi_ex_inst.io.hdmi_0.tmds_bit_clock_ratio.tmds_bit_clock_ratio := io.tmds_bit_clock_ratio
  t1_hdmi_ex_inst.io.hdmi_0.scrambler_enable.scrambler_enable := io.scrambler_enable
  t1_hdmi_ex_inst.io.hdmi_0.ctrl.ctrl := io.ctrl
  t1_hdmi_ex_inst.io.hdmi_0.tx_clk_clk := t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.clkout_clk(0)
  t1_hdmi_ex_inst.io.hdmi_0.clocked_video_vid.de := io.vid.de
  t1_hdmi_ex_inst.io.hdmi_0.clocked_video_vid.hsync := io.vid.hsync
  t1_hdmi_ex_inst.io.hdmi_0.clocked_video_vid.vsync := io.vid.vsync
  t1_hdmi_ex_inst.io.hdmi_0.clocked_video_vid.data := io.vid.data
  io.vid.clk := t1_hdmi_ex_inst.io.vid_clk_clk

  // phy interface
  val polinv = if (cfg.tx_polarity == HIGH) {
    B"4'b1"
  } else {B"4'b0"}
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.bonding_clocks_clk :=
    Vec.fill(4)(t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.tx_bonding_clocks_clk).asBits
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.coreclkin_clk :=
    Vec.fill(4)(t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.clkout_clk(0)).asBits
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.cal_busy_tx_cal_busy :=
    Vec.fill(4)(t1_hdmi_ex_inst.io.xcvr.fpll_s10_htile_0.pll_cal_busy.pll_cal_busy).asBits
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.parallel_data_tx_parallel_data :=
    t1_hdmi_ex_inst.io.hdmi_0.phy_interface.out_r ## t1_hdmi_ex_inst.io.hdmi_0.phy_interface.out_g ## t1_hdmi_ex_inst.io.hdmi_0.phy_interface.out_b ## t1_hdmi_ex_inst.io.hdmi_0.phy_interface.out_c
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.polinv_tx_polinv := polinv
  t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.unused_tx_parallel_data_unused_tx_parallel_data := B"240'b0"
  io.tx_serial_data := t1_hdmi_ex_inst.io.xcvr.native_s10_htile_0.tx.serial_data_tx_serial_data

}
