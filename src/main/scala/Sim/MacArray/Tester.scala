package Sim.MacArray

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Sim.write_image
import breeze.linalg._
import breeze.math.Complex

object Tester {
  def testMacResult(mac_result: Array[DenseMatrix[Complex]]): Unit = {
    import breeze.signal.iFourierTr
    val uout = mac_result.map(iFourierTr(_))
    val uout_abs = uout.map(_.map(_.abs))

    val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y) =>
      var umax = 0d
      for (d <- 0 until rsd_cfg.depth_factor) {
        if (uout_abs(d)(x, y) > umax) {
          umax = uout_abs(d)(x, y)
        }
      }
      umax
    }
    val uout_abs_max_flip = fliplr(uout_abs_max)
    write_image(uout_abs_max_flip, "tb/RowMacArray/mac_res_test.jpg")
  }
}
