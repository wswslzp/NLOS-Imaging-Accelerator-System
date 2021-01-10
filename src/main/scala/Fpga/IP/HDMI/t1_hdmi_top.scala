package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

case class t1_hdmi_top(cfg: hdmi_config, vid_fm: vid_format) extends Component {
  val hdmi_refclk = in Bool()
  val io = new Bundle {
    val tx_hpd = in Bool() // Hot Plug detect; connect to pin
    val tx_hpd_req = out Bool()
    val tx_serial_data = out Bits(4 bit)
  }

  val hdmi_ex_clk_dom = ClockDomain.internal("hdmi_ex_inst")
  hdmi_ex_clk_dom.clock := clockDomain.readClockWire
  hdmi_ex_clk_dom.reset := !clockDomain.readResetWire
  val hdmi_ex_clk_area = new ClockingArea(hdmi_ex_clk_dom){
    val hdmi_ex_inst = t1_hdmi_wrapper(cfg) // hdmi_ex_inst reset polarity is high!!
  }
//  val hdmi_ex_inst = t1_hdmi_wrapper(cfg) // hdmi_ex_inst reset polarity is high!!

  val vid_clk_dom = ClockDomain.internal("vid")
  vid_clk_dom.reset := clockDomain.readResetWire
//  vid_clk_dom.clock := hdmi_ex_inst.io.vid.clk
  vid_clk_dom.clock := hdmi_ex_clk_area.hdmi_ex_inst.io.vid.clk
  val vid_clk_area = new ClockingArea(vid_clk_dom){
    val vid_gen = t1_vid_gen(cfg, vid_fm)
  }

//  hdmi_ex_inst.io.tx_hpd := io.tx_hpd
//  io.tx_hpd_req := hdmi_ex_inst.io.tx_hpd_req
//  hdmi_ex_inst.io.iopll_0_refclk_clk := hdmi_refclk
//
//  hdmi_ex_inst.io.vid.de := vid_clk_area.vid_gen.io.de
//  hdmi_ex_inst.io.vid.hsync := vid_clk_area.vid_gen.io.hsync
//  hdmi_ex_inst.io.vid.vsync := vid_clk_area.vid_gen.io.vsync
//  hdmi_ex_inst.io.vid.data := vid_clk_area.vid_gen.io.data
//  hdmi_ex_inst.io.scrambler_enable := Bool(cfg.pixel_per_clock > 340)
//  hdmi_ex_inst.io.tmds_bit_clock_ratio := Bool(cfg.pixel_per_clock > 340)
//  hdmi_ex_inst.io.ctrl := Vec.fill(cfg.pixel_per_clock)(B(0, 6 bit)).asBits
//  hdmi_ex_inst.io.mode := True
//  hdmi_ex_inst.io.os := cfg.getTxOS
//  io.tx_serial_data := hdmi_ex_inst.io.tx_serial_data
//
  hdmi_ex_clk_area.hdmi_ex_inst.io.tx_hpd := io.tx_hpd
  io.tx_hpd_req := hdmi_ex_clk_area.hdmi_ex_inst.io.tx_hpd_req
  hdmi_ex_clk_area.hdmi_ex_inst.io.iopll_0_refclk_clk := hdmi_refclk

  hdmi_ex_clk_area.hdmi_ex_inst.io.vid.de := vid_clk_area.vid_gen.io.de
  hdmi_ex_clk_area.hdmi_ex_inst.io.vid.hsync := vid_clk_area.vid_gen.io.hsync
  hdmi_ex_clk_area.hdmi_ex_inst.io.vid.vsync := vid_clk_area.vid_gen.io.vsync
  hdmi_ex_clk_area.hdmi_ex_inst.io.vid.data := vid_clk_area.vid_gen.io.data
  hdmi_ex_clk_area.hdmi_ex_inst.io.scrambler_enable := Bool(cfg.pixel_per_clock > 340)
  hdmi_ex_clk_area.hdmi_ex_inst.io.tmds_bit_clock_ratio := Bool(cfg.pixel_per_clock > 340)
  hdmi_ex_clk_area.hdmi_ex_inst.io.ctrl := Vec.fill(cfg.pixel_per_clock)(B(0, 6 bit)).asBits
  hdmi_ex_clk_area.hdmi_ex_inst.io.mode := True
  hdmi_ex_clk_area.hdmi_ex_inst.io.os := cfg.getTxOS

  io.tx_serial_data := hdmi_ex_clk_area.hdmi_ex_inst.io.tx_serial_data
}

object t1_hdmi_top extends App {
  import java.io.File

  val cfg = hdmi_config()
  new File("rtl").mkdir()
  SpinalConfig(
    targetDirectory = "rtl",
    defaultConfigForClockDomains = ClockDomainConfig(resetActiveLevel = LOW)
  ).generateVerilog(
    t1_hdmi_top(cfg, vid_720x480p60)
  )
}