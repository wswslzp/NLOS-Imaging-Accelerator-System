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

  val withWave = true
  val waveDepth = 1

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
        while(true){
          var hf = 0
          if(dd == 0) {
            val tmp = catchFUin(dut)
            h_fft_out(hf) = tmp
            hf += 1
            println(s"Got the ${hf}th fft uin image.")
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

//  testRSDK(h_rsdk)
//  testFUin(h_fft_out)

  // TODO: Noticed that when (1, 0), fft to mac valid didn't assert
  //  as rsd kernel valid do.
  testMacResult(h_mac_result)
  csvwrite(
    new File("tb/NlosCore/hmac_res10.csv"),
    h_mac_result(10).map(_.real)
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
