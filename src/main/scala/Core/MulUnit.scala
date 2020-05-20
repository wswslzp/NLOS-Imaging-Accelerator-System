package Core

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
    require(a.getWidth == b.getWidth)
    val width = a.getWidth
    val mul_unit = MulUnit(width)
    mul_unit.io.a <> a.asBits
    mul_unit.io.b <> b.asBits
    mul_unit.io.c.asUInt
  }
}
