package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga.NlosFpgaSys
import breeze.linalg._

import scala.sys.process.{Process, ProcessLogger}

object NlosFpgaSysTest extends App{
  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

  SimConfig
    .allOptimisation
    .withWave(3)
    .workspacePath("tb")
    .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
    .compile(NlosFpgaSys(rsd_cfg))
    .doSim("NlosFpgaSys_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.sys_init #= false
      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(40000000)
      }

      fork {
        while(true){
          dut.clockDomain.waitSamplingWhere(dut.nlos_driver.io.cnt_incr.toBoolean)
          println(s"now is (${dut.nlos_driver.io.dc.toInt}, ${dut.nlos_driver.io.fc.toInt})")
        }
      }

      dut.io.sys_init #= true
      uout_pp := catchResult(dut, 2, 2)
      dut.clockDomain.waitSamplingWhere(dut.io.done.toBoolean)
      simSuccess()
    }

  testPostProc(uout_pp, "tb/NlosFpgaSys")

  val nullLogger = ProcessLogger(_ => {})
  println("Converting vcd to vpd...")
  Process("vcd2vpd tb/NlosFpgaSys/NlosFpgaSys_tb.vcd tb/NlosFpgaSys/NlosFpgaSys_tb.vpd") ! nullLogger
  println("Convert done.")
  Process("dve -full64 -vpd tb/NlosFpgaSys/NlosFpgaSys_tb.vpd -session session.NlosFpgaSys_tb.vpd.tcl").run(nullLogger)
}
