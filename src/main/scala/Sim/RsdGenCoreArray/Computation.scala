package Sim.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze._
import breeze.linalg._

import scala.collection.mutable.ArrayBuffer

package object Computation {
  import linalg.DenseMatrix
  import breeze.math.Complex

  def generateCoef(
                    wave: DenseMatrix[Double], // wave(radius, depth)
                    distance: DenseMatrix[Double], // distance(freq, depth)
                    timeshift: DenseMatrix[Complex] // timeshift(freq, depth)
                  ): Array[DenseMatrix[Complex]] = {
    val freq_num = distance.rows
    val depth_num = wave.cols
    val radius_num = wave.rows
    val coef = Array.fill(depth_num)(
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

  @deprecated
  def restoreImpl(impl_rad: DenseMatrix[Complex]): Array[DenseMatrix[Complex]] = {
    val radius_num = impl_rad.rows
    val R = impl_rad.cols
    val kernel_size = 2*R
    val out_impl = Array.fill(radius_num)(
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
      out_impl(r)(ii, jj) = imp_r(impl_rad_indx)
    }
    out_impl
  }

  def generateRSDRadKernel(
                         coef: Array[DenseMatrix[Complex]],
                         impulse_rad: DenseMatrix[Complex]
                       ): Array[Array[DenseVector[Complex]]] = {
    //coef : (depth, freq, radius)
    //impulse_rad: (R, radius)
    //result: (depth, freq, R)
    val freq_num = coef.head.rows
    val depth_num = coef.length
    val radius_num = coef.head.cols
    val R = impulse_rad.rows
    val result = Array.fill(depth_num, freq_num)(DenseVector.fill(R)(Complex(0, 0)))
    for {
      dd <- 0 until depth_num
      ff <- 0 until freq_num
      rr <- 0 until radius_num
    } {
      val tmp = impulse_rad(::, rr).map(_*coef(dd)(ff, rr))
      result(dd)(ff) += tmp
    }

    result
  }

  // The address map from rad to mat
  //    The idx map should be multiplied with a factor 1.1.
  def rsdKernelMap(kernel_size: (Int, Int), sample_point: Int, mapping_factor: Double = 1.1): Array[Int] = {
    val row_num = kernel_size._1
    val col_num = kernel_size._2
    val result_mat = DenseMatrix.zeros[Int](row_num, col_num)

    for {
      x <- 0 until row_num
      y <- 0 until col_num
    } {
      val xp = (x + row_num/2) % row_num
      val yp = (y + col_num/2) % col_num
      result_mat(xp, yp) = Math.max(
          Math.min(
            Math.sqrt(Math.pow(x - row_num/2, 2) + Math.pow(y - col_num/2, 2)) * mapping_factor,
            sample_point - 1
          ),
          0
        ).toInt
    }
    Array.tabulate(row_num * col_num){idx=>
      val row = idx / row_num
      val col = idx % row_num
      result_mat(row, col)
    }
  }

  def rsdKernelMap(): Array[Int] = {
    import Sim.RsdGenCoreArray.LoadData._
    val addressMap = loadDoubleMatrix("src/test/resource/data/map.csv")
    (0 until addressMap.rows * addressMap.cols).toArray map {idx=>
      val x = idx / addressMap.rows
      val y = idx % addressMap.rows
      addressMap(x, y).toInt - 1
    }
  }

  // restore the rsd kernel from rsd kernel rad
  def restoreRSD(rsd_kernel_rad: DenseVector[Complex], kernel_size: (Int, Int)): DenseMatrix[Complex] = {
    val addressMap = rsdKernelMap(kernel_size, rsd_kernel_rad.length)
//    val addressMap = rsdKernelMap()
    DenseMatrix.tabulate(kernel_size._1, kernel_size._2){(x, y)=>
      rsd_kernel_rad(addressMap(x * kernel_size._1 + y))
    }
  }

  def restoreRSD(rsd_kernel_rad: Array[Array[DenseVector[Complex]]], kernel_size: (Int, Int)): Array[Array[DenseMatrix[Complex]]] = {
    val addressMap = rsdKernelMap(kernel_size, rsd_kernel_rad.length)
    rsd_kernel_rad.map(_.map{kernel_rad=>
      DenseMatrix.tabulate(kernel_size._1, kernel_size._2){(x, y)=>
        kernel_rad(addressMap(x * kernel_size._1 + y))
      }
    })
  }


}
