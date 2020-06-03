package Util

import spinal.core._

case class DivUnit(width: Int) extends BlackBox {
  val io = new Bundle {
    val a = in Bits(2 * width bit) // a serve as the divisor
    val b = in Bits(width bit)// b serve as the dividend
    val q = out Bits(width bit) // q = a / b
    val r = out Bits(width bit) // r = a % b
  }

  noIoPrefix()
}

object DivUnit {
  def apply(a: UFix, b: UFix): UFix = {
    import MyUFix._
    // unify two number's q_format
    val uq_res = UQ(
      Math.max(a.getBitsWidth, b.getBitsWidth),
      Math.max(-a.minExp, -b.minExp)
    )
    // fix two numbers to the same q_format
    val ia = a.fixTo(uq_res)
    val ib = b.fixTo(uq_res)
    val div_unit = DivUnit(ia.bitCount) // designate the width of the divider
    // append zeros to the right of the ia, ia.lsb
    div_unit.io.a := ia.asBits ## B(ia.bitCount bit, default -> false)
    div_unit.io.b := ib.asBits
    // get the quotient and the remainder
//    val quotient = div_unit.io.q
//    val remainder = div_unit.io.r
    val ret = UFix(ia.maxExp exp, ia.minExp exp)
    ret.assignFromBits(div_unit.io.q)
    ret
  }
  def apply(a: SFix, b: SFix): SFix = {
    import MySFix._
    val sq_res = SQ(
      Math.max(a.bitCount, b.bitCount),
      Math.max(-a.minExp, -b.minExp)
    )
    val ia = a.fixTo(sq_res)
    val ib = b.fixTo(sq_res)
    val div_unit = DivUnit(ia.bitCount)
    div_unit.io.a := ia.asBits ## B(ia.bitCount bit, default -> false)
    div_unit.io.b := ib.asBits
    val ret = SFix(ia.maxExp-1 exp, ia.minExp exp)
    ret.assignFromBits(div_unit.io.q)
    ret
  }

  def mod(a: SInt, b: SInt): SInt = {
    val com_width = Math.max(a.getWidth, b.getWidth)
    val ia = a.resize(com_width)
    val ib = b.resize(com_width)
    val divider = DivUnit(com_width)
    divider.io.a <> ia.asBits
    divider.io.b <> ib.asBits
    divider.io.r.asSInt
  }

  def mod(a: SFix, b:SFix): SFix = {
    // TODO:
    import MySFix._
    val sq_res = SQ(
      Math.max(a.bitCount, b.bitCount),
      Math.max(-a.minExp, -b.minExp)
    )
    val ia = a.fixTo(sq_res)
    val ib = b.fixTo(sq_res)
    val div_unit = DivUnit(ia.bitCount)
    div_unit.io.a := ia.asBits ## B(ia.bitCount bit, default -> false)
    div_unit.io.b := ib.asBits
    val ret = SFix(ia.maxExp-1 exp, ia.minExp exp)
    ret.assignFromBits(div_unit.io.r)
    ret
  }
}
