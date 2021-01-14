package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga.NlosFpgaCore
import breeze.linalg._

import scala.sys.process.{Process, ProcessLogger}

object NlosFpgaCoreTest extends App{
  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

  SimConfig
    .allOptimisation
//    .withWave(3)
    .workspacePath("tb")
    .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
    .compile(NlosFpgaCore(rsd_cfg))
    .doSim("NlosFpgaCore_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.hdmi_if.video_mem_read_clk.forkStimulus(2)
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
      uout_pp := catchResult(dut, 2)
      dut.clockDomain.waitSamplingWhere(dut.io.done.toBoolean)
      simSuccess()
    }

  testPostProc(uout_pp, "tb/NlosFpgaCore")

  val nullLogger = ProcessLogger(_ => {})
  println("Converting vcd to vpd...")
  Process("vcd2vpd tb/NlosFpgaCore/NlosFpgaCore_tb.vcd tb/NlosFpgaCore/NlosFpgaCore_tb.vpd") ! nullLogger
  println("Convert done.")
  Process("dve -full64 -vpd tb/NlosFpgaCore/NlosFpgaCore_tb.vpd -session session.NlosFpgaCore_tb.vpd.tcl").run(nullLogger)
}
