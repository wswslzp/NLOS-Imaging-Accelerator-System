package SimTest.DriverTest

import spinal.core._
import sim._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.lib.bus.amba4.axi.Axi4Config

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

  SimConfig
    .withVcdWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .compile(WaveLoadUnit(rsd_cfg, axi_config = axi_cfg, init_addr = init_addr))
    .doSim("RsdDriverTest_tb") {dut=>
      dut.clockDomain.forkStimulus(2)

      val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
    }

}
