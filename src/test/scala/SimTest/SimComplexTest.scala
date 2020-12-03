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
    }

    val xin2 = io.xin * io.xin
    val xin2_p_xin = io.xin + xin2
    xin2_p_xin.simPublic()
    val xin2_p_xin_10: Vec[HComplex] = History(xin2_p_xin, 256)
    val vec_hcomp_10 = Vec(Reg(HComplex(xin2_p_xin.config)), 256) simPublic()
    for(i <- vec_hcomp_10.indices){
      vec_hcomp_10(i) := xin2_p_xin_10(i)
    }

    io.xout := RegNext(vec_hcomp_10(3).conj)
  }

  val cfg = HComplexConfig(16, -8)

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
      val inputData = Complex(Math.exp(i*2), Math.exp(i*2-1))
      dut.io.xin #= inputData
      println(s"input: $inputData")
      dut.clockDomain.waitSampling()
      println(s"dat2input: ${dut.io.xin.toComplex}")
      dut.clockDomain.waitSampling(2)
      for(i <- 0 until 10){
        println(s"inside[$i] = ${dut.vec_hcomp_10(i).toComplex}")
      }
      println(s"output: ${dut.io.xout.toComplex}")
    }
    simSuccess()

  }
}
