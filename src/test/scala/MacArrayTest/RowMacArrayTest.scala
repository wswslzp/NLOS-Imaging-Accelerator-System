package MacArrayTest

import Config.RsdKernelConfig._
import Fpga.MacArray.RowMacArray
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import SimTest.NlosSystemSimTest.write_image
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import breeze.signal.{fourierTr, iFourierTr}
import spinal.core.sim._
import Sim.MacArray.Driver._
import Sim.MacArray.Monitor._
import Sim.MacArray.Tester._
import java.io._
import scala.sys.process.Process

object RowMacArrayTest extends App{
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)//(d, f, r)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)//(d, f, R)
  val rsd_kernel = Computation.restoreRSD(rsd, (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last))//(d, f, x, y)
//  val uin_fft = uin.map(fourierTr(_).map(_ / (128*128)))
  val uin_fft = uin.map(fourierTr(_))
//  val totalSize = rsd_cfg.kernel_size.product
//  val uin_fft = uin.map(fourierTr(_).map(_ / totalSize))
  val uout_f = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )

  var hard_kernel = DenseMatrix.zeros[Complex](uin_fft.head.rows, uin_fft.head.cols)
  val soft_kernel = rsd_kernel.head.head
  val hard_kernel_f = new File("tmp/RowMacArray/hard_kernel.csv")
  val soft_kernel_f = new File("tmp/RowMacArray/soft_kernel.csv")
  new File("tmp/RowMacArray").mkdir()
  csvwrite(
    soft_kernel_f, soft_kernel.map(_.real)
  )

  var hard_fft_out = DenseMatrix.zeros[Complex](uin_fft.head.rows, uin_fft.head.cols)
  var soft_fft_out = uin_fft.head
  val hard_fft_out_f = new File("tmp/RowMacArray/hard_fft_out.csv")
  val soft_fft_out_f = new File("tmp/RowMacArray/soft_fft_out.csv")
  new File("tmp/RowMacArray").mkdir()
  csvwrite(
    soft_fft_out_f, soft_fft_out.map(_.real)
  )

  val withWave = true
  val compiled = if(withWave){
    SimConfig
      .withWave(3)
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16")
      .compile(RowMacArray(rsd_cfg))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16")
      .compile(RowMacArray(rsd_cfg))
  }

  compiled
    .doSim("RowMacArray_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dutInit(dut)
      dut.clockDomain.waitSampling()

      var depth = 0

      forkJoin(

        //Driver
        () => {
          forkJoin(
            // fft out driver
            () => driveFUin(dut, uin_fft),
            // kernel driver
            () => driveRSDKernel(dut, rsd_kernel)
          )
        }
        ,

        // Monitor for fft out
        () => hard_fft_out = catchFuin(dut),

        // Monitor for rsd kernel
        () => hard_kernel = catchRSDKernel(dut),

        // Monitor
        () => {
          var dd = 0
          while(true){
            uout_f(dd) = catchMacResult(dut)
            dd += 1
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
    new File("tmp/RowMacArray/hard_uoutf10.csv"),
    uout_f(10).map(_.real)
  )

  testMacResult(uout_f)

  val tb_path = "tb/RowMacArray"
  val tmp_path = "tmp/RowMacArray"
//  val nullLogger = ProcessLogger(line=>{})

  if(withWave){
    Process(s"vcd2vpd ${tb_path}/RowMacArray_tb.vcd ${tb_path}/RowMacArray_tb.vpd").!
    Process(s"dve -full64 -session session.RowMacArray_tb.vpd.tcl").!!
  }

}
