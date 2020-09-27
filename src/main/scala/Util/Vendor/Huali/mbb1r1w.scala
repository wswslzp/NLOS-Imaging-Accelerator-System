package Util.Vendor.Huali

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.Vendor.MemBlackBox
import MemManager._

class mbb1r1w(wrap: Ram1r1w) extends Util.Vendor.MemBlackBox {
  //  this.setDefinitionName()
  val io = new Bundle {
    val CLKA, CLKB = in Bool
    val ADRA, ADRB = in UInt(wrap.mc.aw bit)
    val D = in Bits(wrap.mc.dw bit)
    val Q = out Bits(wrap.mc.dw bit)
    val WEMA, WEMB = in Bits(wrap.mc.dw bit)
    val WE, MEA, MEB, TEST1A, TEST1B, RMEA, RMEB = in Bool
    val RMA, RMB = in Bits(4 bit)
  }

  val cda = ClockDomain(io.CLKA)
  val cdb = ClockDomain(io.CLKB)

  def Build(): MemBlackBox = {
    wrap.cda.setSynchronousWith(cda)
    wrap.cdb.setSynchronousWith(cdb)
    this.io.CLKA   <> wrap.cda.readClockWire
    this.io.CLKB   <> wrap.cdb.readClockWire
    this.io.ADRA   <> wrap.io.apa.addr
    this.io.ADRB   <> wrap.io.apb.addr
    this.io.D     <> wrap.io.dp.din
    this.io.Q     <> wrap.io.dp.dout
    this.io.WEMA   <> wrap.io.apa.bwe
    this.io.WEMB   <> wrap.io.apb.bwe
    this.io.WE    <> wrap.io.dp.we
    this.io.MEA    <> wrap.io.apa.cs
    this.io.MEB    <> wrap.io.apb.cs
    this.io.TEST1A := True
    this.io.RMEA   := True
    this.io.RMA    := B"4'b0010"
    this.io.TEST1B := True
    this.io.RMEB   := True
    this.io.RMB    := B"4'b0010"
    this
  }

  noIoPrefix()

}
