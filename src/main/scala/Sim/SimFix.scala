package Sim

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.core.sim._

package object SimFix {
  import scala.math._
  implicit class SimSFix(x: SFix) {
    val fraction_bit = -x.minExp
    val max_v: Long = (1L << ( x.bitCount - 1)) - 1
    val min_v: Long = -(1L << (x.bitCount - 1))
    def #=(that: Float): Unit = {
      var rhs = (that * (1 << fraction_bit)).toLong
      rhs = Math.min(max_v, rhs)
      rhs = Math.max(min_v, rhs)
      x.raw #= rhs
    }
    def #=(that: Double): Unit = {
      this.#=(that.toFloat)
    }
    def toFloat: Float = {
      if (x.bitCount <= 32){
        x.raw.toInt.toFloat / (1 << fraction_bit)
      }
      else {
        x.raw.toLong.toFloat / (1 << fraction_bit)
      }
    }
    def toDouble: Double = {
      this.toFloat.toDouble
    }
  }

  implicit class SimUFix(x: UFix) {
    val fraction_bit = -x.minExp
    val max_v = (1 << x.bitCount) - 1
    def #=(that: Float): Unit = {
      var rhs = Math.min(max_v, (that * (1 << fraction_bit)).toInt)
      rhs = Math.max(0, rhs)
      x.raw #= rhs
    }
    def #=(that: Double): Unit = {
      this.#=(that.toFloat)
    }
    def toFloat: Float = {
      if (x.bitCount <= 32){
        x.raw.toInt.toFloat / (1 << fraction_bit)
      }
      else {
        x.raw.toLong.toFloat / (1 << fraction_bit)
      }
    }
    def toDouble: Double = {
//      x.raw.toInt.toDouble / (1 << fraction_bit)
      this.toFloat.toDouble
    }
  }
}

