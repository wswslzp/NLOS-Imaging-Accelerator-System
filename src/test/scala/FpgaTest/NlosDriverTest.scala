package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.NlosDriver
import Config.RsdKernelConfig._
import breeze.linalg._
import breeze.math._
import Sim.SimComplex._
import scala.sys.process._

object NlosDriverTest extends App{
  val loadUnitAddrs = rsd_cfg.loadUnitAddrs

  val h_img = Array.fill(rsd_cfg.freq_factor)(DenseMatrix.zeros[Complex](rsd_cfg.rows, rsd_cfg.cols))
  val h_ts = DenseMatrix.zeros[Complex](timeshift.rows, timeshift.cols)
  val h_ds = DenseMatrix.zeros[Double](distance.rows, distance.cols)
  val h_wv = DenseMatrix.zeros[Double](wave.rows, wave.cols)
  val h_imp = DenseMatrix.zeros[Double](impulse.rows, impulse.cols)

  SimConfig
    .allOptimisation
    .workspacePath("tb")
    .withWave
    .compile(NlosDriver(rsd_cfg, loadUnitAddrs))
    .doSim("NlosDriver_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.sys_init #= false
      dut.io.fft_comp_end #= false
      dut.io.cnt_incr #= false
      dut.io.kernel_in.aw.ready #= true
      dut.io.kernel_in.w.ready #= true
      dut.io.load_req #= 15
      dut.clockDomain.waitSampling()


      forkJoin(
        () => {
          dut.io.sys_init #= true
          for(d <- rsd_cfg.depthRange){
            for(f <- rsd_cfg.freqRange){
              println(s"now is ($d, $f)")
              dut.clockDomain.waitSampling()
              println(s"now the hardware counter is (${dut.io.dc.toInt}, ${dut.io.fc.toInt})")

              val waiting = fork{
                if(d == 0) {
                  dut.clockDomain.waitSampling(128*128+98)
                  dut.io.fft_comp_end #= true
                  dut.clockDomain.waitSampling()
                  dut.io.fft_comp_end #= false
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

              fork{
                dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(2) + rsd_cfg.radius_factor))
                dut.clockDomain.waitSampling()
                dut.io.load_req #= dut.io.load_req.toInt & 11 // load_req[2] = 0
              }

              fork{
                dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == (loadUnitAddrs(3) + rsd_cfg.impulse_sample_point*rsd_cfg.radius_factor))
                dut.clockDomain.waitSampling()
                dut.io.load_req #= dut.io.load_req.toInt & 7 // load_req[3] = 0
              }

              waiting.join()
              dut.io.cnt_incr #= true
              dut.clockDomain.waitSampling()
              dut.io.cnt_incr #= false
            }
          }
          dut.clockDomain.waitSampling(100)
          simSuccess()
        }
        ,

        // image monitor
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
          for(d <- rsd_cfg.depthRange){
            for(f <- rsd_cfg.freqRange){
              dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(0))
              dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
              h_ts(f, d) = bitsToComplex(dut.io.kernel_in.w.data.toLong, rsd_cfg.timeshift_cfg)
            }
          }
        }
        ,

        // ds monitor
        () => {
          for(d <- rsd_cfg.depthRange){
            for(f <- rsd_cfg.freqRange){
              dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toLong == loadUnitAddrs(1))
              dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.w.valid.toBoolean)
              h_ts(f, d) = bitsToComplex(dut.io.kernel_in.w.data.toLong, rsd_cfg.timeshift_cfg)
            }
          }
        }
//        ,
//
//        // wave monitor
//        () => {
//          for(d <- rsd_cfg.depthRange){
//            dut.clockDomain.waitSamplingWhere(dut.io.kernel_in.aw.addr.toInt == loadUnitAddrs(2))
//            for(r <- rsd_cfg.radiusRange){
//              dut.clockDomain.waitSampling()
//              h_wv()
//            }
//          }
//        }
      )


    }


  val nullLogger = ProcessLogger(_ => {})
  println("Converting vcd to vpd...")
  Process("vcd2vpd tb/NlosDriver/NlosDriver_tb.vcd tb/NlosDriver/NlosDriver_tb.vpd") ! nullLogger
  println("Convert done.")
  Process("dve -full64 -vpd tb/NlosDriver/NlosDriver_tb.vpd -session session.NlosDriver_tb.vpd.tcl") !!
}
