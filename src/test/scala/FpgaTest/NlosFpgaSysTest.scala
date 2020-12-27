package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga.NlosFpgaSys
import breeze.linalg._

object NlosFpgaSysTest extends App{
  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

  SimConfig
    .allOptimisation
    .withWave(1)
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

      dut.io.sys_init #= true
      uout_pp := catchResult(dut, 2, 2)
      dut.clockDomain.waitSamplingWhere(dut.io.done.toBoolean)
      simSuccess()
    }
}
