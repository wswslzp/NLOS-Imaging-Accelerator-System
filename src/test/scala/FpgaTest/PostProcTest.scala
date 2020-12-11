package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import RsdKernelConfig._
import Sim.RsdGenCoreArray._
import breeze.linalg._
import breeze.signal._
import breeze.math._
import java.io._
import Fpga._

object PostProcTest extends App{
  // Generating input data
  val coef = Computation.generateCoef(wave, distance, timeshift)
  val rsd = Computation.generateRSDRadKernel(coef, impulse)
  val uin_fft = uin.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- rsd_cfg.freqRange) {
      val rsd_kernel_rad = rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    if(depth == 10){
      csvwrite(
        new File("tmp/soft_uoutf10.csv"),
        uout_f.map(_.real)
      )
    }
    iFourierTr(uout_f)
  }

  val withWave = false
  val waveDepth = 2

  val compiled = if (withWave) {
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(PostProcess(rsd_cfg))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(PostProcess(rsd_cfg))
  }

}
