package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._

import scala.collection.mutable.ArrayBuffer
import breeze.linalg._
import java.io._

object AddrMapTest extends App{
  val row_num = 128
  val col_num = 128

  def buildAddrMap(sample_point: Int, mapping_factor: Double = 1.1): Array[Int] = {
    // Return the address LUT
    // The index of the LUT represents the linear address of the original image's pixels
    // The value of the LUT represents the linear address of the radius of image.

    val result_mat = ArrayBuffer.fill(row_num, col_num)(0)
    for {
      x <- 0 until row_num
      y <- 0 until col_num
    } {
      val xp = (x + row_num/2) % row_num
      val yp = (y + col_num/2) % col_num
      result_mat(xp)(yp) = Math.max(
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
      result_mat(row)(col)
    }
  }

  val addr_map = buildAddrMap(101)
  val addr_map_mat = DenseMatrix.tabulate(row_num, col_num){(x, y)=>
    addr_map(
      x * col_num + y
    )
  }
  new File("tmp/addr_map_test").mkdir()
  csvwrite(
    new File("tmp/addr_map_test/addr_map_mat.csv"),
    addr_map_mat.map(_.toDouble)
  )

  import Sim.RsdGenCoreArray.Computation._
  val true_map = rsdKernelMap((128, 128), 101)
  val true_map_mat = DenseMatrix.tabulate(row_num, col_num){(x, y)=>
    true_map(x * col_num+y).toDouble
  }
  csvwrite(
    new File("tmp/addr_map_test/true_map_mat.csv"),
    true_map_mat
  )

}
