import java.io.File

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Fpga._
import breeze.linalg._
import breeze.math.Complex
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Sim.NlosCore.Driver._

import scala.sys.process.{Process, ProcessLogger}

object NlosCoreTest extends App{

  val withWave = false
  val waveDepth = 2

  val compiled = if(withWave){
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(NlosCore(rsd_cfg))
  }else{
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
  val h_fft_out = Array.fill(rsd_cfg.freq_factor){
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  }
  val h_rsdk = Array.fill(rsd_cfg.depth_factor, rsd_cfg.freq_factor){
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  }
  compiled.doSim("NlosCore_tb"){dut=>
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
        while(true){
          uout(uout_d) = catchResult(dut)
          println(s"Get the ${uout_d}th output image.")
          uout_d += 1
        }
      }
      ,

      // Monitor Mac result
      () => {
        var uout_d = 0
        while(true){
          h_mac_result(uout_d) = catchMacResult(dut)
          println(s"Get the ${uout_d}th mac result.")
          uout_d += 1
        }
      }
      ,

      // Monitor for fft out
      () => {
        var hf = 0
        while(true){
          if(dd == 0) {
            val tmp = catchFUin(dut)
            h_fft_out(hf) = tmp
            println(s"Got the ${hf}th fft uin image.")
            hf += 1
          }else{
            dut.clockDomain.waitSampling()
          }
        }
      }
      ,

      // Monitor for rsdk
      () => {
        while(true){
          h_rsdk(dd)(ff) = catchRSDK(dut)
        }
      }
    )
  }

  testRSDK(h_rsdk)
  testFUin(h_fft_out)
  testFUinAndRSDK(h_fft_out, h_rsdk)
  csvwrite(
    new File("tb/NlosCore/hrsdk_10_10.csv"),
    h_rsdk(10)(10).map(_.real)
  )
  csvwrite(
    new File("tb/NlosCore/rsdk_10_10.csv"),
    rsd_kernel(10)(10).map(_.real)
  )

  // TODO: The mac result is huge.
  testMacResult(h_mac_result)
  csvwrite(
    new File("tb/NlosCore/hmac_res10.csv"),
    h_mac_result(10).map(_.real)
  )
  csvwrite(
    new File("tb/NlosCore/hfftout10.csv"),
    h_fft_out(10).map(_.real)
  )

  testFinal(uout)

  if(withWave){
    val nullLogger = ProcessLogger(_=>{})
    println("Converting vcd to vpd...")
    Process("vcd2vpd tb/NlosCore/NlosCore_tb.vcd tb/NlosCore/NlosCore_tb.vpd") ! nullLogger
    println("Convert done.")
    Process("dve -full64 -vpd tb/NlosCore/NlosCore_tb.vpd") !!
  }

}
