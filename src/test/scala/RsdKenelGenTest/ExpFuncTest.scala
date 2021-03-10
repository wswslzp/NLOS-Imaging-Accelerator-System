package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray.ExpFunc
import Util._
import spinal.core._
import java.io.File
import spinal.core.sim._
import Sim.SimFix._
import Sim.SimComplex._
import breeze.linalg._
import breeze.plot._
import breeze.numerics._
import breeze.math._
import org.jfree.chart.axis._

object ExpFuncTest extends App{
  val cfg = HComplexConfig(
    16, 32
  )

//  SpinalConfig(
//    mode = Verilog,
//    targetDirectory = "../fpga/ExpFunc/rtl",
//    headerWithDate = true
//  ).generateVerilog(
//    ExpFunc(cfg, 128, 1)
//  )

  val x = DenseVector.rangeD(-1, 2, 0.001)
  val comp_j = Complex(0, 1)
  val y = x.map {d=>
    exp(2*comp_j*scala.math.Pi * d)
  }
  val f = Figure("exp func comparison")
  val p = f.subplot(0)
  p.legend = true
  p += plot(
    x = x, y = y.map(_.real), name = "original exp function curve"
  )
  val hy = DenseVector.zeros[Double](x.length)

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb")
    .compile(ExpFunc(cfg, 128, 1))
    .doSim("ExpFunc_tb"){dut=>
//      dut.clockDomain.forkStimulus(2)
      dut.io.data_in #= 0
//      dut.clockDomain.waitSampling()
      sleep(10)

      forkJoin(
        ()=>{
          x.foreach{dat=>
            dut.io.data_in #= dat
//            dut.clockDomain.waitSampling()
            sleep(2)
          }
        }
        ,

        ()=>{
          for(i <- 0 until x.length){
            sleep(2)
//            dut.clockDomain.waitSampling()
            hy(i) = dut.io.data_out.toComplex.real
          }
        }
      )

      simSuccess()
    }

  p += plot(
    x = x, y = hy, name = "hardware exp function curve"
  )
  p.xlabel = "x"
  p.ylabel = "y"
  f.saveas("tb/ExpFunc/exp.png")
}
