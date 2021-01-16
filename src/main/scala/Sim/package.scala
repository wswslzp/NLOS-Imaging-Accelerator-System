import breeze.linalg
import breeze.linalg.{DenseMatrix, csvread}
import breeze.math.Complex
import org.bytedeco.javacpp.indexer.UByteIndexer
import org.bytedeco.opencv.global.opencv_core.CV_8U
import org.bytedeco.opencv.global.opencv_imgcodecs.{imread, imwrite}
import org.bytedeco.opencv.global.opencv_imgproc.{CV_RGB2GRAY, cvtColor, resize}
import org.bytedeco.opencv.opencv_core.{Mat, Size}

import java.io.File

package object Sim {

  def load_image(input_img: String, rows: Int, cols: Int): linalg.DenseMatrix[Double] = {
    var inimg = imread(input_img)
    resize(inimg, inimg, new Size(rows, cols))
    cvtColor(inimg, inimg, CV_RGB2GRAY)
    val inimg_indexr = inimg.createIndexer().asInstanceOf[UByteIndexer]
    val fft_in = linalg.DenseMatrix.tabulate(rows, cols){case(i, j)=>
      inimg_indexr.get(i.toLong, j.toLong).toDouble
    }
    fft_in
  }

  def write_image(input: linalg.DenseMatrix[Double], path: String): Unit = {
    val input_max: Double = linalg.max(input)
    val input_min: Double = linalg.min(input)
    //    println(s"the max of input is $input_max")

    val outimg = Mat.zeros(input.rows, input.cols, CV_8U).asMat()
    val outimg_indexer = outimg.createIndexer().asInstanceOf[UByteIndexer]

    for{
      i <- 0 until input.rows
      j <- 0 until input.cols
    }{
      outimg_indexer.put(i.toLong, j.toLong, ( ( ( input(i, j) - input_min) * 255) / ( input_max - input_min) ).toByte)
    }
    imwrite(path, outimg)
  }

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
