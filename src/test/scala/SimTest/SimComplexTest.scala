package SimTest

import spinal.core._
import spinal.core.sim._
import breeze.math._
import breeze.linalg._
import Util._
import Config.HComplexConfig

import scala.collection.mutable

object SimComplexTest extends App{
  case class TestSC(cfg: HComplexConfig) extends Component {
    val io = new Bundle {
      val xin = in(HComplex(cfg))
      val xout = out(HComplex(cfg))
    }

    io.xout := RegNext( io.xin.conj )
  }

  val cfg = HComplexConfig(0, 32)

  SimConfig
    .withWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .compile(TestSC(cfg))
    .doSim("TestSC_tb") {dut=>
      import Sim.SimComplex._

      dut.clockDomain.forkStimulus(2)
      dut.clockDomain.waitSampling()

      fork{
        SimTimeout(300)
      }

      for(i <- 3 to 5) {
        val inputData = Complex(Math.exp(-i), Math.exp(-i-1))
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
