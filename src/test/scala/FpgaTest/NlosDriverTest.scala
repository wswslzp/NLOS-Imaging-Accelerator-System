package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.NlosDriver
import Config.RsdKernelConfig._
import Sim.RsdGenCoreArray.Computation
import breeze.linalg._
import breeze.math._
import Sim.SimComplex._
import Sim.write_image
import breeze.signal._
import Sim.SimFix._
import java.io.File
import scala.sys.process._

object NlosDriverTest extends App{
  val loadUnitAddrs = rsd_cfg.loadUnitAddrs

  val h_img = Array.fill(rsd_cfg.freq_factor)(DenseMatrix.zeros[Complex](rsd_cfg.rows, rsd_cfg.cols))
  val h_ts = DenseMatrix.zeros[Complex](timeshift.rows, timeshift.cols)
  val h_ds = DenseMatrix.zeros[Double](distance.rows, distance.cols)
  val h_wv = DenseMatrix.zeros[Double](wave.rows, wave.cols)
  val h_imp = DenseMatrix.zeros[Double](impulse.rows, impulse.cols)

  val wave_init_addrs = Array.tabulate(rsd_cfg.radius_factor / 16){idx=>
    loadUnitAddrs(2) + idx * 16
  }

  val imp_init_addrs = Array.tabulate(rsd_cfg.impulse_sample_point * rsd_cfg.radius_factor/ 16){idx=>
    loadUnitAddrs(3) + idx * 16
  }

