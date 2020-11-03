import Config._
import Sim.RsdGenCoreArray._
import breeze.linalg._
import breeze.math._
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import spinal.core.sim._
import Core.KernelMacArray
import breeze.signal.fourierTr

object KernelMacArrayMain extends App{

  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv") //(
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse: DenseMatrix[Complex] = LoadData.loadComplexMatrix( //(R, radius)
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
  )
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows
  )
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
  println(rsd_cfg.toString)
  val uin = Array.tabulate(rsd_cfg.freq_factor){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/data/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/data/imag/uin_${idx+1}.csv"
    )
  }
  val uin_fft = uin.map(fourierTr(_))

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb")
    .compile(KernelMacArray(rsd_cfg))
    .doSim("KernelMacArray_tb"){dut=>
      import Sim.SimComplex._
      dut.clockDomain.forkStimulus(2)
      dut.io.fft_out.valid #= false
      dut.io.rsd_kernel.valid #= false
      dut.clockDomain.waitSampling()

      // Driver
      fork{
        for(d <- 0 until rsd_cfg.depth_factor){
          for(f <- 0 until rsd_cfg.freq_factor){
            forkJoin(
              // fft_out driver
              () =>{
                dut.io.fft_out.valid #= true
                for(y <- 0 until rsd_cfg.kernel_size.last){
                  dut.io.fft_out.payload.zipWithIndex.foreach{ case (complex, i) =>
                    complex #= uin_fft(f)(i, y)
                  }
                  dut.clockDomain.waitSampling()
                }
              }
              ,
              // kernel driver
              () => {
                dut.io.rsd_kernel.valid #= true
                for(y <- 0 until rsd_cfg.kernel_size.last){
                  dut.io.rsd_kernel.payload.zipWithIndex.foreach{ case (complex, i) =>
                    complex #= rsd_kernel(d)(f)(i, y)
                  }
                  dut.clockDomain.waitSampling()
                }
              }
            )
          }
        }
      }
    }

}
