package Util.Vendor.Huali

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import Util.Vendor._

class mbbrom(wrap: Rom) extends MemBlackBox(wrap.mc) {
//  this.setDefinitionName()
  val io = new Bundle {
    val CLK = in Bool()
    val ADR = in UInt(wrap.mc.aw bit)
    val Q = out Bits(wrap.mc.dw bit)
    val ME, LS = in Bool()
    val TEST1, RME = in Bool()
    val RM = in Bits(4 bit)
  }

  val cd = ClockDomain(io.CLK)

  def Build(): MemBlackBox = {
    wrap.clockDomain.setSynchronousWith(cd)
    io.CLK <> wrap.clockDomain.readClockWire
    io.ADR <> wrap.io.addr
    io.Q   <> wrap.io.rdata
    io.ME  <> wrap.io.cs
    io.LS  := False
    io.TEST1 := True
    io.RME := True
    io.RM  := B"4'b0010"
    this
  }

  noIoPrefix()
}
