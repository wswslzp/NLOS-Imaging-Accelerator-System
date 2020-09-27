package Sim.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze._
import breeze.linalg.DenseVector

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
      val tmp = numerics.exp(2*Complex(0, 1)*Math.PI*wave(rr, dd)*distance(ff, dd)) / wave(rr, dd) * timeshift(ff, dd)
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
      impl_rad_indx = (R-1) - Math.min(impl_rad_indx, R-1)
//      if (r == 0 && (ii < 4) && (jj < 4)) {
//        println(s"imp_0 is \n ${imp_r.toString()}")
//        println(s"The impl_rad_indx($ii, $jj) = $impl_rad_indx")
//      }
      out_impl(r)(ii, jj) = imp_r(impl_rad_indx)
    }
    out_impl
  }

//  @deprecated
//  def generateRSDkernel(
//                       coef: Vector[DenseMatrix[Complex]],
//                       impl: Vector[DenseMatrix[Complex]]
//                       ): Vector[DenseMatrix[Complex]] = {
//    import scala.collection.mutable.ArrayBuffer
//    //coef : (depth, freq, radius)
//    //impulse: (radius, R)
//    //result: (depth, freq, H, W)
//    val freq_num = coef.head.rows
//    val depth_num = coef.length
//    val radius_num = coef.head.cols
//    val result: ArrayBuffer[DenseMatrix[Complex]] = ArrayBuffer.fill(radius_num)(
//      DenseMatrix.fill(impl.head.rows, impl.head.cols)(Complex(0, 0))
//    )
//    for {
//      dd <- 0 until depth_num
//      ff <- 0 until freq_num
//      rr <- 0 until radius_num
//    } {
//      // RSD kernel has no radius dimension
//      // TODO: This function is wrong
//      result(rr) = coef(dd)(ff, rr) * impl(rr)
//    }
//    result.toVector
//  }
//
  def generateRSDRadKernel(
                         coef: Vector[DenseMatrix[Complex]],
                         impulse_rad: DenseMatrix[Complex]
                       ): Vector[Vector[DenseVector[Complex]]] = {
    //coef : (depth, freq, radius)
    //impulse_rad: (radius, R)
    //result: (depth, freq, R)
    val freq_num = coef.head.rows
    val depth_num = coef.length
    val radius_num = coef.head.cols
    val R = impulse_rad.cols
    val result = Vector.fill(depth_num, freq_num)(DenseVector.fill(R)(Complex(0, 0)))
    for {
      dd <- 0 until depth_num
      ff <- 0 until freq_num
      rr <- 0 until radius_num
    } {
      val tmp = ( coef(dd)(ff, rr) * impulse_rad(rr, ::) ).toDenseVector
//      result(dd)(ff) += coef(dd)(ff, rr) * impulse_rad(rr, ::)
      result(dd)(ff) += tmp
    }
    result
  }

  // The address map from rad to mat
  def rsdKernelMap(kernel_size: (Int, Int), sample_point: Int): Vector[Int] = {
    val row_num = kernel_size._1
    val col_num = kernel_size._2
    (0 until kernel_size._1 * kernel_size._2).toVector map {idx =>
      val x = idx / row_num
      val y = idx % row_num
      Math.max(
        Math.min(
          sample_point - Math.sqrt(Math.pow(x - row_num/2, 2) + Math.pow(y - col_num/2, 2)) * sample_point/(row_num/2),
          sample_point - 1
        ),
        0
      ).toInt
    }
  }

  // restore the rsd kernel from rsd kernel rad
  def restoreRSD(rsd_kernel_rad: DenseVector[Complex], kernel_size: (Int, Int)): DenseMatrix[Complex] = {
    val addressMap = rsdKernelMap(kernel_size, rsd_kernel_rad.length)
    DenseMatrix.tabulate(kernel_size._1, kernel_size._2){(x, y)=>
      rsd_kernel_rad(addressMap(x * kernel_size._1 + y))
    }
  }

  def restoreRSD(rsd_kernel_rad: Vector[Vector[DenseVector[Complex]]], kernel_size: (Int, Int)): Vector[Vector[DenseMatrix[Complex]]] = {
    val addressMap = rsdKernelMap(kernel_size, rsd_kernel_rad.length)
    rsd_kernel_rad.map(_.map{kernel_rad=>
      DenseMatrix.tabulate(kernel_size._1, kernel_size._2){(x, y)=>
        kernel_rad(addressMap(x * kernel_size._1 + y))
      }
    })
  }


}
