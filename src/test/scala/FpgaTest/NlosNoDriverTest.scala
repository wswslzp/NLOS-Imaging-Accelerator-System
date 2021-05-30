package FpgaTest

import Config.RsdKernelConfig._
import Fpga.{NlosCore, NlosNoDriver}
import Sim.NlosCore.Driver._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import breeze.linalg._
import breeze.math._
import spinal.core.sim._

import java.io.File
import scala.sys.process.{Process, ProcessLogger}
import Config._

object NlosNoDriverTest extends App{


  val withWave = false
  val waveDepth = 1

  val compiled = if (withWave) {
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(NlosNoDriver(rsd_cfg))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(NlosNoDriver(rsd_cfg))
  }

  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

    compiled.doSim("NlosNoDriver_tb") { dut =>
      dut.clockDomain.forkStimulus(2)
      dutInit(dut)
      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(40000000)
      }

      forkJoin(

        // Drive data
        () => driveRsdData(dut),
        () => driveImage(dut),

        // Monitor result
        () => {
          uout_pp := catchResult(dut)
        }
      )
    }
    testPostProc(uout_pp)

  if (withWave) {
    val nullLogger = ProcessLogger(_ => {})
    println("Converting vcd to vpd...")
    Process("vcd2vpd tb/NlosNoDriver/NlosNoDriver_tb.vcd tb/NlosNoDriver/NlosNoDriver_tb.vpd") ! nullLogger
    println("Convert done.")
    Process("dve -full64 -vpd tb/NlosNoDriver/NlosNoDriver_tb.vpd") !!
  }
}
