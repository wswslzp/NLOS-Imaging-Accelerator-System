package FFT2dTest

import Config._
import Core.FFT2d._
import Fpga.FFT2d._
import Sim.SimFix._
import Sim.SimComplex._
import Util._
import breeze._
import breeze.math.Complex
import org.bytedeco.javacpp.indexer._
import org.bytedeco.opencv.global.opencv_core._
import org.bytedeco.opencv.global.opencv_imgcodecs._
import org.bytedeco.opencv.global.opencv_imgproc._
import org.bytedeco.opencv.opencv_core._
import spinal.core._
import spinal.core.sim._
import spinal.lib._

import scala.collection.mutable

object FFT2dv1Test extends App{
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

  case class SFFT2d(cfg: FFTConfig) extends Component {
    import FFT2d._
    val io = new Bundle {
      val line_in = slave(Flow(HComplex(cfg.hComplexConfig)))
      val line_out = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
    }

    io.line_out <> fft2(io.line_in, cfg.row, cfg.point)
  }

  case class FFT2IFFT_2d(cfg: FFTConfig) extends Component {
    import FFT2d._
    import IFFT2d._
    val io = new Bundle {
      val line_in = slave(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
      val pixel_in = slave(Flow(HComplex(cfg.hComplexConfig)))
      val line_out = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
//      val pixel_out = master(Flow(HComplex(cfg.hComplexConfig)))
    }

    val fft2_inst = FFT2dv1(cfg)
//    fft2_inst.io.col_line_in.valid := False
//    fft2_inst.io.col_line_in.payload.foreach(_ := HC(0, 0, cfg.hComplexConfig))
//    fft2_inst.io.row_pix_in << io.pixel_in
    fft2_inst.io.col_line_in << io.line_in
    fft2_inst.io.row_pix_in.valid := False
    fft2_inst.io.row_pix_in.payload := HC(0, 0, cfg.hComplexConfig)
//    fft2_inst.io.mode := True // row pixel in
    fft2_inst.io.mode := False // col line in
    fft2_inst.io.inverse := False
//    val fft_out = fft2_inst.io.col_line_out
    val fft_out = fft2_inst.io.row_pix_out

//    val ifft2_inst = FFT2dv1(cfg)
//    ifft2_inst.io.col_line_in << fft_out
//    ifft2_inst.io.mode := False
//    ifft2_inst.io.inverse := True
//    ifft2_inst.io.row_pix_in.valid := False
//    ifft2_inst.io.row_pix_in.payload := HC(0, 0, cfg.hComplexConfig)
//    io.pixel_out << ifft2_inst.io.row_pix_out

//    io.line_out <> fft2(fft2(io.line_in, cfg.row, cfg.point), True, cfg.row)
//    io.line_out <> fft2(fft_out, True, cfg.row)
    io.line_out <> fft2(fft_out, True, cfg.row, cfg.point)
  }

  import linalg._
  val fft2_in = load_image("tb/FFT2d_tb/data/t1.png")
  write_image(fft2_in, "tb/FFT2d_tb/inimg_resize.jpg")

  val first_fft_col_out = mutable.Queue[Complex]()
  val first_fft_row_out = mutable.Queue[Complex]()
  val int_mem_pix_out = mutable.Queue[Complex]()
  val int_mem_col_out = mutable.Queue[Complex]()
  val last_fft_col_out = mutable.Queue[Complex]()
  val last_fft_pix_out = mutable.Queue[Complex]()

  val compiled = SimConfig .withWave .allOptimisation .workspacePath("tb/FFT2d_tb") .compile(FFT2IFFT_2d(fft_config))

  for (_ <- 0 until 5) {
    first_fft_col_out.clear()
    first_fft_row_out.clear()
    int_mem_col_out.clear()
    int_mem_pix_out.clear()

    compiled.doSim("FFT2IFFT2d_tb") {dut =>

      dut.io.pixel_in.valid #= false
      dut.io.line_in.valid #= false
      dut.clockDomain.forkStimulus(2)
      dut.clockDomain.waitSampling()

      var t1 = 0
      dut.clockDomain onSamplings {
        val cond1 = dut.fft2_inst.first_fft.io.col_line_out.valid.toBoolean
        val cond2 = dut.fft2_inst.first_fft.io.row_pix_out.valid.toBoolean
        if (cond1 && t1 < 10) {
          t1 += 1
          first_fft_col_out.enqueue(dut.fft2_inst.first_fft.io.col_line_out.payload(2).toComplex)
        }
        if (cond2 && t1 < 10) {
          t1 += 1
          first_fft_row_out.enqueue(dut.fft2_inst.first_fft.io.row_pix_out.payload.toComplex)
        }
      }

      var t2 = 0
      dut.clockDomain onSamplings {
        val cond1 = dut.fft2_inst.int_mem.io.row_pix_out.valid.toBoolean
        val cond2 = dut.fft2_inst.int_mem.io.col_line_out.valid.toBoolean
        if (cond1 && t2 < 10) {
          t2 += 1
          int_mem_pix_out.enqueue(dut.fft2_inst.int_mem.io.row_pix_out.payload.toComplex)
        }
        if (cond2 && t2 < 10) {
          t2 += 1
          int_mem_col_out.enqueue(dut.fft2_inst.int_mem.io.col_line_out.payload(2).toComplex)
        }
      }

      var t3 = 0
      dut.clockDomain onSamplings {
        val cond1 = dut.fft2_inst.last_fft.io.row_pix_out.valid.toBoolean
        val cond2 = dut.fft2_inst.last_fft.io.col_line_out.valid.toBoolean
        if (cond1 && t3 < 10) {
          t3 += 1
          last_fft_pix_out.enqueue(dut.fft2_inst.last_fft.io.row_pix_out.payload.toComplex)
        }
        if (cond2 && t3 < 10) {
          t3 += 1
          last_fft_col_out.enqueue(dut.fft2_inst.last_fft.io.col_line_out.payload(2).toComplex)
        }
      }

      forkJoin(
        () => {
          dut.io.pixel_in.valid #= true
          for (i <- 0 until fft_config.row) {
            for (j <- 0 until fft_config.point) {
              dut.io.pixel_in.payload.real #= fft2_in(i, j)
              dut.io.pixel_in.payload.imag #= 0
              dut.clockDomain.waitSampling()
            }
          }
          dut.io.pixel_in.valid #= false
        }
        ,

        () => {
          dut.io.line_in.valid #= true
          for (i <- 0 until fft_config.row) {
            for (j <- 0 until fft_config.point) {
              dut.io.line_in.payload(j).real #= fft2_in(i, j)
              dut.io.line_in.payload(j).imag #= 0
            }
              dut.clockDomain.waitSampling()
          }
          dut.io.line_in.valid #= false
        }
      )

      val fft2_out = DenseMatrix.zeros[Double](fft_config.row, fft_config.point)
      val fft2_out_1 = DenseMatrix.zeros[Double](fft_config.row, fft_config.point)
      var row_addr = 0
      var flag = false
      var flag1 = false
      var pixel_addr = 0

      fork {
        waitUntil(flag)
        println(s"last fft col out 0 to 9 is $last_fft_col_out")
        println(s"last fft row out 0 to 9 is $last_fft_pix_out")
//        println(s"first fft col out 0 to 9 is ${first_fft_col_out}\n")
//        println(s"first fft row out 0 to 9 is ${first_fft_row_out}\n")
//        println(s"int mem row out 0 to 9 is ${int_mem_pix_out}\n")
//        println(s"int mem col out 0 to 9 is ${int_mem_col_out}\n")
      }

//      dut.clockDomain.onSamplings {
//        if (dut.io.pixel_out.valid.toBoolean) {
//          val row = pixel_addr / fft_config.point
//          val col = pixel_addr % fft_config.point
//          fft2_out_1(row, col) = dut.io.pixel_out.payload.toComplex.abs
//          pixel_addr += 1
//        }
//
//        if (pixel_addr == (fft_config.row * fft_config.point) & !flag1) {
//          flag1 = true
//          println("The output image from pixel out has been collected.")
//          write_image(fft2_out_1, "tb/FFT2d_tb/fft_hw2.jpg")
//          dut.clockDomain.waitSampling(10)
//          simSuccess()
//        }
//      }
//
      dut.clockDomain onSamplings {
        if (dut.io.line_out.valid.toBoolean) {
//          println(s"current row is $row_addr")
          val row = linalg.DenseVector.fill(fft_config.point)(0d)
          for (i <- 0 until fft_config.point) {
            val oreal = dut.io.line_out.payload(i).real.toDouble
            val oimag = dut.io.line_out.payload(i).imag.toDouble
            row(i) = Math.sqrt(oreal*oreal + oimag*oimag)
          }
          fft2_out(row_addr, ::) := row.t
          row_addr += 1
        }

        if (row_addr == fft_config.row && !flag) {
          flag = true
          println("The output image has been collected.")
          val out_img = fft2_out//.t
          write_image(out_img, "tb/FFT2d_tb/fft_hw1.jpg")
        }
      }

      fork {
//        waitUntil(pixel_addr == ( fft_config.row * fft_config.point ))
        waitUntil(flag)
        dut.clockDomain.waitSampling(10)
        simSuccess()
      }

      dut.clockDomain.waitSampling(100000)
    }
  }

}
