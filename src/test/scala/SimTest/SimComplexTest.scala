package SimTest

import java.io.File

import spinal.core._
import spinal.core.sim._
import breeze.math._
import breeze.linalg._
import Util._
import Config.HComplexConfig
import Config.RsdKernelConfig._

import scala.collection.mutable
import spinal.lib._

object SimComplexTest extends App{
  case class TestSC() extends Component {

    val io = new Bundle {
      val ker = in(HComplex(rsd_cfg.getKernelConfig))
      val fft = in(HComplex(rsd_cfg.getFUinConfig))
      val rsd_prod = out(HComplex(rsd_cfg.getKernelConfig * rsd_cfg.getFUinConfig))
//      val xout1 = out(HComplex(HComplexConfig(17, -1)))
    }

//    io.xout1 := io.xin1

    io.rsd_prod := Delay(io.ker * io.fft, 1)
  }

//  val cfg = HComplexConfig(38, 26)

  new File("tmp/test_sc").mkdir()
  val report = SpinalConfig(
    targetDirectory = "tmp/test_sc",
    headerWithDate = true
  ).addTransformationPhase(
    new SwapTagPhase(SimPublic, Verilator.public)
  ).generateVerilog(
    TestSC()
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
//      val inputData = Complex(-Math.exp(i*2), Math.exp(i*2-1))
      dut.io.fft #= Complex(0, -64)
      dut.io.ker #= Complex(-0.00978, -0.003433)
//      println(s"input: $inputData")
      dut.clockDomain.waitSampling()
      println(s"xin: ${dut.io.fft.toComplex}")
      println(s"xin1: ${dut.io.ker.toComplex}")
      dut.clockDomain.waitSampling(2)
      println(s"output: ${dut.io.rsd_prod.toComplex}")
    }
    simSuccess()

  }
}
