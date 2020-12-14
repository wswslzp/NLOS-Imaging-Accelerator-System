package MacArrayTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.MacArray._
import Sim.SimComplex._
import breeze.linalg._
import breeze.math._
import Fpga.MacArray.RowAcc

object ComplexAccTest extends App{
  import Config.RsdKernelConfig.rsd_cfg
  val cfg = Config.HComplexConfig(8, 0)

  val test_vec = DenseVector.tabulate(10)(Complex(_, 0))
  val test_mat = DenseMatrix.tabulate(3, 10)(
    Complex(_, _)
  )
//  val acc_result = test_vec.reduce(_ + _)
  val acc_result = DenseVector.tabulate(10){i =>
    test_mat(0, i) + test_mat(1, i) + test_mat(2, i)
  }
  println(s"The test vector is $test_mat. And the acc result is $acc_result")

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb")
//    .compile(ComplexAcc(cfg))
    .compile{
      val dut = RowAcc(cfg, 10)
      dut
    }
    .doSim("RowAcc_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.data_in.valid #= false
      dut.io.clear #= false
      dut.io.acc_in_addr #= 0
      dut.io.pipe_out_addr #= 0
      dut.clockDomain.waitSampling()

      for(i <- 0 until 3){
        for(c <- 0 until 10){
          dut.io.data_in.valid #= true
          dut.io.data_in.payload #= test_mat(i, c)
          dut.io.acc_in_addr #= c
          dut.clockDomain.waitSampling()
        }
        dut.io.data_in.valid #= false
        dut.clockDomain.waitSampling(10)
      }
//      println(s"the hardware acc result is ${dut.io.data_out.toComplex}")

      dut.clockDomain.waitSampling(5)
      dut.io.clear #= true
      dut.clockDomain.waitSampling()
      dut.io.clear #= false

      dut.clockDomain.waitSampling(150)

      simSuccess()
    }


}
