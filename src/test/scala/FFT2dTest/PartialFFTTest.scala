package FFT2dTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import Sim.SimFix._
import Sim.SimComplex._
import RsdKernelConfig._
import FFT2dTest.FFTMain._

object PartialFFTTest extends App{
  import java.io.File
  import Fpga.FFT2d._

  new File("tmp/PartialFFT").mkdirs()
  val fft_config = rsd_cfg.getFFT2dConfig
  val input_image = load_image("src/test/resource/fft_data/test.jpg")
  write_image(input_image, "tmp/PartialFFT/inimg.jpg")

  val hasWave = false

  val compiled = if (hasWave) {
    SimConfig
      .withWave
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(PartialFFT(fft_config))
  }else{
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(PartialFFT(fft_config))
  }

  compiled.doSim("PartialFFT_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dut.clockDomain.waitSampling()


  }

}
