package Fpga.IP

import spinal.core._
import spinal.lib._

case class IOPLL(ip_name: String = "iopll") extends BlackBox {
  setDefinitionName(ip_name)
  val rst = in Bool()
  val refclk = in Bool()
  val locked = out Bool()
  val outclk_0 = out Bool()

  def setRefClock(ref_clk_domain: ClockDomain): Unit = mapClockDomain(
    ref_clk_domain, clock = refclk, reset = rst
  )

  def setOutClock(out_clk_domain: ClockDomain): Unit = mapClockDomain(
    out_clk_domain, clock = outclk_0, reset = rst
  )

}
