import Config._
import Sim.RsdGenCoreArray._
import breeze.linalg._
import breeze.math._
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import spinal.core.sim._
import Core.KernelMacArray
import SimTest.NlosSystemSimTest.write_image
import breeze.signal._

import scala.sys.process.Process

object KernelMacArrayMain extends App{
  import RsdKernelConfig._

  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val uin = Array.tabulate(rsd_cfg.freq_factor){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/data/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/data/imag/uin_${idx+1}.csv"
    )
  }
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
  val uin_fft = uin.map(fourierTr(_))
  val uout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )

  new java.io.File("rtl/KernelMacArray").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/KernelMacArray",
    oneFilePerComponent = true
  ).generateVerilog(KernelMacArray(rsd_cfg))

  SimConfig
    .withWave
    .allOptimisation
    .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16")
    .workspacePath("tb")
    .compile(KernelMacArray(rsd_cfg))
    .doSim("KernelMacArray_tb"){dut=>
      import Sim.SimComplex._
      dut.clockDomain.forkStimulus(2)
      dut.io.fft_out.valid #= false
      dut.io.rsd_kernel.valid #= false
      dut.clockDomain.waitSampling()
      var depth = 0

      // Driver
      fork{
        for(d <- 0 until rsd_cfg.depth_factor){
          for(f <- 0 until rsd_cfg.freq_factor){
            depth = d
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
                dut.io.fft_out.valid #= false
                if(d == 0) {
                  dut.clockDomain.waitSampling(100)
                }
                else{
                  dut.clockDomain.waitSampling(2)
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
                dut.io.rsd_kernel.valid #= false
                if(d == 0) {
                  dut.clockDomain.waitSampling(100)
                }
                else{
                  dut.clockDomain.waitSampling(2)
                }
                dut.clockDomain.waitSampling(10)
              }
            )
          }
        }
        simSuccess()
      }

      fork{
        var col = 0
        while(true){
          dut.clockDomain.waitActiveEdgeWhere(dut.io.mac_result.valid.toBoolean)
          for(row <- 0 until rsd_cfg.kernel_size.head){
            // The result(d) comes at (d+1)'s first f cycle.
            // Maybe ???
            uout_f(depth-1)(row, col) = dut.io.mac_result.payload(row).toComplex
          }
          dut.clockDomain.waitSampling()
          col += 1
        }
      }

    }

  val uout = uout_f.map(iFourierTr(_))
  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
    var umax = 0d
    for(d <- 0 until rsd_cfg.depth_factor) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/KernelMacArray/nlos_hard_out.jpg")

  val tb_path = "tb/KernelMacArray"
  Process(s"vcd2vpd ${tb_path}/KernelMacArray_tb.vcd tb/KernelMacArray_tb.vpd").!
  Process(s"vpd2fsdb ${tb_path}/KernelMacArray_tb.vpd -o ${tb_path}/KernelMacArray_tb.fsdb").!
  Process(s"verdi -ssf ${tb_path}/KernelMacArray_tb.fsdb").!!

}
