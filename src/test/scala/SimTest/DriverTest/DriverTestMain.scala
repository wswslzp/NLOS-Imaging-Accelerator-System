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
  import LoadUnit._
  import RsdKernelConfig._
  import Sim.RsdGenCoreArray._
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = 1,
    radius_factor = 68
  )
  var init_addr = 0

  //      val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
  val timeshift = LoadData.loadComplexMatrix(
    real_part_filename = "src/test/resource/data/timeshift_real.csv",
    imag_part_filename = "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse = LoadData.loadComplexMatrix(
    real_part_filename = "src/test/resource/data/impulse_rad_real.csv",
    imag_part_filename = "src/test/resource/data/impulse_rad_imag.csv"
  )

  SimConfig
    .withVcdWave
    .withVerilator
    .allOptimisation
    .workspacePath("tb")
    .addSimulatorFlag("--trace-underscore")
    .compile(ImpLoadUnit(rsd_cfg, init_addr = init_addr))
    .doSim("RsdDriverTest_tb") {dut=>
      import Sim.SimComplex._
      dut.clockDomain.forkStimulus(2)
      println(s"Input impulse = ${impulse(12, ::).t.map(_.real)}")
      println(" ")

      val driver = RsdDriver(dut.data_in, dut.clockDomain)

      dut.io.fc_eq_0 #= true
      dut.io.dc_eq_0 #= true
//      dut.io.push_ending #= false
      dut.io.distance_enable #= false
//      dut.io.impulse_enable #= false
      dut.data_in.aw.valid #= false
      dut.data_in.w.valid #= false
      dut.data_in.b.ready #= false

      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(50000)
      }

      forkJoin(
        () => {
          driver.driveComplexData(impulse, init_addr, rsd_cfg.imp_cfg)
          dut.clockDomain.waitSampling()
          driver.driveData(1, init_addr + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
          dut.clockDomain.waitSampling(10)
          simSuccess()
        }
        ,
        () => {
          while(true) {
            dut.clockDomain.waitActiveEdgeWhere(dut.transfer_done_rise.toBoolean)
            val imp = Array.tabulate(dut.int_ram_array.length){idx=>
              Array.tabulate(dut.cfg.radius_factor){addr=>
                val xbits = dut.int_ram_array(idx).getBigInt(addr).toLong
                bitsToComplex(xbits, dut.cfg.imp_cfg).real
              }
            }
            // TODO:
            //  1 the image part of the input impulse becomes zeros.
            println(s"Got impulse:\n ${imp(12).map(_.toString()).mkString("Array(", ", ", ")")}")
            //            println(s"Sample Timeshift_reg: ${dut..toComplex}")
          }
        }
//        ,
//        () => {
//          while(true) {
//            dut.clockDomain.waitActiveEdgeWhere(dut.data_in.w.valid.toBoolean)
//            println(s"data_in.w = ${dut.data_in.w.payload.data.toLong} (${dut.data_in.w.payload.data.toLong.toBinaryString})")
//          }
//        }
      )

//      if(dut.io.load_req.toBoolean) {
//        dut.io.distance_enable #= true
//        driver.driveDoubleData(timeshift(0, 0), init_addr, rsd_cfg.wave_cfg.fracw)
//        dut.clockDomain.waitSampling()
//        dut.io.distance_enable #= false
//      }

//      dut.clockDomain.waitSampling(10)
//
//      simSuccess()

    }

}
