package Sim.NlosCore

import java.io.File
import Config.RsdKernelConfig._
import Sim.RsdGenCoreArray.Computation
import Sim.write_image
import breeze.linalg._
import breeze.math.Complex
import breeze.signal._

object Tester {
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
//  val uin_fft = uin.map(fourierTr(_))
  val uin_fft = uin.map(fourierTr(_).map(_ / (uin.head.rows*uin.head.cols)))

  def testMacResult(mac_result: Array[DenseMatrix[Complex]]): Unit = {
    import breeze.signal.iFourierTr
    val uout = mac_result.map(iFourierTr(_))
    val uout_abs = uout.map(_.map(_.abs))

    val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
      var umax = 0d
      for(d <- 0 until rsd_cfg.depth_factor) {
        if (uout_abs(d)(x, y) > umax) {
          umax = uout_abs(d)(x, y)
        }
      }
      umax
    }

    val uout_abs_max_flip = fliplr(uout_abs_max)
    write_image(uout_abs_max_flip, "tb/NlosCore/mac_res_test.jpg")
  }

  def testPostProc(post_proc_result: DenseMatrix[Double], path: String = "tb/NlosNoDriver"): Unit = {
    csvwrite(
      new File(s"$path/hard_out.csv"),
      post_proc_result
    )

    write_image(post_proc_result, s"$path/nlos_hard_out.jpg")
  }

  def testFinal(finalResult: Array[DenseMatrix[Complex]]): Unit = {
    val uout_abs = finalResult.map(_.map(_.abs))

    val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
      var umax = 0d
      for(d <- 0 until rsd_cfg.depth_factor) {
        if (uout_abs(d)(x, y) > umax) {
          umax = uout_abs(d)(x, y)
        }
      }
      umax
    }

    csvwrite(
      new File("tb/NlosCore/hard_out.csv"),
      uout_abs_max
    )

    val uout_abs_max_flip = fliplr(uout_abs_max)
    write_image(uout_abs_max_flip, "tb/NlosCore/nlos_hard_out.jpg")
  }

  def testRsdFuinProd(prod: Array[Array[DenseMatrix[Complex]]]): Unit = {
    val kernel_size = (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    val uout = Array.tabulate(rsd_cfg.depth_factor) { d =>
      val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
      for(f <- rsd_cfg.freqRange) {
        uout_f += prod(d)(f)
      }
      if(d == 10) {
        csvwrite(
          new File("tb/NlosCore/prod_mac_res10.csv"),
          uout_f.map(_.real)
        )
      }
      iFourierTr(uout_f)
    }
    val uout_abs = uout.map(_.map(_.abs))
    val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
      var umax = 0d
      for(d <- rsd_cfg.depthRange) {
        if (uout_abs(d)(x, y) > umax) {
          umax = uout_abs(d)(x, y)
        }
      }
      umax
    }
    val uout_abs_max_flip = fliplr(uout_abs_max)
    write_image(uout_abs_max_flip, s"tb/NlosCore/nlos_test_prod.jpg")

  }

  def testFUinAndRSDK(fuin: Array[DenseMatrix[Complex]], rsdk: Array[Array[DenseMatrix[Complex]]], fname: String = "nlos_test_fuin_and_rsdk"): Unit = {
    val kernel_size = (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    val uout = Array.tabulate(rsd_cfg.depth_factor) { d =>
      val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
      for(f <- rsd_cfg.freqRange) {
        val rsd_kernel = rsdk(d)(f)
        uout_f += rsd_kernel *:* fuin(f)
        if(d == 10){
          new File("tmp/macres/d10").mkdirs()
          csvwrite(new File(s"tmp/macres/d10/k${f}_real.csv"), rsd_kernel.map(_.real))
          csvwrite(new File(s"tmp/macres/d10/k${f}_imag.csv"), rsd_kernel.map(_.imag))
          csvwrite(new File(s"tmp/macres/d10/f${f}_real.csv"), fuin(f).map(_.real))
          csvwrite(new File(s"tmp/macres/d10/f${f}_imag.csv"), fuin(f).map(_.imag))
        }
      }
      if(d == 10) {
        csvwrite(
          new File("tb/NlosCore/soft_mac_res10.csv"),
          uout_f.map(_.real)
        )
      }
      iFourierTr(uout_f)
    }
    val uout_abs = uout.map(_.map(_.abs))
    val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
      var umax = 0d
      for(d <- rsd_cfg.depthRange) {
        if (uout_abs(d)(x, y) > umax) {
          umax = uout_abs(d)(x, y)
        }
      }
      umax
    }
    val uout_abs_max_flip = fliplr(uout_abs_max)
    write_image(uout_abs_max_flip, s"tb/NlosCore/$fname.jpg")
  }

  def testFUin(fuin: Array[DenseMatrix[Complex]]): Unit = {
    testFUinAndRSDK(fuin, rsd_kernel, "nlos_test_fuin")
  }

  def testRSDK(rsdk: Array[Array[DenseMatrix[Complex]]]): Unit = {
    testFUinAndRSDK(uin_fft, rsdk, "nlos_test_rsdk")
  }
}
