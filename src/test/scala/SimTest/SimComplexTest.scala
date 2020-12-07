package SimTest

import java.io.File

import spinal.core._
import spinal.core.sim._
import breeze.math._
import breeze.linalg._
import Util._
import Config.HComplexConfig

import scala.collection.mutable
import spinal.lib._

object SimComplexTest extends App{
  case class TestSC(cfg: HComplexConfig) extends Component {

    val io = new Bundle {
      val xin = in(HComplex(cfg))
      val xout = out(HComplex(cfg))
      val xin1 = in(HComplex(HComplexConfig(9, 7)))
      val xout1 = out(HComplex(HComplexConfig(17, -1)))
    }

    io.xout1 := io.xin1

    io.xout := Delay(io.xin, 1)
  }

  val cfg = HComplexConfig(38, 26)

  new File("tmp/test_sc").mkdir()
  val report = SpinalConfig(
    targetDirectory = "tmp/test_sc",
    headerWithDate = true
  ).addTransformationPhase(
    new SwapTagPhase(SimPublic, Verilator.public)
  ).generateVerilog(
    TestSC(cfg)
  )

  val module_compiled = SimConfig.withWave.withVerilator.allOptimisation.workspacePath("tb").compile(report)

  module_compiled.doSim("TestSC_tb") {dut=>
    import Sim.SimComplex._

    dut.clockDomain.forkStimulus(2)
    dut.clockDomain.waitSampling()

    fork{
      SimTimeout(300)
    }

    for(i <- 0 to 5) {
      val inputData = Complex(-Math.exp(i*2), Math.exp(i*2-1))
      dut.io.xin #= inputData
      dut.io.xin1 #= inputData
      println(s"input: $inputData")
      dut.clockDomain.waitSampling()
      println(s"dat2input: ${dut.io.xin.toComplex}")
      dut.clockDomain.waitSampling(2)
      println(s"output: ${dut.io.xout.toComplex}")
    }
    simSuccess()

  }
}
