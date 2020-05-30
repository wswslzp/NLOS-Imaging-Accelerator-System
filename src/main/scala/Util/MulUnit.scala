package Util

import spinal.core._

case class MulUnit(
                  width: Int
                  ) extends BlackBox {
  val io = new Bundle {
    val a = in Bits(width bit)
    val b = in Bits(width bit)
    val c = out Bits(width bit)
  }

  noIoPrefix()
  addRTLPath("rtl/core.MulUnit.v")
}

object MulUnit {
  def apply(a: UInt, b: UInt): UInt = {
    val width = Math.max(a.getBitsWidth, b.getBitsWidth)
    val ia = a.resize(width)
    val ib = b.resize(width)
    val mul_unit = MulUnit(width)
    mul_unit.io.a <> ia.asBits
    mul_unit.io.b <> ib.asBits
    mul_unit.io.c.asUInt
  }
  def apply(a: UFix, b: UFix): UFix = {
//    require(a.getBitsWidth == b.getBitsWidth)
    import Util.MyUFix._
    val uq_res = UQ(
      Math.max(a.getBitsWidth, b.getBitsWidth),
      Math.max(-a.minExp, -b.minExp)
    )
    val ia = a.fixTo(uq_res)
    val ib = b.fixTo(uq_res)
    val mul_unit = MulUnit(ia.getBitsWidth)
    mul_unit.io.a := ia.asBits
    mul_unit.io.b := ib.asBits
    val ret = cloneOf(ia)
    ret.assignFromBits(mul_unit.io.c)
    ret
  }
  def apply(a: SFix, b: SFix): SFix = {
//    require(a.bitCount == b.bitCount)
    import Util.MySFix._
    val sq_res = SQ(
      Math.max(a.bitCount, b.bitCount),
      Math.max(-a.minExp, -b.minExp)
    )
    val ia = a.fixTo(sq_res)
    val ib = b.fixTo(sq_res)
    val mul_unit = MulUnit(ia.bitCount)
    mul_unit.io.a := ia.asBits
    mul_unit.io.b := ib.asBits
    val ret = cloneOf(ia)
    ret.assignFromBits(mul_unit.io.c)
    ret
  }
}
