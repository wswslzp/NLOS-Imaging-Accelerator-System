package FpgaTest

import Config.RsdKernelConfig._
import Fpga.NlosCore
import Sim.NlosCore.Driver._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Sim.SimComplex._
import Sim.SimFix._
import breeze.linalg._
import breeze.math._
import spinal.core.sim._

import java.io.File
import scala.sys.process.{Process, ProcessLogger}

object NlosCoreTest extends App {

  val withWave = true
  val waveDepth = 3

  val compiled = if (withWave) {
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(NlosCore(rsd_cfg))
  } else {
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(NlosCore(rsd_cfg))
  }

  val uout = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val h_mac_result = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val h_rsd_fft_prod = Array.fill(rsd_cfg.depth_factor, rsd_cfg.freq_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  val h_fft_out = Array.fill(rsd_cfg.freq_factor) {
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  }
  val h_rsdk = Array.fill(rsd_cfg.depth_factor, rsd_cfg.freq_factor) {
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  }
  val h_rsdk_real_bin = Array.fill(rsd_cfg.freq_factor) {
    DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  }
  val h_rsdk_rad = Array.fill(rsd_cfg.depth_factor, rsd_cfg.freq_factor) {
    DenseVector.zeros[Complex](rsd_cfg.impulse_sample_point)
  }
  val h_prod_bin_10 = Array.fill(rsd_cfg.freq_factor)(DenseMatrix.zeros[Double](rsd_cfg.rows, rsd_cfg.cols))
  val h_prod_fix_10 = Array.fill(rsd_cfg.freq_factor)(DenseMatrix.zeros[Double](rsd_cfg.rows, rsd_cfg.cols))
  compiled.doSim("NlosCore_tb") { dut =>
    dut.clockDomain.forkStimulus(2)
    dutInit(dut)
    dut.clockDomain.waitSampling()

    fork {
      SimTimeout(40000000)
    }

    forkJoin(

      // Drive data
      () => driveRsdData(dut),
      () => driveImage(dut),

      // Monitor result
      () => {
        var uout_d = 0
        while (true) {
          uout(uout_d) = catchResult(dut)
//          println(s"Get the ${uout_d}th output image.")
          uout_d += 1
        }
      }
      ,

      // Monitor Mac result
      () => {
        var uout_d = 0
        while (true) {
          h_mac_result(uout_d) = catchMacResult(dut)
//          println(s"Get the ${uout_d}th mac result.")
          uout_d += 1
        }
      }
      ,

      // Monitor for fft out
      () => {
        var hf = 0
        while (true) {
          if (dd == 0 && hf < rsd_cfg.freq_factor) {
            val tmp = catchFUin(dut)
            h_fft_out(hf) = tmp
//            println(s"Got the ${hf}th fft uin image.")
            hf += 1
          } else {
            dut.clockDomain.waitSampling()
          }
        }
      }
      ,

      // Monitor for rsdk
      () => {
        while (true) {
          h_rsdk(dd)(ff) = catchRSDK(dut)
        }
      }
      ,

      // Monitor for rsdk binary
      () => {
        while (true) {
          dut.clockDomain.waitActiveEdgeWhere(dut.rgca.io.rsd_kernel.valid.toBoolean && (dut.io.dc.toInt == 10))
          for(c <- rsd_cfg.colRange){
            for(r <- rsd_cfg.rowRange){
              h_rsdk_real_bin(ff)(r, c) = dut.rgca.io.rsd_kernel.payload(r).real.raw.toInt.toDouble
            }
            dut.clockDomain.waitSampling()
          }
        }
      }
      ,

      // Catch rsd_fft_prod
      // todo: the monitor maybe wrong??
      () => {
        var cur_d = 0
        var cur_f = 0
        while(true){
          fork{
            dut.clockDomain.waitActiveEdgeWhere(dut.mac_array.rsd_fft_prod_valid.toBoolean)
            cur_d = dd
            cur_f = ff
          }
          h_rsd_fft_prod(cur_d)(cur_f) = catchRsdFuinProd(dut)
          println(s"current prod catch cycle is ($cur_d, $cur_f)")
        }
      }
      ,

      () => {
        var curf = 0
        while(true){
          dut.clockDomain.waitActiveEdgeWhere(dut.mac_array.rsd_fft_prod_valid.toBoolean && ( dut.io.dc.toInt == 10 ))
          curf = ff
          for(c <- rsd_cfg.colRange){
            for(r <- rsd_cfg.rowRange){
              h_prod_bin_10(curf)(r, c) = dut.mac_array.rsd_fft_prod(r).real.raw.toInt.toDouble
              h_prod_fix_10(curf)(r, c) = dut.mac_array.rsd_fft_prod(r).toComplex.real
            }
            dut.clockDomain.waitSampling()
          }
          println(s"comp prod catch cycle is (10, $curf)")
        }
      }
      ,

      // catch rsd kernel rad
      () => {
        while(true) {
          dut.clockDomain.waitActiveEdgeWhere(dut.rgca.io.rsd_kernel.valid.toBoolean)
          for(i <- rsd_cfg.rLengthRange) {
            h_rsdk_rad(dd)(ff)(i) = dut.rgca.rsd_mem(i).toComplex
          }
          dut.clockDomain.waitActiveEdgeWhere(!dut.rgca.io.rsd_kernel.valid.toBoolean)
        }
      }
    )
  }

  for(f <- rsd_cfg.freqRange){
    csvwrite(
      new File(s"tmp/macres/rsdk_d10_f$f.csv"), h_rsdk_real_bin(f)
    )
  }
  new File("tmp/macres/prod_bin_fix_comp").mkdir()
  for(f <- rsd_cfg.freqRange){
    csvwrite(
      new File(s"tmp/macres/prod_bin_fix_comp/d10_bin_f$f.csv"), h_prod_bin_10(f)
    )
  }
  for(f <- rsd_cfg.freqRange){
    csvwrite(
      new File(s"tmp/macres/prod_bin_fix_comp/d10_fix_f$f.csv"), h_prod_fix_10(f)
    )
  }

  println("testing h_rsdk")
  testRSDK(h_rsdk)
  println("testing h_fft_out")
  testFUin(h_fft_out)
  println("testing h_fft_out and h_rsdk")
  testFUinAndRSDK(h_fft_out, h_rsdk)
  println("testing h_rsd_fft_prod")
  testRsdFuinProd(h_rsd_fft_prod)

  new File("tmp/macres/hard_prod").mkdir()
  for(f <- rsd_cfg.freqRange){
    csvwrite(new File(s"tmp/macres/hard_prod/h_prod_f${f}_real.csv"), h_rsd_fft_prod(10)(f).map(_.real))
    csvwrite(new File(s"tmp/macres/hard_prod/h_prod_f${f}_imag.csv"), h_rsd_fft_prod(10)(f).map(_.imag))
  }

  val hrsdrad_10 = DenseMatrix.zeros[Double](rsd_cfg.freq_factor, rsd_cfg.impulse_sample_point)
  val rsdrad_10 = DenseMatrix.zeros[Double](rsd_cfg.freq_factor, rsd_cfg.impulse_sample_point)
  for(f <- rsd_cfg.freqRange){
    hrsdrad_10(f, ::) := h_rsdk_rad(10)(f).map(_.real).t
    rsdrad_10(f, ::) := rsd(10)(f).map(_.real).t
  }
  csvwrite(new File("tb/NlosCore/hrsdrad10.csv"), hrsdrad_10)
  csvwrite(new File("tb/NlosCore/rsdrad10.csv"), rsdrad_10)
  println("testing mac result")
  testMacResult(h_mac_result)

  println("testing uout")
  testFinal(uout)
  new File("src/test/resource/uout").mkdir()
  for(i <- uout.indices){
    csvwrite(
      new File(s"src/test/resource/uout/uout_${i}_real.csv"),
      uout(i).map(_.real)
    )
    csvwrite(
      new File(s"src/test/resource/uout/uout_${i}_imag.csv"),
      uout(i).map(_.imag)
    )
  }


  if (withWave) {
    val nullLogger = ProcessLogger(_ => {})
    println("Converting vcd to vpd...")
    Process("vcd2vpd tb/NlosCore/NlosCore_tb.vcd tb/NlosCore/NlosCore_tb.vpd") ! nullLogger
    println("Convert done.")
    Process("rm tb/NlosCore/NlosCore_tb.vcd").!
    Process("dve -full64 -session session.NlosCore_tb.vpd.tcl") !!
  }

}
