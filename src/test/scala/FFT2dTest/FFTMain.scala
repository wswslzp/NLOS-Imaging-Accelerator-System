package FFT2dTest

import Config._
import Core.FFT2d.FFT2d
import breeze._
import org.bytedeco.javacpp.indexer._
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._
import spinal.core.sim._
import Sim.SimFix._

object FFTMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(9, 9),
    point = 64,
    row = 64
  )

  def load_image(input_img: String): linalg.DenseMatrix[Double] = {
    var inimg = imread(input_img)
    resize(inimg, inimg, new Size(fft_config.row, fft_config.point))
    cvtColor(inimg, inimg, CV_RGB2GRAY)
    val inimg_indexr = inimg.createIndexer().asInstanceOf[UByteIndexer]
    val fft_in = linalg.DenseMatrix.tabulate(fft_config.row, fft_config.point){case(i, j)=>
      inimg_indexr.get(i.toLong, j.toLong).toDouble
    }
    fft_in
  }

  def write_image(input: linalg.DenseMatrix[Double], path: String): Unit = {
    val input_max = linalg.max(input)
    println(s"the max of input is $input_max")

    val outimg = Mat.zeros(input.rows, input.cols, CV_8U).asMat()
    val outimg_indexer = outimg.createIndexer().asInstanceOf[UByteIndexer]

    for{
      i <- 0 until input.rows
      j <- 0 until input.cols
    }{
      outimg_indexer.put(i.toLong, j.toLong, ( ( input(i, j) * 255) / input_max ).toByte)
    }
    imwrite(path, outimg)
  }

  def fft2d_func(input_img: linalg.DenseMatrix[Double]): linalg.DenseMatrix[breeze.math.Complex] = {
    val fft_out = signal.fourierTr(input_img).map(_ / (input_img.rows * input_img.cols))
    val fft_out_mag = fft_out.map(_.abs)
    write_image(fft_out_mag, "tb/FFT2d_tb/fft_out_image.jpg")
    fft_out
  }

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb/FFT2d_tb")
    .compile(FFT2d(fft_config))
    .doSim("FFT2d_tb") {dut =>
      import linalg._
      val fft2_in = load_image("tb/FFT2d_tb/data/test.jpg")
      val true_res = fft2d_func(fft2_in)

      dut.io.line_in.valid #= false
      dut.clockDomain.doStimulus(2)

      dut.io.line_in.valid #= true
      for (i <- 0 until fft_config.row) {
        for (j <- 0 until fft_config.point) {
          dut.io.line_in.payload(j).real #= fft2_in(i, j)
          dut.io.line_in.payload(j).imag #= 0
        }
        dut.clockDomain.waitSampling()
      }
      dut.io.line_in.valid #= false

      val fft2_out = DenseMatrix.zeros[Double](fft_config.row, fft_config.point)
      var row_addr = 0
      println("shit")
      dut.clockDomain onSamplings {
        if (dut.io.line_out.valid.toBoolean) {
          println(s"current row is $row_addr")
          val row = linalg.DenseVector.fill(fft_config.point)(0d)
          for (i <- 0 until fft_config.point) {
            val oreal = dut.io.line_out.payload(i).real.toDouble
            val oimag = dut.io.line_out.payload(i).imag.toDouble
            row(i) = Math.sqrt(oreal*oreal + oimag*oimag)
          }
          fft2_out(row_addr, ::) := row.t
          row_addr += 1
        }

        if (row_addr == fft_config.row) {
          println("The output image has been collected.")
          println(s"The true result: ${true_res(0 to 9, 0 to 9).toString()}\nThe output: ${fft2_out(0 to 9, 0 to 9).toString()}")
          write_image(fft2_out.asInstanceOf[linalg.DenseMatrix[Double]], "tb/FFT2d_tb/fft_hw.jpg") // TODO: Why the fft2_out is DenseMat[Nothing]
          simSuccess()
        }
      }

      dut.clockDomain.waitSampling(1000)
    }

}
