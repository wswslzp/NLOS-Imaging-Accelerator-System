package RsdKenelGenTest

import scala.sys.process._
import Config._
import Core._
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import spinal.core._
import spinal.core.sim._
import Sim.SimComplex._
import Sim.SimFix._
import SimTest.NlosSystemSimTest.{kernel_size, write_image}
import breeze.signal._
import java.io._

object RsdGenCoreArrayMain extends App{

  import RsdGenCoreArray._
  import Sim.RsdGenCoreArray._
  import RsdKernelConfig._
  var init_addr = 0
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)
  var dd = 0
  var ff = 0

  val uout = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )
  val uin_fft = uin.map(fourierTr(_))
  val hard_rsd_kernel = DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))

  val h_wave = DenseMatrix.fill(wave.rows, wave.cols)(0d)
  val h_distance = DenseMatrix.fill(distance.rows, distance.cols)(0d)
  val h_timeshift = DenseMatrix.fill(timeshift.rows, timeshift.cols)(Complex(0d, 0d))
  val h_impulse = DenseMatrix.fill(impulse.rows, impulse.cols)(0d)

  new File("rtl/RsdGenCoreArray").mkdir()
  val report = SpinalConfig(
//    oneFilePerComponent = true,
    targetDirectory = "rtl/RsdGenCoreArray",
    headerWithDate = true
  ).addTransformationPhase(
    new SwapTagPhase(SimPublic, Verilator.public)
  ).generateVerilog(
    RsdGenCoreArray(rsd_cfg, init_addr)
  )

  val withWave = false
  val module_compiled = if(withWave) {
    SimConfig.withWave(1).allOptimisation.workspacePath("tb").workspaceName("RsdGenCoreArray")
      .addSimulatorFlag("-j 16 --threads 16 --trace-threads 16").compile(report)
  } else {
    SimConfig.allOptimisation.workspacePath("tb").workspaceName("RsdGenCoreArray")
      .addSimulatorFlag("-j 16 --threads 16 ").compile(report)
  }

  module_compiled.doSim("RsdGenCoreArray_tb") {dut =>
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
    dut.io.fft2d_out_sync #= false
    dut.io.dc #= 0
    dut.io.fc #= 0
    val rsdDriver = RsdDriver(dut.data_in, dut.clockDomain)
    dut.clockDomain.waitSampling()

    fork{
      SimTimeout(40000000)
    }

    forkJoin(
      () => {
        // Driver to send the input data
        for(d <- 0 until rsd_cfg.depth_factor) {
          dd = d
          dut.io.dc #= d
          for(f <- 0 until rsd_cfg.freq_factor) {
            ff = f
            dut.io.fc #= f
            dut.clockDomain.waitSampling()
            if((dut.io.load_req.toInt & 1) == 1) {
              rsdDriver.driveComplexData(timeshift(f, d), dut.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
              rsdDriver.driveData(1, dut.loadUnitAddrs(0) + 1)
            }
            dut.clockDomain.waitSampling()
            if((dut.io.load_req.toInt & 2) == 2) {
              rsdDriver.driveDoubleData(distance(f, d), dut.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
              rsdDriver.driveData(1, dut.loadUnitAddrs(1) + 1)
            }
            dut.clockDomain.waitSampling()
            if((dut.io.load_req.toInt & 4) == 4) {
              rsdDriver.driveDoubleData(wave(::, d), dut.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
              rsdDriver.driveData(1, dut.loadUnitAddrs(2) + dut.cfg.radius_factor)
            }
            dut.clockDomain.waitSampling()
            if((d == 0) && (f == 0)) {
              if((dut.io.load_req.toInt & 8) == 8) {
                rsdDriver.driveDoubleData(impulse, dut.loadUnitAddrs(3), rsd_cfg.imp_cfg.fracw)
                rsdDriver.driveData(1, dut.loadUnitAddrs(3) + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
              }
              dut.clockDomain.waitSampling()
            }
            fork{
              // TODO: DF logic needs to modify
              if(d == 0) {
                // for d == 0, kernel pushing needs to wait for fft2d output valid.
                // waiting cycle ~ K^2, so we set 100 cycles
                dut.clockDomain.waitSampling(2000) // simulate the image loading
                dut.io.fft2d_out_sync #= true
                dut.clockDomain.waitSampling()
                dut.io.fft2d_out_sync #= false
                dut.clockDomain.waitSampling(120) // simulate the fft
              } else {
                dut.clockDomain.waitSampling(10)
              }
            }
            // TODO: master will change
            dut.clockDomain.waitActiveEdgeWhere(dut.io.cnt_incr.toBoolean)
          }
        }
        dut.clockDomain.waitSampling(10)
        simSuccess()
      }
      ,
      () => {
        // Monitor to catch the data pipe into the memory.
        // Here we try to restore the data store in src/test/resource,
        // but catch them from hardware
        fork{
          // Monitor for time shift
          while(true){
            dut.clockDomain.waitActiveEdgeWhere(dut.timeshift_load_unit.io.timeshift.valid.toBoolean)
            h_timeshift(ff, dd) = dut.timeshift_load_unit.io.timeshift.payload.toComplex
          }
        }
        fork{
          // Monitor for distance
          while(true){
            dut.clockDomain.waitActiveEdgeWhere(dut.distance_load_unit.io.distance.valid.toBoolean)
            h_distance(ff, dd) = dut.distance_load_unit.io.distance.payload.toDouble
          }
        }
        fork{
          // Monitor for wave
          while(true){
            dut.clockDomain.waitActiveEdgeWhere(dut.wave_load_unit.io.wave.valid.toBoolean)
            if(ff == 0){
              for(r <- 0 until rsd_cfg.radius_factor){
                h_wave(r, dd) = dut.wave_load_unit.io.wave.payload.toDouble
                dut.clockDomain.waitSampling()
              }
            }
          }
        }
        fork{
          // Monitor for impulse
            while(true){
              dut.clockDomain.waitActiveEdgeWhere(dut.impulse_load_unit.io.impulse_out.valid.toBoolean)
              if(dd == 0 && ff == 0){
                for(r <- rsd_cfg.radiusRange){
                  for(len <- rsd_cfg.rLengthRange){
                    h_impulse(len, r) = dut.impulse_load_unit.io.impulse_out.payload(len).toDouble
                  }
                  dut.clockDomain.waitSampling()
                }
              }
            }
        }
      }
      ,
      () => {
        // Monitor to catch the rsd kernel output
        // TODO: uout didn't conform to RsdGenCore.
        //  Now we know that the rsd mem store wrong rsd kernel rad.
        //  Problems may be
        //  1. cur_d and cur_f may wrong: Print the cur_d and cur_f every time? No
        //  2. Wrong impulse/coef/timeshift/distance/wave loads in ??
        //  3. Address map output wrong pixel address: Get the rsd kernel rad output and compare with the RsdGenCore output
        //  4.
        new File("tb/RsdGenCoreArray/uout").mkdir()
        new File("tb/RsdGenCoreArray_rad").mkdir()
        val rsd_rad = DenseMatrix.fill(rsd_cfg.freq_factor, rsd_cfg.impulse_sample_point)(Complex(0, 0))
        while(true) {
          val cur_d = dd
          val cur_f = ff
          dut.clockDomain.waitActiveEdgeWhere(dut.io.rsd_kernel.valid.toBoolean)

          // get the rsd kernel rad from internal memory
          for(r <- 0 until rsd_cfg.impulse_sample_point){
            rsd_rad(cur_f, r) = dut.rsd_mem(r).toComplex
          }

          // Get the restored rsd hardware kernel
          for(y <- 0 until rsd_cfg.kernel_size.last) {
            for(x <- 0 until rsd_cfg.kernel_size.head) {
              hard_rsd_kernel(x, y) = dut.io.rsd_kernel.payload(x).toComplex
            }
            dut.clockDomain.waitSampling()
          }
          println(s"Now is ($dd, $ff) and cur_d is $cur_d, cur_f is $cur_f")
          uout(cur_d) += hard_rsd_kernel *:* uin_fft(cur_f)
          if(cur_f == rsd_cfg.freq_factor-1){
            uout(cur_d) = iFourierTr(uout(cur_d))
//              csvwrite(
//                new File(s"tb/RsdGenCoreArray/uout/uout_d${cur_d}_real.csv"),
//                uout(cur_d).map(_.abs)
//              )
            csvwrite(
              new File(s"tb/RsdGenCoreArray_rad/arr_rad_d$cur_d.csv"),
              rsd_rad.map(_.abs)
            )
          }
        }
      }
    )

  }
  new File("tmp/rsd_gen_core_array_dat").mkdir()
  csvwrite(
    new File("tmp/rsd_gen_core_array_dat/h_timeshift.csv"),
    h_timeshift.map(_.real)
  )
  csvwrite(
    new File("tmp/rsd_gen_core_array_dat/h_distance.csv"),
    h_distance
  )
  csvwrite(
    new File("tmp/rsd_gen_core_array_dat/h_wave.csv"),
    h_wave
  )
  csvwrite(
    new File("tmp/rsd_gen_core_array_dat/h_impulse.csv"),
    h_impulse
  )

  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
    var umax = 0d
    for(d <- 0 until rsd_cfg.depth_factor) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/RsdGenCoreArray/nlos_hard_out.jpg")

  if(withWave){
    Process("vcd2vpd tb/RsdGenCoreArray/RsdGenCoreArray_tb.vcd tb/RsdGenCoreArray/RsdGenCoreArray_tb.vcd.vpd").!
    Process("vpd2fsdb tb/RsdGenCoreArray/RsdGenCoreArray_tb.vcd.vpd -o tb/RsdGenCoreArray/RsdGenCoreArray_tb.vcd.vpd.fsdb").!
    Process("verdi -ssf tb/RsdGenCoreArray/RsdGenCoreArray_tb.vcd.vpd.fsdb").!!
  }

}
