package MacArrayTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.MacArray._
import Sim.SimComplex._
import breeze.linalg.DenseVector
import breeze.math._
import Fpga.MacArray.RowMac

object ComplexAccTest extends App{
  import Config.RsdKernelConfig.rsd_cfg
  val cfg = Config.HComplexConfig(8, 8)

  val test_vec = DenseVector.tabulate(10)(Complex(_, 0))
  val acc_result = test_vec.reduce(_ + _)
  println(s"The test vector is $test_vec. And the acc result is $acc_result")

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb")
//    .compile(ComplexAcc(cfg))
    .compile(RowMac(rsd_cfg))
    .doSim("ComplexAcc_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.data_in.valid #= false
      dut.io.clear #= false
      dut.io.acc_in_addr #= 2
      dut.io.pipe_out_addr #= 1
      dut.clockDomain.waitSampling()

      test_vec.foreach{dat=>
        dut.io.data_in.valid #= true
        dut.io.data_in.payload #= dat
        dut.clockDomain.waitSampling()
        println(s"the hardware input is ${dut.io.data_in.payload.toComplex}")
      }
      dut.io.data_in.valid #= false
      dut.clockDomain.waitSampling()
      println(s"the hardware acc result is ${dut.io.data_out.toComplex}")

      dut.clockDomain.waitSampling(5)
      dut.io.clear #= true
      dut.clockDomain.waitSampling()
      dut.io.clear #= false

      dut.clockDomain.waitSampling(10)

      simSuccess()
    }


}
