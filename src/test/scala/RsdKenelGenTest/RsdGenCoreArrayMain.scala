package RsdKenelGenTest

import Config._
import Core._
import spinal.core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi.Axi4Config

object RsdGenCoreArrayMain extends App{

  import RsdGenCoreArray._
  import Sim.RsdGenCoreArray._
  import RsdKernelConfig._
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = 1,
    radius_factor = 69
  )
  var init_addr = 0

  SimConfig
    .withWave
    .normalOptimisation
    .workspacePath("tb")
    .compile(RsdGenCoreArray(rsd_cfg, init_addr))
    .doSim("RsdGenCoreArray_tb") {dut =>
      import Sim.RsdGenCoreArray.Driver._
      dut.clockDomain.forkStimulus(2)

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

      dut.data_in.aw.valid #= false
      dut.data_in.aw.burst #= 0
      dut.data_in.aw.len #= 0
      dut.data_in.aw.size #= 0
      dut.data_in.aw.addr #= 0
      dut.data_in.w.valid #= false
      dut.data_in.w.data #= 0
      dut.data_in.b.ready #= true
      dut.io.dc_eq_0 #= false
      dut.io.fc_eq_0 #= false
      dut.io.fft2d_out_sync #= false
      val rsdDriver = RsdDriver(dut.data_in, dut.clockDomain)
      dut.clockDomain.waitSampling()

      fork{
        SimTimeout(50000)
      }

      for(d <- 0 until 3) {
        dut.io.dc_eq_0 #= d == 0
        for(f <- 0 until 3) {
          dut.io.fc_eq_0 #= f == 0
          if(dut.io.load_req(0).toBoolean) {
            rsdDriver.driveComplexData(timeshift(0, 0), dut.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
            rsdDriver.driveData(1, dut.loadUnitAddrs(0) + 1)
          }
          dut.clockDomain.waitSampling()
          if(dut.io.load_req(1).toBoolean) {
            rsdDriver.driveDoubleData(distance(0, 0), dut.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
            rsdDriver.driveData(1, dut.loadUnitAddrs(1) + 1)
          }
          dut.clockDomain.waitSampling()
          if(f == 0) {
            if(dut.io.load_req(2).toBoolean) {
              rsdDriver.driveDoubleData(wave(::, 1), dut.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
              rsdDriver.driveData(1, dut.loadUnitAddrs(2) + 1)
            }
            dut.clockDomain.waitSampling()
          }
          if((d == 0) && (f == 0)) {
            if(dut.io.load_req(3).toBoolean) {
              rsdDriver.driveComplexData(impulse, dut.loadUnitAddrs(3), dut.cfg.imp_cfg)
            }
            dut.clockDomain.waitSampling()
          }
          dut.clockDomain.waitSampling(100)
          dut.io.fft2d_out_sync #= true
          dut.clockDomain.waitSampling()
          dut.io.fft2d_out_sync #= false
          dut.clockDomain.waitActiveEdgeWhere(dut.io.push_ending.toBoolean)
        }
      }

      dut.clockDomain.waitSampling(10)
      simSuccess()
    }

//  SpinalConfig(
//    targetDirectory = "rtl/RsdGenCoreArray",
//    oneFilePerComponent = true
//  ).generateVerilog(
//    RsdGenCoreArray(rsd_cfg, init_addr)(axi_cfg)
//  )
//
}
