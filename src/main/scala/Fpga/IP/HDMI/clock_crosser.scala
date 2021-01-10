package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

case class clock_crosser(width: Int) extends BlackBox {
  val io = new Bundle {
    val din = in Bits(width bit)
    val in_clk = in Bool()
    val in_reset = in Bool()
    val out_clk = in Bool()
    val out_reset = in Bool()
    val dout = out Bits(width bit)
  }

  noIoPrefix()
  mapCurrentClockDomain(io.in_clk)

  io.din.setName("in")
  io.dout.setName("out")
}