  SimConfig
    .allOptimisation
    .workspacePath("tb")
    .withWave
    .compile(NlosDriver(rsd_cfg, loadUnitAddrs))
    .doSim("NlosDriver_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.sys_init #= false
      dut.io.cnt_incr #= false
      dut.io.kernel_in.aw.ready #= true
      dut.io.kernel_in.w.ready #= true
      dut.io.load_req #= 15
      dut.io.done #= false
      dut.clockDomain.waitSampling()


      forkJoin(
        () => {
          dut.io.sys_init #= true
          for(d <- rsd_cfg.depthRange){
            for(f <- rsd_cfg.freqRange){
              dut.clockDomain.waitSampling()
              println(s"now the hardware counter is (${dut.io.dc.toInt}, ${dut.io.fc.toInt})")

              val waiting = fork{
                if(d == 0) {
                  dut.clockDomain.waitSampling(128*128+98)
                }
                else{
                  dut.clockDomain.waitSampling(128+100)
                }

                dut.io.load_req #= dut.io.load_req.toInt | 1 | 2
                if(f == rsd_cfg.freq_factor-1){
                  dut.io.load_req #= dut.io.load_req.toInt | 1 | 2 | 4
                }
              }

              fork{
                dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(0) + 1))
                dut.clockDomain.waitSampling()
                dut.io.load_req #= dut.io.load_req.toInt & 14 // load_req[0] = 0
              }

              fork{
                dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(1) + 1))
                dut.clockDomain.waitSampling()
                dut.io.load_req #= dut.io.load_req.toInt & 13 // load_req[1] = 0
              }

              if (f == 0){
                fork{
                  dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(2) + rsd_cfg.radius_factor))
                  dut.clockDomain.waitSampling()
                  dut.io.load_req #= dut.io.load_req.toInt & 11 // load_req[2] = 0
                }
              }

              if (d == 0 && f == 0){
                fork{
                  dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(3) + rsd_cfg.impulse_sample_point*rsd_cfg.radius_factor))
                  dut.clockDomain.waitSampling()
                  dut.io.load_req #= dut.io.load_req.toInt & 7 // load_req[3] = 0
                }
              }

              waiting.join()
              dut.io.cnt_incr #= true
              dut.clockDomain.waitSampling()
              dut.io.cnt_incr #= false
            }
          }
          dut.clockDomain.waitSampling(1280)
          dut.io.done #= true
          dut.clockDomain.waitSampling()
          dut.io.done #= false
          dut.clockDomain.waitSampling(100)
          simSuccess()
        }
        ,

        // image monitor
        // TODO: Need check
        () => {
          while(true){
            dut.clockDomain.waitSamplingWhere(dut.io.original_img.valid.toBoolean)
            for(r <- rsd_cfg.rowRange){
              for(c <- rsd_cfg.colRange){
                h_img(dut.io.fc.toInt)(r, c) = dut.io.original_img.payload.toComplex
                dut.clockDomain.waitSampling()
              }
            }
          }
        }
        ,

        // ts monitor
        () => {
          while(true){
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(0) && dut.io.kernel_in.aw.valid.toBoolean)
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
            val d = dut.io.dc.toInt
            val f = dut.io.fc.toInt
            h_ts(f, d) = bitsToComplex(dut.io.kernel_in.w.data.toLong, rsd_cfg.timeshift_cfg)
          }
        }
        ,

        // ds monitor
        () => {
          while(true){
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(1))
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
            val d = dut.io.dc.toInt
            val f = dut.io.fc.toInt
            h_ds(f, d) = bitsToDouble(dut.io.kernel_in.w.data, rsd_cfg.distance_cfg.getDataWidth, rsd_cfg.distance_cfg.fracw)
          }
        }
        ,

        // wave monitor
        () => {
          while(true){
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(2))
            for(r <- rsd_cfg.radiusRange){
              dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
              h_wv(r, dut.io.dc.toInt) = bitsToDouble(dut.io.kernel_in.w.data, rsd_cfg.wave_cfg.getDataWidth, rsd_cfg.wave_cfg.fracw)
            }
          }
        }
        ,

        // imp monitor
        () => {
          dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(3))
          for(idx <- 0 until ( impulse.rows * impulse.cols )) {
            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
            val rl = idx / impulse.cols
            val r = idx % impulse.cols
            h_imp(rl, r) = bitsToDouble(dut.io.kernel_in.w.data, rsd_cfg.imp_cfg.getDataWidth, rsd_cfg.imp_cfg.fracw)
          }
        }

      )


    }

  csvwrite(new File("tb/NlosDriver/h_timeshift_real.csv"), h_ts.map(_.real))
  csvwrite(new File("tb/NlosDriver/h_timeshift_imag.csv"), h_ts.map(_.imag))
  csvwrite(new File("tb/NlosDriver/h_distance.csv"), h_ds)
  csvwrite(new File("tb/NlosDriver/h_wave.csv"), h_wv)
  csvwrite(new File("tb/NlosDriver/h_impulse.csv"), h_imp)

  val h_coef = Computation.generateCoef(h_wv, h_ds, h_ts)
  val h_rsd = Computation.generateRSDRadKernel(h_coef, h_imp)
  val uin_fft = h_img.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  println(kernel_size)

  println("Multiply RSD kernel with input image's freq...")
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- rsd_cfg.freqRange) {
      val rsd_kernel_rad = h_rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    iFourierTr(uout_f)
  }
  println("Done RSD kernel convolution!")

  println("Generating output image...")
  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
    var umax = 0d
    for(d <- rsd_cfg.depthRange) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }
  println("Output image has been generated!")
  println(s"output size: cols = ${uout_abs_max.cols}")

  val uout_abs_max_flip = fliplr(uout_abs_max)
  csvwrite(new File("tb/NlosDriver/nlos_hard_out.csv"), uout_abs_max_flip)
  write_image(uout_abs_max_flip, "tb/NlosDriver/nlos_hard_out.jpg")


  val nullLogger = ProcessLogger(_ => {})
  println("Converting vcd to vpd...")
  Process("vcd2vpd tb/NlosDriver/NlosDriver_tb.vcd tb/NlosDriver/NlosDriver_tb.vpd") ! nullLogger
  println("Convert done.")
  Process("dve -full64 -vpd tb/NlosDriver/NlosDriver_tb.vpd -session session.NlosDriver_tb.vpd.tcl").run(nullLogger)
}
