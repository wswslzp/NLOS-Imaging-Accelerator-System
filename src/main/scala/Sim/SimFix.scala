package Sim

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.core.sim._

object SimFix {

  def bitsToDouble(dat: Bits, fracw: Int): Double = {
    val low_part_mask: Long = (1L << dat.getBitsWidth) - 1

    def uintToFloat(x: Long): Double = {
      val neg_detect: Long => Boolean = xt => {
        ( xt >> (dat.getBitsWidth - 1) ) == 1
      }
      if (neg_detect(x)) {
        // x is negative
        val xt: Long = ( -1L ^ low_part_mask ) | x // sign extension
        -(~xt + 1).toDouble / (1L << fracw)
      } else {
        // x is positive
        x.toDouble / (1L << fracw)
      }
    }

    uintToFloat(dat.toLong)
  }

  implicit class SimSFix(x: SFix) {
    val fraction_bit = -x.minExp
    val max_v: Long = (1L << ( x.bitCount - 1)) - 1
    val min_v: Long = -(1L << (x.bitCount - 1))
    def #=(that: Float): Unit = {
      var rhs = (that * scala.math.pow(2, fraction_bit)).toLong
      rhs = Math.min(max_v, rhs)
      rhs = Math.max(min_v, rhs)
      x.raw #= rhs
    }
    def #=(that: Double): Unit = {
      this.#=(that.toFloat)
    }
    def toFloat: Float = {
      if (x.bitCount < 32){
        x.raw.toInt.toFloat / scala.math.pow(2, fraction_bit).toFloat
      }
      else {
        x.raw.toLong.toFloat / scala.math.pow(2, fraction_bit).toFloat
      }
    }
    def toDouble: Double = {
      if (x.bitCount < 32){
        x.raw.toInt.toDouble / scala.math.pow(2, fraction_bit)
      }
      else {
        // TODO: Test when bit count == 64
        x.raw.toLong.toDouble / scala.math.pow(2, fraction_bit)
      }
    }
  }

  implicit class SimUFix(x: UFix) {
    val fraction_bit = -x.minExp
    val max_v = (1L << x.bitCount) - 1
    def #=(that: Float): Unit = {
      var rhs = Math.min(max_v, (that * scala.math.pow(2, fraction_bit)).toLong)
      rhs = Math.max(0, rhs)
      x.raw #= rhs
    }
    def #=(that: Double): Unit = {
      this.#=(that.toFloat)
    }
    def toFloat: Float = {
      if (x.bitCount < 32){
        x.raw.toInt.toFloat / scala.math.pow(2, fraction_bit).toFloat
      }
      else {
        x.raw.toLong.toFloat / scala.math.pow(2, fraction_bit).toFloat
      }
    }
    def toDouble: Double = {
      if (x.bitCount < 32){
        x.raw.toInt.toDouble / scala.math.pow(2, fraction_bit)
      }
      else {
        x.raw.toLong.toDouble / scala.math.pow(2, fraction_bit)
      }
    }
  }
}

