package Util.Vendor.Huali

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import Util.Vendor._

class mbb2rw(wrap: Ram2rw) extends MemBlackBox(wrap.mc) {
  //  this.setDefinitionName()
  val io = new Bundle {
    val CLKA, CLKB = in Bool
    val ADRA, ADRB = in UInt(wrap.mc.aw bit)
    val DA, DB = in Bits(wrap.mc.dw bit)
    val QA, QB = out Bits(wrap.mc.dw bit)
    val WEMA, WEMB = in Bits(wrap.mc.dw bit)
    val WEA, WEB, MEA, MEB, TEST1A, TEST1B, RMEA, RMEB = in Bool
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
    this.io.DA     <> wrap.io.dpa.din
    this.io.QA     <> wrap.io.dpa.dout
    this.io.DB     <> wrap.io.dpb.din
    this.io.QB     <> wrap.io.dpb.dout
    this.io.WEMA   <> wrap.io.apa.bwe
    this.io.WEMB   <> wrap.io.apb.bwe
    this.io.WEA    <> wrap.io.dpa.we
    this.io.WEB    <> wrap.io.dpb.we
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
