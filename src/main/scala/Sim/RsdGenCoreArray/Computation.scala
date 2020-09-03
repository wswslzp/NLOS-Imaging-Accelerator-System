package Sim.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze._

object Computation {
  import linalg.DenseMatrix
  import breeze.math.Complex

  def generateCoef(
                    wave: DenseMatrix[Double],
                    distance: DenseMatrix[Double],
                    timeshift: DenseMatrix[Complex]
                  ): Vector[DenseMatrix[Complex]] = {
    val freq_num = distance.rows
    val depth_num = wave.cols
    val radius_num = wave.rows
    val coef = Vector.fill(depth_num)(
      DenseMatrix.fill(freq_num, radius_num)(Complex(0, 0))
    )
    for {
      dd <- 0 until depth_num
      ff <- 0 until freq_num
      rr <- 0 until radius_num
    } {
      val tmp = numerics.exp(2*Complex(0, 1)*wave(rr, dd)*distance(ff, dd)) / wave(rr, dd) * timeshift(ff, dd)
      coef(dd)(ff, rr) = tmp
    }
    coef
  }

  def restoreImpl(impl_rad: DenseMatrix[Complex]): Vector[DenseMatrix[Complex]] = {
    val radius_num = impl_rad.rows
    val R = impl_rad.cols
    val kernel_size = 2*R
    val out_impl = Vector.fill(radius_num)(
      DenseMatrix.fill(kernel_size, kernel_size)(Complex(0, 0))
    )
    for {
      r <- 0 until radius_num
      imp_r = impl_rad(r, ::).t
      ii <- 0 until kernel_size
      jj <- 0 until kernel_size
    } {
      var impl_rad_indx = Math.sqrt(
        ( Math.pow(ii - R, 2) + Math.pow(jj - R, 2) ) / 2
      ).toInt
      impl_rad_indx = Math.min(impl_rad_indx, R-1)
      out_impl(r)(ii, jj) = imp_r(impl_rad_indx)
    }
    out_impl
  }

  def generateRSDkernel(
                       coef: Vector[DenseMatrix[Complex]],
                       impl: Vector[DenseMatrix[Complex]]
                       ): Vector[DenseMatrix[Complex]] = {
    import scala.collection.mutable.ArrayBuffer
    val freq_num = coef.head.rows
    val depth_num = coef.length
    val radius_num = coef.head.cols
    val result: ArrayBuffer[DenseMatrix[Complex]] = ArrayBuffer.fill(radius_num)(
      DenseMatrix.fill(impl.head.rows, impl.head.cols)(Complex(0, 0))
    )
    for {
      dd <- 0 until depth_num
      ff <- 0 until freq_num
      rr <- 0 until radius_num
    } {
      result(rr) = coef(dd)(ff, rr) * impl(rr)
    }
    result.toVector
  }
}
