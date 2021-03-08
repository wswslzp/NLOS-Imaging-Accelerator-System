package SimTest

import java.io.File

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Sim.RsdGenCoreArray._
import breeze._
import breeze.linalg._
import breeze.math._
import org.bytedeco.javacpp.indexer.UByteIndexer
import org.bytedeco.opencv.global.opencv_core.CV_8U
import org.bytedeco.opencv.global.opencv_imgcodecs.imwrite
import org.bytedeco.opencv.opencv_core.Mat
import signal._

object NlosSystemSimTest extends App{
  import RsdKernelConfig._

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

  val freq_num = 69
  val depth_num = 51

  println("Begin loading...")
  println("Begin input generation...")
  val coef = Computation.generateCoef(wave, distance, timeshift)
  val rsd = Computation.generateRSDRadKernel(coef, impulse)
  println("Generation done!")

  println("Begin loading input image...")
  val uin = Array.tabulate(freq_num){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/uin/letter_4/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/uin/letter_4/imag/uin_${idx+1}.csv"
    )
  }
  println("Loading image done!")
//  val rsd_kernel = Computation.restoreRSD(rsd, (uin.head.rows, uin.head.cols))

  println("Doing input image's FFT2d...")
  val uin_fft = uin.map(fourierTr(_))
//  println(s"${uin_fft(0)(0 to 5, 0 to 5)}")
  println("Done input image FFT2d!")

  val kernel_size = (uin.head.rows, uin.head.cols)
  println(kernel_size)

  println("Multiply RSD kernel with input image's freq...")
  val uout = Array.tabulate(depth_num) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- 0 until freq_num) {
      val rsd_kernel_rad = rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    if(depth == 10){
      csvwrite(
        new File("tmp/soft_uoutf10.csv"),
        uout_f.map(_.real)
      )
    }
    iFourierTr(uout_f)
  }
  println("Done RSD kernel convolution!")

  println("Generating output image...")
  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
    var umax = 0d
    for(d <- 0 until depth_num) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }
  csvwrite(new File("tmp/soft_uout_abs_max.csv"), uout_abs_max)
  println("Output image has been generated!")
  println(s"output size: cols = ${uout_abs_max.cols}")

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "src/test/scala/SimTest/nlos_out.jpg")
//  val uout_abs_max_maxv: Double = max(uout_abs_max)
//  val uout_abs_max_minv: Double = min(uout_abs_max)
//  val uout_abs_norm = (uout_abs_max - uout_abs_max_minv)/(uout_abs_max_maxv - uout_abs_max_minv) * 256


}
