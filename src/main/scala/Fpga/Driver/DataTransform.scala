package Fpga.Driver

import Config._
import breeze.math._
import spinal.core._

trait DataTransform {
  protected def complexToBits(dat: Complex, cfg: HComplexConfig): Bits = {
    val real_p = (dat.real * scala.math.pow(2, cfg.fracw)).toInt
    val imag_p = (dat.imag * scala.math.pow(2, cfg.fracw)).toInt
    if (cfg.real_high) {
      B(real_p, cfg.getDataWidth bit) ## B(imag_p, cfg.getDataWidth bit)
    } else {
      B(imag_p, cfg.getDataWidth bit) ## B(real_p, cfg.getDataWidth bit)
    }
  }

  protected def doubleToBits(dat: Double, cfg: HComplexConfig): Bits = {
    B(
      ( dat * scala.math.pow(2, cfg.fracw) ).toInt, cfg.getDataWidth bit
    )
  }
}
