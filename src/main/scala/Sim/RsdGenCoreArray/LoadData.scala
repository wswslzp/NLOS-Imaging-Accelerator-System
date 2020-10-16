package Sim.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze.linalg._
import breeze.math._
import java.io._

package object LoadData {
  def loadDoubleMatrix(filename: String): DenseMatrix[Double] = csvread(
    new File(filename)
  )
  def loadComplexMatrix(real_part_filename: String, imag_part_filename: String): DenseMatrix[Complex] = {
    val real_part_mat = csvread(new File(real_part_filename))
    val imag_part_mat = csvread(new File(imag_part_filename))
    val out_mat = DenseMatrix.tabulate(real_part_mat.rows, real_part_mat.cols){case(i, j) =>
      Complex(real_part_mat(i, j), imag_part_mat(i, j))
    }
    out_mat
  }
}
