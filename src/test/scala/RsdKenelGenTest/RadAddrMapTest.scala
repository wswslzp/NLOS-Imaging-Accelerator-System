package RsdKenelGenTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.RsdGenCoreArray.RadAddrMap
import Config.RsdKernelConfig.rsd_cfg
import breeze.linalg.{DenseMatrix, csvwrite}
import java.io._

object RadAddrMapTest extends App{
  val addr_mat = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)

  SimConfig
    .allOptimisation
    .workspacePath("tb")
    .compile(RadAddrMap(rsd_cfg))
    .doSim("RadAddrMap_tb"){dut=>
      for(col <- 0 until rsd_cfg.kernel_size.last){
        wait(10)
        dut.io.col_addr #= col
        wait(10)
        for(row <- 0 until rsd_cfg.kernel_size.head){
          addr_mat(row, col) = dut.io.pixel_addrs(row).toInt.toDouble
        }
      }
      csvwrite(new File("tmp/addr_map_test/hard_addr_mat.csv"), addr_mat)
      simSuccess()
    }
}
