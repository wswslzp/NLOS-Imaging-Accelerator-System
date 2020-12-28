package Fpga.Driver

import Config._
import breeze.math._
import spinal.core._

// Note! Mem init content only receive literal value, so
//  the function returns Bits is not work.
trait DataTransform {
  protected def doubleToSInt(dat: Double, cfg: HComplexConfig): BigInt = {
    val max_v = (1L << (cfg.getDataWidth-1))-1
    val min_v = -(1L << (cfg.getDataWidth-1))

    val sint_v = scala.math.max(scala.math.min(
      scala.math.pow(2, cfg.fracw) * dat, max_v
    ), min_v).toLong

    if (sint_v >= 0) BigInt(sint_v)
    else (BigInt(1) << cfg.getDataWidth) + BigInt(sint_v)
  }

  protected def complexToSInt(dat: Complex, cfg: HComplexConfig): BigInt = {
    val real_p = doubleToSInt(dat.real, cfg)
    val imag_p = doubleToSInt(dat.imag, cfg)
    if (cfg.real_high){
      ( real_p << cfg.getDataWidth ) | imag_p
    }else{
      ( imag_p << cfg.getDataWidth ) | real_p
    }
  }

  @deprecated
  protected def doubleToBits(dat: Double, cfg: HComplexConfig): Bits = {
    val max_v = ( (1L << (cfg.getDataWidth-1))-1 ) / cfg.fracw.toDouble
    val min_v = -(1L << (cfg.getDataWidth-1)) / cfg.fracw.toDouble
    val dat1 = scala.math.max(scala.math.min(dat, max_v), min_v)
    S(
      ( dat1 * scala.math.pow(2, cfg.fracw) ).toInt, cfg.getDataWidth bit
    ).asBits
  }

  @deprecated
  protected def complexToBits(dat: Complex, cfg: HComplexConfig): Bits = {
    val real_p = doubleToBits(dat.real, cfg)
    val imag_p = doubleToBits(dat.imag, cfg)
    if (cfg.real_high) {
      real_p ## imag_p
    } else {
      imag_p ## real_p
    }
  }
}
