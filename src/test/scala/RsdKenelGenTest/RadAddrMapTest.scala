package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.RsdGenCoreArray._
import Config.RsdKernelConfig._
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import java.io._
import Config.{HComplexConfig, RsdKernelConfig}
import RsdKernelConfig._
import Sim.RsdGenCoreArray.{Computation, LoadData}
import SimTest.NlosSystemSimTest.write_image
import breeze.math.Complex
import breeze.signal._

object RadAddrMapTest extends App{
  val addr_mat = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)

  SimConfig
    .allOptimisation
    .workspacePath("tb")
    .compile(RadAddrMap(rsd_cfg))
    .doSim("RadAddrMap_tb"){dut=>
      for(col <- 0 until rsd_cfg.kernel_size.last){
        sleep(1)
        dut.io.col_addr #= col
        sleep(1)
        for(row <- 0 until rsd_cfg.kernel_size.head){
          addr_mat(row, col) = dut.io.pixel_addrs(row).toInt.toDouble
        }
      }
    }

  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)
  val uin_fft = uin.map(fourierTr(_))

  // Here we use the hardware-generated address map
  val uout = Array.tabulate(rsd_cfg.depth_factor){d=>
    val uout_f = DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
    for(f <- 0 until rsd_cfg.freq_factor) {
      val rsd_kernel_rad = rsd(d)(f)
      val rsd_kernel = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last){(x, y)=>
        val idx = scala.math.min(addr_mat(x, y).toInt, rsd_cfg.impulse_sample_point-1)
        rsd_kernel_rad(idx)
      }
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    iFourierTr(uout_f)
  }
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
  write_image(uout_abs_max_flip, "tmp/addr_map_test/nlos_map_test.jpg")

}
