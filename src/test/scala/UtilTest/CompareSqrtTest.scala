package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Util.SqrtImpl._
import scala.util.Random._
import scala.math._

object CompareSqrtTest extends App{

  case class CompSqrt() extends Component {
    val din = in UInt(16 bit)
    val dout = out UInt(8 bit)
    val cs = CompareSqrt(8)

    cs.io.din := din |<< 8
    dout := RegNext(cs.io.dout).tag(UQ(8, 4)).fixTo(UQ(8, 0))
  }

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb")
    .compile(CompSqrt())
    .doSim("CompareSqrt_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.din #= 0
      dut.clockDomain.waitSampling()

      for(_ <- 0 until 10){
        val tmp = nextInt(10)*nextInt(10) //<< 8
        println(s"The input is ${tmp}")
        dut.din #= tmp
        dut.clockDomain.waitSampling(2)
        println(s"The hard output is ${dut.dout.toInt} and the true result is ${sqrt(tmp)}")
      }
      simSuccess()

    }
}
