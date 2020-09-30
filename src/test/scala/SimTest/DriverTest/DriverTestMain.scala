package SimTest.DriverTest

import spinal.core._
import sim._
import spinal.lib._
import Core._
import Util._
import Config._
import Sim.RsdGenCoreArray.Driver.RsdDriver
import breeze.linalg.DenseMatrix
import spinal.lib.bus.amba4.axi._
import spinal.sim.SimThread

object DriverTestMain extends App{
  import LoadUnit.WaveLoadUnit
  import RsdKernelConfig._
  import Sim.RsdGenCoreArray._
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
  val axi_cfg = Axi4Config(
    addressWidth = 32, dataWidth = 32, idWidth = 4, useId = true, useBurst = true, useCache = false,
    useLast = true, useRegion = false, useLock = false, useLen = true, useSize = true, useQos = false,
    useResp = true, useProt = false, useStrb = false
  )

  def setAddr(bus: Axi4WriteOnly, clock: ClockDomain, address: Int): Array[SimThread] = {
    val tsk = fork {
      bus.aw.valid #= true
      bus.aw.addr #= address
      waitUntil(bus.aw.valid.toBoolean && bus.aw.ready.toBoolean)
      bus.aw.valid #= false
    }//.join()
    Array(tsk)
//    clock.waitSampling()
  }

  SimConfig
    .withVcdWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .compile(WaveLoadUnit(rsd_cfg, init_addr = init_addr))
    .doSim("RsdDriverTest_tb") {dut=>
      dut.clockDomain.forkStimulus(2)

//      val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
      val wave = DenseMatrix.tabulate(4, 10)(_ + _)
      println(s"wave is \n ${wave.toString()}")

      val driver = RsdDriver(dut.data_in, dut.clockDomain)

      dut.io.fc_eq_0 #= true
      dut.io.dc_eq_0 #= true
      dut.io.push_ending #= false
      dut.io.distance_enable #= false
      dut.io.impulse_enable #= false
      dut.data_in.aw.valid #= false
      dut.data_in.w.valid #= false
      dut.data_in.b.ready #= false

      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(50000)
      }

      if(dut.io.load_req.toBoolean) {
        dut.io.distance_enable #= true
        driver.driveData(wave, init_addr)
        dut.clockDomain.waitSampling()
        dut.io.distance_enable #= false
      }

//      dut.clockDomain.onSamplings {
//        if(dut.io.load_req.toBoolean) {
//          dut.io.distance_enable #= true
////          dut.clockDomain.waitSampling()
//          driver.driveDoubleData(wave, init_addr, 8)
//          dut.clockDomain.waitSampling()
//          dut.io.distance_enable #= false
//        }
//      }

      dut.clockDomain.waitSampling(10)

      simSuccess()

    }

}
