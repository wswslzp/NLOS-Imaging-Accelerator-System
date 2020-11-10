package LoadUnitTest

import Config._
import Core._
import Sim.RsdGenCoreArray.Driver.RsdDriver
import spinal.core._
import spinal.core.sim._

object WaveLoadUnitTest extends App{
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

  SimConfig
    .withVcdWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .compile(WaveLoadUnit(rsd_cfg, init_addr = init_addr))
    .doSim("WaveLoadUnit_tb") {dut=>
      dut.clockDomain.forkStimulus(2)

      val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")

      val driver = RsdDriver(dut.data_in, dut.clockDomain)

      dut.io.fc_eq_0 #= true
      dut.data_in.aw.valid #= false
      dut.data_in.w.valid #= false
      dut.data_in.b.ready #= false

      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(50000)
      }

      // df = (0, 0)
      if(dut.io.load_req.toBoolean) {
        dut.clockDomain.waitSampling()
        driver.driveDoubleData(wave(::, 1), init_addr, rsd_cfg.wave_cfg.fracw)
//        dut.clockDomain.waitSampling()
//        dut.io.distance_enable #= false
      }
      dut.clockDomain.waitSampling()
      driver.driveData(1, dut.transfer_done_map.head._1.last)

      dut.clockDomain.waitSampling(rsd_cfg.impulse_sample_point)
      forkJoin(
        () => {
          dut.clockDomain.waitSampling()
        },
        () => {
          dut.clockDomain.waitSampling(rsd_cfg.radius_factor)
        }
      )
      dut.clockDomain.waitSampling(30)

      // df = (0, 1)
      dut.io.fc_eq_0 #= false
      dut.clockDomain.waitSampling(30)

      // df = (1, 0)
      dut.io.fc_eq_0 #= true
      if(dut.io.load_req.toBoolean) {
        dut.clockDomain.waitSampling(10)
        dut.clockDomain.waitSampling()
        driver.driveDoubleData(wave(::, 2), init_addr, rsd_cfg.wave_cfg.fracw)
      }
      dut.clockDomain.waitSampling()
      driver.driveData(1, dut.transfer_done_map.head._1.last)

      dut.clockDomain.waitSampling(rsd_cfg.radius_factor)

      // df = (1, 1)
      dut.io.fc_eq_0 #= false
      dut.clockDomain.waitSampling(30)

      simSuccess()

    }

}
