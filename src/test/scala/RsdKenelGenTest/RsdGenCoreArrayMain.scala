package RsdKenelGenTest

import Config._
import Core._
import breeze.linalg.{DenseMatrix, DenseVector, fliplr}
import breeze.math.Complex
import spinal.core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi.Axi4Config
import Sim.SimComplex._
import Sim.SimFix._
import SimTest.NlosSystemSimTest.write_image
import breeze.signal._

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

  val uin = Array.tabulate(rsd_cfg.freq_factor){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/data/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/data/imag/uin_${idx+1}.csv"
    )
  }
  val uout = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))
  )
  val uin_fft = uin.map(fourierTr(_))
  val hard_rsd_kernel = DenseMatrix.fill(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)(Complex(0, 0))

  SimConfig
    .withFstWave
    .noOptimisation
    .workspacePath("tb")
    .addSimulatorFlag("--hierarchical")
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

//      fork{
//        SimTimeout(9000000)
//      }

      forkJoin(
        () => {
          // Driver to send the input data
          for(d <- 0 until rsd_cfg.depth_factor) {
            dd = d
            dut.io.dc #= d
            dut.io.dc_eq_0 #= d == 0
            for(f <- 0 until rsd_cfg.freq_factor) {
              ff = f
              dut.io.fc #= f
              dut.io.fc_eq_0 #= f == 0
//              println(s"df = ($d, $f)")
              dut.clockDomain.waitSampling()
              if((dut.io.load_req.toInt & 1) == 1) {
//                println("TS")
//                println(s"timeshift($f, $d) = ${timeshift(f, d)}")
                rsdDriver.driveComplexData(timeshift(f, d), dut.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
                rsdDriver.driveData(1, dut.loadUnitAddrs(0) + 1)
              }
              dut.clockDomain.waitSampling()
              if((dut.io.load_req.toInt & 2) == 2) {
//                println("D")
                rsdDriver.driveDoubleData(distance(f, d), dut.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
                rsdDriver.driveData(1, dut.loadUnitAddrs(1) + 1)
              }
              dut.clockDomain.waitSampling()
              if(f == 0) {
                if((dut.io.load_req.toInt & 4) == 4) {
//                  println("Wave")
                  rsdDriver.driveDoubleData(wave(::, d), dut.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
                  rsdDriver.driveData(1, dut.loadUnitAddrs(2) + dut.cfg.radius_factor)
                }
                dut.clockDomain.waitSampling()
              }
              if((d == 0) && (f == 0)) {
                if((dut.io.load_req.toInt & 8) == 8) {
//                  println("Imp")
                  rsdDriver.driveComplexData(impulse, dut.loadUnitAddrs(3), dut.cfg.imp_cfg)
                  rsdDriver.driveData(1, dut.loadUnitAddrs(3) + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
                }
                dut.clockDomain.waitSampling()
              }
              if(d == 0) {
                // for d == 0, kernel pushing needs to wait for fft2d output valid.
                // waiting cycle ~ K^2, so we set 100 cycles
                dut.io.fft2d_out_sync #= true
                dut.clockDomain.waitSampling()
                dut.io.fft2d_out_sync #= false
                dut.clockDomain.waitSampling(128)
              } else {
                dut.clockDomain.waitSampling(10)
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
          // Monitor to catch the rsd kernel output
          while(true) {
            val cur_d = dd
            val cur_f = ff
            dut.clockDomain.waitActiveEdgeWhere(dut.io.rsd_kernel.valid.toBoolean)
            for(y <- 0 until rsd_cfg.kernel_size.last) {
              for(x <- 0 until rsd_cfg.kernel_size.head) {
                hard_rsd_kernel(x, y) = dut.io.rsd_kernel.payload(x).toComplex
              }
              dut.clockDomain.waitSampling()
            }
            uout(cur_d) += hard_rsd_kernel *:* uin_fft(cur_f)
            if(cur_d == rsd_cfg.depth_factor-1){
              uout(cur_d) = iFourierTr(uout(cur_d))
            }
          }
        }
      )

    }

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

//  SpinalConfig(
//    targetDirectory = "rtl/RsdGenCoreArray",
//    oneFilePerComponent = true
//  ).generateVerilog(
//    RsdGenCoreArray(rsd_cfg, init_addr)(axi_cfg)
//  )
//
}
