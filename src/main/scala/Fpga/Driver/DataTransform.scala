package Fpga.Driver

import Config._
import breeze.math._
import spinal.core._

trait DataTransform {
  protected def complexToBits(dat: Complex, cfg: HComplexConfig): Bits = {
    val ret = Bits(cfg.getComplexWidth bit)
    val real_p = (dat.real * scala.math.pow(2, cfg.fracw)).toInt
    val imag_p = (dat.imag * scala.math.pow(2, cfg.fracw)).toInt
    ret := if (cfg.real_high) {
      B(real_p) ## B(imag_p)
    } else {
      B(imag_p) ## B(real_p)
    }
    ret
  }
}
