package RsdKenelGenTest

import Config._
import Core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi.Axi4Config

object RsdGenCoreArrayMain extends App{

  import RsdGenCoreArray._
  import Sim.RsdGenCoreArray._
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(17, -1),
    depth_factor = 1,
    radius_factor = 69
  )
  val axi_cfg: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = 32,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  var init_addr = 0

  SimConfig
    .withWave
    .normalOptimisation
    .workspacePath("tb")
    .compile(RsdGenCoreArray(rsd_cfg, init_addr, axi_cfg))
    .doSim("RsdGenCoreArray_tb") {dut =>
      dut.clockDomain.doStimulus(2)

      val wave = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/wave.csv")
      val distance = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/distance.csv")
      val timeshift = LoadData.loadComplexMatrix(
        "tb/RsdGenCoreArray_tb/data/timeshift_real.csv",
        "tb/RsdGenCoreArray_tb/data/timeshift_imag.csv"
      )
      val impulse = LoadData.loadComplexMatrix(
        "tb/RsdGenCoreArray_tb/data/kernel_rad_real.csv",
        "tb/RsdGenCoreArray_tb/data/kernel_rad_imag.csv"
      )


    }

//  SpinalConfig(
//    targetDirectory = "../NLOS_RTL"
//  ).generateVerilog(
//    RsdGenCoreArray.RsdGenCoreArray(
//      rsd_cfg, init_addr, axi_cfg
//    )
//  )
//
}
