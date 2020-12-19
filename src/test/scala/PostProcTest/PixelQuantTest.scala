package PostProcTest

import Config.HComplexConfig
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.PostProc.PixelQuant
import breeze.linalg._
import Sim.SimFix._

import java.io.File

object PixelQuantTest extends App{
  val k = 100
  val test_mat = DenseMatrix.tabulate(k, k){(i, j)=>
    Math.pow((i-k/2)/10d, 2) + Math.pow((j-k/2)/5d, 2)
  }
  val test_mat_max = max(test_mat)
  val test_mat_min = min(test_mat)
  val color_depth = 256
  val quant_mat = test_mat.map{pix=>
    (pix - test_mat_min) * color_depth / (test_mat_max - test_mat_min)
  }

  val cfg = HComplexConfig(16, 16)
  val h_mat = DenseMatrix.zeros[Int](k, k)

  SimConfig
    .allOptimisation
    .withWave
    .workspacePath("tb")
    .compile(PixelQuant(cfg, log2Up(color_depth)))
    .doSim("PixelQuant_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.pix_in.valid #= false
      dut.io.lower_bound #= test_mat_min
      dut.io.upper_bound #= test_mat_max
      dut.clockDomain.waitSampling()

      forkJoin(
        () => {
          for(r <- 0 until test_mat.rows){
            dut.io.pix_in.valid #= true
            for(c <- 0 until test_mat.cols){
              dut.io.pix_in.payload #= test_mat(r, c)
              dut.clockDomain.waitSampling()
            }
            dut.io.pix_in.valid #= false
            dut.clockDomain.waitSampling(100)
          }
          simSuccess()
        }
        ,

        () => {
          for(r <- 0 until test_mat.rows){
            for(c <- 0 until test_mat.cols){
              dut.clockDomain.waitActiveEdgeWhere(dut.io.pix_out.valid.toBoolean)
              h_mat(r, c) = dut.io.pix_out.payload.toInt
              println(s"Got pixel ($r, $c)")
            }
          }

        }
      )
    }

  csvwrite(
    new File("tb/PixelQuant/s_mat.csv"),
    test_mat
  )

  csvwrite(
    new File("tb/PixelQuant/h_mat.csv"),
    h_mat.map(_.toDouble)
  )

}
