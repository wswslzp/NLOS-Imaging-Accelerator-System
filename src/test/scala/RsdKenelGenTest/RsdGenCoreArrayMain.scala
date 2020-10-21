package RsdKenelGenTest

import Config._
import Core._
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.math.Complex
import spinal.core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi.Axi4Config
import Sim.SimComplex._
import Sim.SimFix._

object RsdGenCoreArrayMain extends App{

  import RsdGenCoreArray._
  import Sim.RsdGenCoreArray._
  import RsdKernelConfig._
  var init_addr = 0
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse: DenseMatrix[Complex] = LoadData.loadComplexMatrix(
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
  )
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows
  )
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)
  var dd = 0
  var ff = 0

  SimConfig
    .withWave
    .noOptimisation
    .workspacePath("tb")
    .compile(RsdGenCoreArray(rsd_cfg, init_addr))
    .doSim("RsdGenCoreArray_tb") {dut =>
      import Sim.RsdGenCoreArray.Driver._
      dut.clockDomain.forkStimulus(2)

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
        SimTimeout(9000000)
      }

      forkJoin(
        () => {
          // Driver to send the input data
          for(d <- 0 until rsd_cfg.depth_factor) {
            dd = d
            dut.io.dc_eq_0 #= d == 0
            for(f <- 0 until rsd_cfg.freq_factor) {
              ff = f
              dut.io.fc_eq_0 #= f == 0
              println(s"df = ($d, $f)")
              dut.clockDomain.waitSampling()
              if((dut.io.load_req.toInt & 1) == 1) {
                println("TS")
                println(s"timeshift($f, $d) = ${timeshift(f, d)}")
                rsdDriver.driveComplexData(timeshift(f, d), dut.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
                rsdDriver.driveData(1, dut.loadUnitAddrs(0) + 1)
              }
              dut.clockDomain.waitSampling()
              if((dut.io.load_req.toInt & 2) == 2) {
                println("D")
                rsdDriver.driveDoubleData(distance(f, d), dut.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
                rsdDriver.driveData(1, dut.loadUnitAddrs(1) + 1)
              }
              dut.clockDomain.waitSampling()
              if(f == 0) {
                if((dut.io.load_req.toInt & 4) == 4) {
                  println("Wave")
                  rsdDriver.driveDoubleData(wave(::, d), dut.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
                  rsdDriver.driveData(1, dut.loadUnitAddrs(2) + dut.cfg.radius_factor)
                }
                dut.clockDomain.waitSampling()
              }
              if((d == 0) && (f == 0)) {
                if((dut.io.load_req.toInt & 8) == 8) {
                  println("Imp")
                  rsdDriver.driveComplexData(impulse, dut.loadUnitAddrs(3), dut.cfg.imp_cfg)
                  rsdDriver.driveData(1, dut.loadUnitAddrs(3) + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
                }
                dut.clockDomain.waitSampling()
              }
              if(d == 0) {
                // for d == 0, kernel pushing needs to wait for fft2d output valid.
                // waiting cycle ~ K^2, so we set 100 cycles
                dut.clockDomain.waitSampling(100)
                dut.io.fft2d_out_sync #= true
                dut.clockDomain.waitSampling()
                dut.io.fft2d_out_sync #= false
              } else {
                dut.clockDomain.waitSampling(20)
              }
              dut.clockDomain.waitActiveEdgeWhere(dut.io.push_ending.toBoolean)
              //          dut.clockDomain.waitActiveEdgeWhere(dut.io.cnt_incr.toBoolean)
            }
          }
          dut.clockDomain.waitSampling(10)
          simSuccess()
        }
        ,
        () => {
          // Monitor to catch the input and median results
          forkJoin(
            () => {
              while(true) {
                // catch timeshift
                dut.clockDomain.waitActiveEdgeWhere(dut.timeshift_load_unit.transfer_done_rise.toBoolean)
                println(s"Got Timeshift:\n ${dut.timeshift_load_unit.sim_timeshift.toComplex}")
              }
            }
            ,
            () => {
              while(true) {
                // catch distance
                dut.clockDomain.waitActiveEdgeWhere(dut.distance_load_unit.transfer_done_rise.toBoolean)
                println(s"Got Distance:\n ${dut.distance_load_unit.distance_reg.toDouble}")
              }
            }
            ,
            () => {
              while(true) {
                // catch wave
                dut.clockDomain.waitActiveEdgeWhere(dut.wave_load_unit.transfer_done_rise.toBoolean)
                println(s"Got Wave:\n ${dut.wave_load_unit.wave_regs.map(_.toDouble)}")
              }
            }
            ,
            () => {
              while(true) {
                // catch imp
                dut.clockDomain.waitActiveEdgeWhere(dut.impulse_load_unit.transfer_done_rise.toBoolean)
                val imp = Array.tabulate(dut.impulse_load_unit.int_ram_array.length){idx=>
                  Array.tabulate(dut.cfg.radius_factor){addr=>
                    val xbits = dut.impulse_load_unit.int_ram_array(idx).getBigInt(addr).toLong
                    bitsToComplex(xbits, dut.cfg.imp_cfg)
                  }
                }
                println(s"Got impulse:\n ${imp.head.map(_.toString()).mkString("Array(", ", ", ")")}")
              }
            }
          )
        }
        ,
        () => {
          // Monitor to catch the rsd kernel output
          while(true) {
            dut.clockDomain.waitActiveEdgeWhere(dut.io.rsd_kernel.valid.toBoolean)
            val rsd_kernel = Sim.RsdGenCoreArray.Computation.restoreRSD(
              rsd(dd)(ff), (rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
            )
            for(x <- 0 until rsd_cfg.kernel_size.last) {
              val hout = DenseVector.tabulate(rsd_cfg.kernel_size.head)(dut.io.rsd_kernel.payload(_).toComplex)
              val diff_amp: DenseVector[Double] = (rsd_kernel(::, x) - hout).map(_.abs)
              val diff_amp_max: Double = breeze.linalg.max(diff_amp)
              if (diff_amp_max > 1e-5) {
                println(s"when dd = $dd, ff = $ff, x = $x")
                println(s"The diff_amp_max = $diff_amp_max > 1e-5, simulation failed.\n hout: ${hout(0 to 9).toString()}\n rsd_kernel: ${rsd_kernel(0 to 9, x).toString()}")
                simFailure("**********TEST FAILED!*************")
              }
              dut.clockDomain.waitSampling()
            }
          }
        }

      )

    }

//  SpinalConfig(
//    targetDirectory = "rtl/RsdGenCoreArray",
//    oneFilePerComponent = true
//  ).generateVerilog(
//    RsdGenCoreArray(rsd_cfg, init_addr)(axi_cfg)
//  )
//
}
