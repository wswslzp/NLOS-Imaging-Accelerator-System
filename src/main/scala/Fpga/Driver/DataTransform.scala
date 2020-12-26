package Fpga.Driver

import Config._
import breeze.math._
import spinal.core._

trait DataTransform {
  protected def doubleToBits(dat: Double, cfg: HComplexConfig): Bits = {
    val max_v = ( (1L << (cfg.getDataWidth-1))-1 ) / cfg.fracw.toDouble
    val min_v = -(1L << (cfg.getDataWidth-1)) / cfg.fracw.toDouble
    val dat1 = scala.math.max(scala.math.min(dat, max_v), min_v)
    S(
      ( dat1 * scala.math.pow(2, cfg.fracw) ).toInt, cfg.getDataWidth bit
    ).asBits
  }

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
