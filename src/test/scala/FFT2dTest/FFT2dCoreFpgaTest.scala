package FFT2dTest

import java.io.File

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.FFT2dCore
import Config._
import RsdKernelConfig._
import Sim.SimComplex._
import Sim.RsdGenCoreArray.Computation
import SimTest.NlosSystemSimTest.write_image
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import breeze.signal.{fourierTr, iFourierTr}

object FFT2dCoreFpgaTest extends App{
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
  val uin_fft = uin.map(fourierTr(_))
  val uout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )

  val withWave = true
  val waveDepth = 1
  val compiled = if(withWave){
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(FFT2dCore(rsd_cfg.getFFT2dConfig, rsd_cfg.freq_factor, rsd_cfg.depth_factor))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(FFT2dCore(rsd_cfg.getFFT2dConfig, rsd_cfg.freq_factor, rsd_cfg.depth_factor))
  }

  val huin_fft = Array.fill(rsd_cfg.freq_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val huout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val huout_d = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )

  var depth = 0
  var freq = 0
  compiled.doSim("FFT2dCore_FPGA_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.io.push_ending #= false
    dut.io.data_in.valid #= false
    dut.io.data_from_mac.valid #= false
    dut.clockDomain.waitSampling()

    forkJoin(
      // Driver
      () => {

        for(d <- rsd_cfg.depthRange){
          depth = d
          dut.io.dc #= d
          if(d == 5) simSuccess()

          //Driver
          // For d == 0, pipe in `uin`
          if (d == 0) {
            for(f <- rsd_cfg.freqRange){
              println(s"Now is ($d, $f)")
              freq = f
              dut.io.fc #= f
              dut.io.data_in.valid #= true
              for(x <- rsd_cfg.rowRange){
                for(y <- rsd_cfg.colRange){
                  dut.io.data_in.payload #= uin(f)(x, y)
                  dut.clockDomain.waitSampling()
                }
              }
              dut.io.data_in.valid #= false
              dut.clockDomain.waitActiveEdgeWhere(dut.io.fft2d_out_sync.toBoolean)
              dut.clockDomain.waitSampling(rsd_cfg.kernel_size.head - 2)
              dut.io.push_ending #= true
              dut.clockDomain.waitSampling()
              dut.io.push_ending #= false
            }
          }

          // For d > 0, reuse the `uin_fft`
          else {
            for(f <- rsd_cfg.freqRange){
              println(s"Now is ($d, $f)")
              freq = f
              dut.io.fc #= f
              dut.clockDomain.waitSampling()
              if(f == 0){
                dut.io.data_from_mac.valid #= true
                for(c <- rsd_cfg.colRange){
                  for(r <- rsd_cfg.rowRange){
                    dut.io.data_from_mac.payload(r) #= huout_f(depth-1)(r, c)
                  }
                  dut.clockDomain.waitSampling()
                }
                dut.io.data_from_mac.valid #= false
              }else{
                for(c <- rsd_cfg.colRange){
                  dut.clockDomain.waitSampling()
                }
              }
              dut.clockDomain.waitSampling()
            }
          }

        }

        // After all depth gone
        println("Final pipe in")
        dut.clockDomain.waitSampling()
        dut.io.data_from_mac.valid #= true
        for(c <- rsd_cfg.colRange){
          for(r <- rsd_cfg.rowRange){
            dut.io.data_from_mac.payload(r) #= huout_f(depth)(r, c)
          }
          dut.clockDomain.waitSampling()
        }
        dut.io.data_from_mac.valid #= false
        dut.clockDomain.waitSampling()

        // All done
        dut.clockDomain.waitSampling(100)
        simSuccess()

      }
      ,

      // Monitor to catch `uin_fft`
      () => {
        while(true){
          if(depth == 0){
            dut.clockDomain.waitActiveEdgeWhere(dut.io.data_to_rgca.valid.toBoolean)
            for(c <- rsd_cfg.colRange){
              for(r <- rsd_cfg.rowRange){
                huin_fft(freq)(r, c) = dut.io.data_to_rgca.payload(r).toComplex
              }
              dut.clockDomain.waitSampling()
            }
            // Multiply `huin_fft` with rsd kernel after collecting uin fft
            // Model the MacArray
            if(freq == rsd_cfg.freq_factor-1){
              for(d <- rsd_cfg.depthRange){
                for(f <- rsd_cfg.freqRange){
                  huout_f(d) += rsd_kernel(d)(f) *:* huin_fft(f)
                }
              }
            }
          }else{
            dut.clockDomain.waitSampling()
          }
        }
      }
      ,

      // Monitor to catch `uout_d`
      () => {
        var uout_depth = 0
        while(true){
          dut.clockDomain.waitActiveEdgeWhere(dut.io.data_to_final.valid.toBoolean)
          println(s"current uout_depth = $uout_depth")
          for(r <- rsd_cfg.rowRange){
            for(c <- rsd_cfg.colRange){
              huout_d(uout_depth)(r, c) = dut.io.data_to_final.payload(c).toComplex
            }
            dut.clockDomain.waitSampling()
          }
          uout_depth += 1
        }
      }


    )

  }

  val uout_abs = huout_d.map(_.map(_.abs))
  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
    var umax = 0d
    for(d <- rsd_cfg.depthRange) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }
//  csvwrite(new File("tmp/soft_uout_abs_max.csv"), uout_abs_max)
  println("Output image has been generated!")
  println(s"output size: cols = ${uout_abs_max.cols}")

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/FFT2dCore/nlos_hard_out.jpg")


}
