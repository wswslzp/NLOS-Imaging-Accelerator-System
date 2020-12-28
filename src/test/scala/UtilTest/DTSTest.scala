package UtilTest

import Config.HComplexConfig
import Config.RsdKernelConfig._
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import breeze.linalg._
import breeze.math._
import breeze.stats.distributions._
import Sim.SimFix._
import Sim.SimComplex._

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
      ( real_p << cfg.getDataWidth ) | imag_p
    }else{
      ( imag_p << cfg.getDataWidth ) | real_p
    }
  }

  case class DTS() extends Component {
    val io = new Bundle {
      val din = in Bits(32 bit)
//      val din_r = in Bits(16 bit)
//      val din_i = in Bits(16 bit)
      val dout = out Bits(32 bit)
//      val dout_r = out Bits(16 bit)
//      val dout_i = out Bits(16 bit)
    }

//    io.dout_r := io.din_r
//    io.dout_i := io.din_i
    io.dout := RegNext(io.din)
  }

  val nn = Gaussian(0, 1)
  val v1 = DenseVector.fill(10)(
    Complex(nn.sample(), nn.sample())
  )
//  println(s"v1 is $v1")

  val cfg = HComplexConfig(8, 8)
//  val v1r_sint = v1.map{d=> doubleToSInt(d.real, cfg)}
//  val v1i_sint = v1.map{d=> doubleToSInt(d.imag, cfg)}
//  println(s"v1r_sint is $v1r_sint")
//  println(s"v1i_sint is $v1i_sint")
  val v1_sint = v1.map(d=> complexToSInt(d, cfg))
//  println(s"v1_sint is $v1_sint")

  val twv = DenseVector.tabulate(rsd_cfg.radius_factor * rsd_cfg.depth_factor){idx=>
    val r = idx / rsd_cfg.depth_factor
    val d = idx % rsd_cfg.depth_factor
    wave(r, d)
  }
//  println(s"wave is ${wave(0 to 10, 0 to 10)}")
//  println(s"twv is ${twv(0 to 10)}")

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
        println(s"${i}th output ${bitsToComplex(dut.io.dout.toLong, cfg)}")
      }
      dut.clockDomain.waitSampling(10)
      simSuccess()
    }

}
