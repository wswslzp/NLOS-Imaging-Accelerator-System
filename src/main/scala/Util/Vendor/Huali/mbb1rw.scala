package Util.Vendor.Huali

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import Vendor._

class mbb1rw(wrap: Ram1rw) extends Util.Vendor.MemBlackBox {
//  this.setDefinitionName()
  val io = new Bundle {
    val CLK = in Bool
    val ADR = in UInt(wrap.mc.aw bit)
    val D = in Bits(wrap.mc.dw bit)
    val Q = out Bits(wrap.mc.dw bit)
    val WEM = in Bits(wrap.mc.dw bit)
    val WE, ME, TEST1, RME = in Bool
    val RM = in Bits(4 bit)
  }
  println("In Huali, mbb1rw")

  val cd = ClockDomain(io.CLK)

  def Build(): MemBlackBox = {
    wrap.clockDomain.setSynchronousWith(cd)
    this.io.CLK   <> wrap.clockDomain.readClockWire
    this.io.ADR   <> wrap.io.ap.addr
    this.io.D     <> wrap.io.dp.din
    this.io.Q     <> wrap.io.dp.dout
    this.io.WEM   <> wrap.io.ap.bwe
    this.io.WE    <> wrap.io.dp.we
    this.io.ME    <> wrap.io.ap.cs
    this.io.TEST1 := True
    this.io.RME   := True
    this.io.RM    := B"4'b0010"
    this
  }

  noIoPrefix()
}
