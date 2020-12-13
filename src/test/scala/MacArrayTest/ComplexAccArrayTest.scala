package MacArrayTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core.MacArray._
import Fpga.MacArray._
import Config._
import RsdKernelConfig._
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import SimTest.NlosSystemSimTest.write_image
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import breeze.signal.{fourierTr, iFourierTr}
import java.io._
import scala.sys.process.{Process, ProcessLogger}

object ComplexAccArrayTest extends App{
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
  val uin_fft = uin.map(fourierTr(_))
//  val totalSize = rsd_cfg.kernel_size.product
//  val uin_fft = uin.map(fourierTr(_).map(_ / totalSize))
  val uout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )

  val hard_kernel = DenseMatrix.zeros[Complex](uin_fft.head.rows, uin_fft.head.cols)
  val soft_kernel = rsd_kernel.head.head
  val hard_kernel_f = new File("tmp/ComplexAccArray/hard_kernel.csv")
  val soft_kernel_f = new File("tmp/ComplexAccArray/soft_kernel.csv")
  new File("tmp/ComplexAccArray").mkdir()
  csvwrite(
    soft_kernel_f, soft_kernel.map(_.real)
  )

  val hard_fft_out = DenseMatrix.zeros[Complex](uin_fft.head.rows, uin_fft.head.cols)
  val soft_fft_out = uin_fft.head
  val hard_fft_out_f = new File("tmp/ComplexAccArray/hard_fft_out.csv")
  val soft_fft_out_f = new File("tmp/ComplexAccArray/soft_fft_out.csv")
  new File("tmp/ComplexAccArray").mkdir()
  csvwrite(
    soft_fft_out_f, soft_fft_out.map(_.real)
  )

  val withWave = true
  val compiled = if(withWave){
    SimConfig
      .withWave(2)
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16")
//      .compile(ComplexAccArray(rsd_cfg))
      .compile(RowMacArray(rsd_cfg))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16")
      .compile(RowMacArray(rsd_cfg))
  }

  compiled
    .doSim("ComplexAccArray_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.fc_overflow #= false
      dut.io.fft_out.valid #= false
      dut.io.rsd_kernel.valid #= false
      dut.clockDomain.waitSampling()

      var depth = 0

      forkJoin(

        //Driver
        () => {
          forkJoin(

            // fft out driver
            () => {
              for(d <- rsd_cfg.depthRange){
                dut.sim.dc #= d
                for(f <- rsd_cfg.freqRange){
                  dut.sim.fc #= f
                  dut.io.fc_overflow #= (f == rsd_cfg.freq_factor-1)
                  println(s"Now is ($d, $f).")
                  depth = d
                  if(d == 0) {
                    dut.clockDomain.waitSampling(100)
                  }
                  else{
                    dut.clockDomain.waitSampling(1)
                  }
                  dut.io.fft_out.valid #= true
                  for(y <- 0 until rsd_cfg.kernel_size.last){
                    dut.io.fft_out.payload.zipWithIndex.foreach{ case (complex, i) =>
                      complex #= uin_fft(f)(i, y)
                    }
                    dut.clockDomain.waitSampling()
                  }
                  dut.io.fft_out.valid #= false
                  dut.clockDomain.waitSampling()
                }
              }
              dut.io.fc_overflow #= false
            }
            ,

            // kernel driver
            () => {
              for(d <- rsd_cfg.depthRange){
                for(f <- rsd_cfg.freqRange){
                  if(d == 0) {
                    dut.clockDomain.waitSampling(100)
                  }
                  else{
                    dut.clockDomain.waitSampling()
                  }
                  dut.io.rsd_kernel.valid #= true
                  for(y <- 0 until rsd_cfg.kernel_size.last){
                    dut.io.rsd_kernel.payload.zipWithIndex.foreach{ case (complex, i) =>
                      complex #= rsd_kernel(d)(f)(i, y)
                    }
                    dut.clockDomain.waitSampling()
                  }
                  dut.io.rsd_kernel.valid #= false
                  dut.clockDomain.waitSampling()
                }
              }
              dut.clockDomain.waitSampling(1000)
              simSuccess()
            }
          )
        }
        ,

        // Monitor for fft out
        () => {
          dut.clockDomain.waitActiveEdgeWhere(dut.io.fft_out.valid.toBoolean)
          for(c <- rsd_cfg.colRange){
            for(r <- rsd_cfg.rowRange){
              hard_fft_out(r, c) = dut.io.fft_out.payload(r).toComplex
            }
            dut.clockDomain.waitSampling()
          }
        }
        ,

        // Monitor for rsd kernel
        () => {
          dut.clockDomain.waitActiveEdgeWhere(dut.io.rsd_kernel.valid.toBoolean)
          for(c <- rsd_cfg.colRange){
            for(r <- rsd_cfg.rowRange){
              hard_kernel(r, c) = dut.io.rsd_kernel.payload(r).toComplex
            }
            dut.clockDomain.waitSampling()
          }
        }
        ,

        // Monitor
        () => {
          while(true){
            dut.clockDomain.waitActiveEdgeWhere(dut.io.mac_result.valid.toBoolean)
            for(col <- rsd_cfg.colRange){
              for(row <- rsd_cfg.rowRange){
                uout_f(depth-1)(row, col) = dut.io.mac_result.payload(row).toComplex
              }
              dut.clockDomain.waitSampling()
            }
          }
        }
      )


    }

  csvwrite(
    hard_fft_out_f, hard_fft_out.map(_.real)
  )
  csvwrite(
    hard_kernel_f, hard_kernel.map(_.real)
  )
  csvwrite(
    new File("tmp/ComplexAccArray/hard_uoutf10.csv"),
    uout_f(10).map(_.real)
  )

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
  csvwrite(new File("tmp/hard_uout_abs_max.csv"), uout_abs_max)

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/RowMacArray/nlos_hard_out.jpg")

  val tb_path = "tb/RowMacArray"
  val tmp_path = "tmp/ComplexAccArray"
  val nullLogger = ProcessLogger(line=>{})

  if(withWave){
    Process(s"vcd2vpd ${tb_path}/ComplexAccArray_tb.vcd ${tb_path}/ComplexAccArray_tb.vpd").!(nullLogger)
    Process(s"dve -full64 -vpd ${tb_path}/ComplexAccArray_tb.vpd").!!
  }

}
