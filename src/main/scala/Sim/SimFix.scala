package Sim

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.core.sim._

package object SimFix {
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
        x.raw.toInt / scala.math.pow(2, fraction_bit)
      }
      else {
        // TODO: Test when bit count == 64
        x.raw.toLong / scala.math.pow(2, fraction_bit)
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
        x.raw.toInt / scala.math.pow(2, fraction_bit)
      }
      else {
        x.raw.toLong / scala.math.pow(2, fraction_bit)
      }
    }
  }
}

