package UtilTest

import Config.HComplexConfig
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import breeze.linalg._
import breeze.math._
import breeze.stats.distributions._
import Sim.SimFix._

object DTSTest extends App{

  def doubleToSInt(dat: Double, cfg: HComplexConfig): BigInt = {
    val max_v = (1L << (cfg.getDataWidth-1))-1
    val min_v = -(1L << (cfg.getDataWidth-1))

    val sint_v = scala.math.max(scala.math.min(
      scala.math.pow(2, cfg.fracw) * dat, max_v
    ), min_v).toLong

    if (sint_v >= 0) BigInt(sint_v)
    else (BigInt(1) << cfg.getDataWidth) + BigInt(sint_v)
  }

  def complexToSInt(dat: Complex, cfg: HComplexConfig): BigInt = {
    val real_p = doubleToSInt(dat.real, cfg)
    val imag_p = doubleToSInt(dat.imag, cfg)
    if (cfg.real_high){
      real_p << cfg.getDataWidth | imag_p << cfg.getDataWidth
    }else{
      imag_p << cfg.getDataWidth | real_p << cfg.getDataWidth
    }
  }

  case class DTS() extends Component {
    val io = new Bundle {
      val din = in Bits(32 bit)
      val dout = out Bits(32 bit)
    }

    io.dout := RegNext(io.din)
  }

  val nn = Gaussian(100, 1000)
  val v1 = DenseVector.fill(10)(nn.sample())
  println(s"v1 is $v1")

  val cfg = HComplexConfig(16, 0)
  val v1_sint = v1.map(d=> doubleToSInt(d, cfg))
  println(s"v1_sint is $v1_sint")

  SimConfig
    .allOptimisation
    .withWave
    .workspacePath("tb")
    .compile(DTS())
    .doSim("DTS_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.clockDomain.waitSampling()

      for(i <- v1.toArray.indices){
        dut.io.din #= v1_sint(i)
        dut.clockDomain.waitSampling(2)
        println(s"${i}th output ${bitsToDouble(dut.io.dout, cfg.getDataWidth, cfg.fracw)}")
      }
      dut.clockDomain.waitSampling(10)
      simSuccess()
    }

}
