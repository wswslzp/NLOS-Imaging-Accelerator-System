import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze._
import org.bytedeco.javacpp.indexer.UByteIndexer
import org.bytedeco.opencv.global.opencv_core.{CV_8U, print}
import org.bytedeco.opencv.global.opencv_imgcodecs.{imread, imwrite}
import org.bytedeco.opencv.global.opencv_imgproc.{CV_RGB2GRAY, cvtColor, resize}
import org.bytedeco.opencv.opencv_core.{Mat, Rect, Size}

object FFTMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )

//  val config = SpinalConfig(
//    targetDirectory = "rtl", headerWithDate = true, oneFilePerComponent = true
//  )
//  SpinalVerilog(config)(
//    FFT2d(fft_config)
//  ).printPruned()

  def load_image(input_img: String): linalg.DenseMatrix[Double] = {
    var inimg = imread(input_img)
    resize(inimg, inimg, new Size(128, 128))
    cvtColor(inimg, inimg, CV_RGB2GRAY)
    val inimg_indexr = inimg.createIndexer().asInstanceOf[UByteIndexer]
    val fft_in = linalg.DenseMatrix.tabulate(128, 128){case(i, j)=>
      inimg_indexr.get(i.toLong, j.toLong).toDouble
    }
    fft_in
  }

  def fft2d_func(input_img: linalg.DenseMatrix[Double]): linalg.DenseMatrix[breeze.math.Complex] = {
    val fft_out = signal.fourierTr(input_img).map(_/(128*128))
    val fft_out_mag = fft_out.map(_.abs)
    val fft_out_mag_max = linalg.max(fft_out_mag)

    val outimg = Mat.zeros(128, 128, CV_8U).asMat()
    val outimg_submat = new Mat(outimg, new Rect(0, 0, 10, 10))
    val outimg_indexer = outimg.createIndexer().asInstanceOf[UByteIndexer]
    for{
      i <- 0 until 128
      j <- 0 until 128
    }{
      outimg_indexer.put(i.toLong, j.toLong, ( ( fft_out_mag(i, j) * 255) / fft_out_mag_max ).toByte)
    }
    print(outimg_submat)
    imwrite("fft.jpg", outimg)
    fft_out
  }

  SimConfig
    .withConfig(SpinalConfig(defaultClockDomainFrequency = FixedFrequency(1 GHz)))
    .withWave
    .allOptimisation
    .workspacePath("tb/FFT2d_tb")
    .compile(FFT2d(fft_config))
    .doSim("FFT2d_tb") {dut =>

    }

}
