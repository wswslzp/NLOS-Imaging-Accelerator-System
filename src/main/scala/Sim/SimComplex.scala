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
}
