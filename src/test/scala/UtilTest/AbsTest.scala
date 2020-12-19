package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Util._
import Config._
import breeze.math._
import breeze.linalg._
import breeze.stats.distributions._
import Sim.SimComplex._
import Sim.SimFix._
import scala.collection._

object AbsTest extends App{
  case class Abs(cfg: HComplexConfig) extends Component {
    val io = new Bundle {
      val din = in(HComplex(cfg))
      val dout = out UFix(cfg.intw exp, -cfg.fracw exp)
    }

    io.dout := RegNext(io.din.abs) init UF(0, cfg.intw exp, -cfg.fracw exp)
  }

  val nn = Gaussian(0, 1)
  val test_vec = DenseVector.tabulate(4){i=>
    Complex(nn.sample(), nn.sample())
  }
  println(s"test vector is $test_vec")
  val true_res = test_vec.map(_.abs)
  println(s"true result is $true_res")
  val hard_res = mutable.Queue[Double]()

  SimConfig
    .allOptimisation
    .withWave
    .workspacePath("tb")
    .compile(Abs(HComplexConfig(8, 8)))
    .doSim("Abs_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.clockDomain.waitSampling()

      test_vec.foreach {dat=>
        dut.io.din #= dat
        dut.clockDomain.waitSampling(2)
        hard_res.enqueue(dut.io.dout.toDouble)
      }
      dut.clockDomain.waitSampling()
      simSuccess()
    }

  println(hard_res)
}
