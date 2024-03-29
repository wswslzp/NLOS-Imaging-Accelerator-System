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
import scala.sys.process._
import Sim.FFT2dCore.Driver._
import Sim.FFT2dCore.Monitor._

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
      .compile(FFT2dCore(rsd_cfg, rsd_cfg.freq_factor, rsd_cfg.depth_factor))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(FFT2dCore(rsd_cfg, rsd_cfg.freq_factor, rsd_cfg.depth_factor))
  }

  val huin = Array.fill(rsd_cfg.freq_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val huin_fft = Array.fill(rsd_cfg.freq_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val huout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val huout_d = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val hdata_from_mac = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )

  compiled.doSim("FFT2dCore_FPGA_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dutInit(dut)
    dut.clockDomain.waitSampling()

    forkJoin(
      // Driver
      () => driveData(dut, huout_f),

      // Monitor for data from mac
      () => {
        var d = 0
        while(true){
          hdata_from_mac(d) = catchMacResult(dut)
          println(s"Got ${d}th mac result")
          d += 1
        }
      }
      ,

      // Monitor for data in
      () => {
        while(true){
          dut.clockDomain.waitActiveEdgeWhere(dut.io.data_in.valid.toBoolean)
          for(x <- rsd_cfg.rowRange){
            for(y <- rsd_cfg.colRange){
              huin(freq)(x, y)  = dut.io.data_in.payload.toComplex
              dut.clockDomain.waitSampling()
            }
          }
          println(s"current freq is $freq")
        }
      }
      ,

      // Monitor to catch `uin_fft`
      () => {
        while(true){
          if(depth == 0){
            dut.clockDomain.waitActiveEdgeWhere(dut.io.data_to_mac.valid.toBoolean)
            for(c <- rsd_cfg.colRange){
              for(r <- rsd_cfg.rowRange){
                huin_fft(freq)(r, c) = dut.io.data_to_mac.payload(r).toComplex
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
  // Test `huout_f`, which is kernel * uin_fft and accumulated.
  // Now we have huout_f correct
  val huout = huout_f.map(iFourierTr(_))
  val huout_abs = huout.map(_.map(_.abs))
  val huout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
    var umax = 0d
    for(d <- rsd_cfg.depthRange) {
      if (huout_abs(d)(x, y) > umax) {
        umax = huout_abs(d)(x, y)
      }
    }
    umax
  }
  val huout_abs_max_flip = fliplr(huout_abs_max)
  write_image(huout_abs_max_flip, "tb/FFT2dCore/nlos_test_houtf_out.jpg")

  // Test `hdata_from_mac`
  csvwrite(
    new File("tb/FFT2dCore/huout_f10.csv"),
    huout_f(10).map(_.real)
  )
  csvwrite(
    new File("tb/FFT2dCore/hdata_from_mac10.csv"),
    hdata_from_mac(10).map(_.real)
  )
  csvwrite(
    new File("tb/FFT2dCore/huin_fft10.csv"),
    huin_fft(10).map(_.real)
  )
  csvwrite(
    new File("tb/FFT2dCore/huin10.csv"),
    huin(10).map(_.real)
  )


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
  write_image(uout_abs_max_flip, "tb/FFT2dCore/nlos_test_huoutd_out.jpg")

  if(withWave){
    Process("vcd2vpd tb/FFT2dCore/FFT2dCore_FPGA_tb.vcd tb/FFT2dCore/FFT2dCore_FPGA_tb.vpd").!
    Process("dve -full64 -vpd tb/FFT2dCore/FFT2dCore_FPGA_tb.vpd").!!
  }

}
