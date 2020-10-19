package SimTest

import spinal.core._
import spinal.core.sim._
import breeze.math._
import breeze.linalg._
import Util._

import scala.collection.mutable

object SimComplexTest extends App{
  case class TestSC() extends Component {
    val io = new Bundle {
      val xin = in(HComplex(19, 19))
      val xout = out(HComplex(19, 19))
    }

    io.xout := RegNext( io.xin.conj )
  }

  SimConfig
    .withWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .compile(TestSC())
    .doSim("TestSC_tb") {dut=>
      import Sim.SimComplex._

      dut.clockDomain.forkStimulus(2)
      dut.clockDomain.waitSampling()

      fork{
        SimTimeout(300)
      }

      for(i <- 0 to 3) {
        val inputData = Complex(Math.exp(i), Math.exp(i+1))
        dut.io.xin #= inputData
        println(s"input: $inputData")
        dut.clockDomain.waitSampling()
        println(s"dat2input: ${dut.io.xin.toComplex}")
        dut.clockDomain.waitSampling(2)
        println(s"output: ${dut.io.xout.toComplex}")
      }
      simSuccess()

    }
}
