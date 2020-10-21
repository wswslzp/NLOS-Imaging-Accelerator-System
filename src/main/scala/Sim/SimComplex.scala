package Sim

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import SimFix._
import breeze.math._

package object SimComplex {
  implicit class SimHComplex(x: HComplex) {
    def #=(that: Complex): Unit = {
      x.real #= that.real
      x.imag #= that.imag
    }
    def toComplex: Complex = {
      Complex(x.real.toDouble, x.imag.toDouble)
    }
  }

  /**Convert Bits to Complex
   * This function convert the `Bits.toLong()` results into Complex
   * @param xbits the result of `Bits.toLong()`
   * @param cfg the complex config
   * @return
   */
  def bitsToComplex(xbits: Long, cfg: HComplexConfig): Complex = {
    require(cfg.getComplexWidth < 64)
    val low_part_mask: Long = (1L << cfg.getDataWidth) - 1
    val high_part_mask: Long = low_part_mask << cfg.getDataWidth
    println(s"lowmask: ${low_part_mask.toBinaryString}\nhighmask: ${high_part_mask.toBinaryString}")

    def uintToFloat(x: Long): Double = {
      val neg_detect: Long => Boolean = xt => {
        ( xt >> (cfg.getDataWidth - 1) ) == 1
      }
      if (neg_detect(x)) {
        // x is negative
        val xt: Long = ( -1L ^ low_part_mask ) | x // sign extension
        -(~xt + 1).toDouble / (1L << cfg.fracw)
      } else {
        // x is positive
        x.toDouble / (1L << cfg.fracw)
      }
    }

    val real_part: Long = if (cfg.real_high) ( xbits & high_part_mask ) >> cfg.getDataWidth else xbits & low_part_mask
    val imag_part: Long = if (cfg.real_high) xbits & low_part_mask else ( xbits & high_part_mask ) >> cfg.getDataWidth

    Complex(
      real = uintToFloat(real_part),
      imag = uintToFloat(imag_part)
    )
  }

}
