package Fpga.IP

import spinal.core._
import spinal.lib._

case class IOPLL(ip_name: String = "iopll") extends BlackBox {
  setDefinitionName(ip_name)
  val rst = in Bool()
  val refclk = in Bool()
  val locked = out Bool()
  val outclk_0 = out Bool()

}
