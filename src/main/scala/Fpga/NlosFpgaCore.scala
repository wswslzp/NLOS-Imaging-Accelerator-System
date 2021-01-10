package Fpga

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._

case class NlosFpgaCore(cfg: RsdKernelConfig, pixel_parallel: Int = 1) extends Component {
  val io = new Bundle {
    val sys_init = in Bool()
    val result = master(Stream(UInt(8 bit)))
    val hdmi_vid_bus = master(HdmiVideoBus())
    val done = out Bool()
  }

  val nlos_no_driver = NlosNoDriver(cfg)
  val nlos_driver = NlosDriver(cfg, nlos_no_driver.loadUnitAddress)
  val hdmi_if = StreamToHDMI(vid_1280x720p60, cfg.rows, cfg.cols)
  val stream_forker = StreamFork2(nlos_no_driver.io.result)

  nlos_no_driver.io.img_in << nlos_driver.io.original_img
  nlos_no_driver.io.data_in << nlos_driver.io.kernel_in
  nlos_no_driver.io.dc := nlos_driver.io.dc
  nlos_no_driver.io.fc := nlos_driver.io.fc
  nlos_driver.io.load_req := nlos_no_driver.io.load_req
  nlos_no_driver.io.cnt_incr <> nlos_driver.io.cnt_incr
  nlos_driver.io.sys_init := io.sys_init
  nlos_driver.io.done := nlos_no_driver.io.done
  io.result << stream_forker._1
  hdmi_if.io.dat_in << stream_forker._2
  io.hdmi_vid_bus <> hdmi_if.io.vid
  io.done := nlos_no_driver.io.done

  nlos_driver.io.cnt_incr.simPublic()
  nlos_driver.io.dc.simPublic()
  nlos_driver.io.fc.simPublic()
}
