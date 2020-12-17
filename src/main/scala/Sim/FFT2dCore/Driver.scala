package Sim.FFT2dCore

import spinal.core._
import spinal.core.sim._
import Fpga.{FFT2dCore, FFT2dCore_v1}
import Config.RsdKernelConfig._
import Sim.SimComplex._
import breeze.linalg._
import breeze.math._

object Driver {
  var depth = 0
  var freq = 0

  /**
   * initialize DUT
   * @param dut FFT2dCore inst
   */
  def dutInit(dut: FFT2dCore): Unit = {
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.io.push_start #= false
    dut.io.data_in.valid #= false
    dut.io.data_from_mac.valid #= false
  }

  /**
   * initialize DUT
   * @param dut FFT2dCore inst
   */
  def dutInit(dut: FFT2dCore_v1): Unit = {
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.io.push_start #= false
    dut.io.push_ending #= false
    dut.io.done #= false
    dut.io.data_in.valid #= false
    dut.io.data_from_mac.valid #= false
  }

  /**
   * Drive the needed data into FFT2dCore
   * @param dut FFT2dCore inst
   * @param huout_f MAC result.
   */
  def driveData(dut: FFT2dCore, huout_f: Array[DenseMatrix[Complex]]): Unit = {

    for(d <- rsd_cfg.depthRange){
      depth = d
      dut.io.dc #= d

      //Driver
      // For d == 0, pipe in `uin`
      if (d == 0) {
        for(f <- rsd_cfg.freqRange){
          println(s"Now is ($d, $f)")
          freq = f
          dut.io.fc #= f
          dut.io.data_in.valid #= true
          for(x <- rsd_cfg.rowRange){
            for(y <- rsd_cfg.colRange){
              dut.io.data_in.payload #= uin(f)(x, y)
              dut.clockDomain.waitSampling()
            }
          }
          dut.io.data_in.valid #= false
          dut.clockDomain.waitActiveEdgeWhere(dut.io.fft2d_out_sync.toBoolean)
          dut.clockDomain.waitSampling(rsd_cfg.kernel_size.head - 2)
          dut.io.push_start #= true
          dut.clockDomain.waitSampling()
          dut.io.push_start #= false
        }
      }

      // For d > 0, reuse the `uin_fft`
      else {
        for(f <- rsd_cfg.freqRange){
          println(s"Now is ($d, $f)")
          freq = f
          dut.io.fc #= f
          dut.clockDomain.waitSampling()
          if(f == 0){
            dut.io.data_from_mac.valid #= true
            for(c <- rsd_cfg.colRange){
              for(r <- rsd_cfg.rowRange){
                dut.io.data_from_mac.payload(r) #= huout_f(depth-1)(r, c)
              }
              dut.clockDomain.waitSampling()
            }
            dut.io.data_from_mac.valid #= false
          }else{
            for(_ <- rsd_cfg.colRange){
              dut.clockDomain.waitSampling()
            }
          }
          dut.io.push_start #= true
          dut.clockDomain.waitSampling()
          dut.io.push_start #= false
        }
      }

    }

    // After all depth gone
    println("Final pipe in")
    dut.clockDomain.waitSampling()
    dut.io.data_from_mac.valid #= true
    for(c <- rsd_cfg.colRange){
      for(r <- rsd_cfg.rowRange){
        dut.io.data_from_mac.payload(r) #= huout_f(depth)(r, c)
      }
      dut.clockDomain.waitSampling()
    }
    dut.io.data_from_mac.valid #= false
    dut.clockDomain.waitSampling()

    // All done
    dut.clockDomain.waitSampling(201)
    simSuccess()
  }

  /**
   * Drive the needed data into FFT2dCore
   * @param dut FFT2dCore inst
   * @param huout_f MAC result.
   */
  def driveData(dut: FFT2dCore_v1, huout_f: Array[DenseMatrix[Complex]]): Unit = {
    // TODO: Modify the driver code for version 1

    for(d <- rsd_cfg.depthRange){
      depth = d
      dut.io.dc #= d

      //Driver
      // For d == 0, pipe in `uin`
      if (d == 0) {
        for(f <- rsd_cfg.freqRange){
          println(s"Now is ($d, $f)")
          freq = f
          dut.io.fc #= f
          dut.io.data_in.valid #= true
          for(x <- rsd_cfg.rowRange){
            for(y <- rsd_cfg.colRange){
              dut.io.data_in.payload #= uin(f)(x, y)
              dut.clockDomain.waitSampling()
            }
          }
          dut.io.data_in.valid #= false
          dut.clockDomain.waitActiveEdgeWhere(dut.io.fft2d_out_sync.toBoolean)
          dut.io.push_start #= true // todo: here push start should be sync with fft2d out sync
          dut.clockDomain.waitSampling()
          dut.io.push_start #= false
          dut.clockDomain.waitSampling(rsd_cfg.kernel_size.head)
        }
        dut.io.data_from_mac.valid #= true
        for(c <- rsd_cfg.colRange){
          for(r <- rsd_cfg.rowRange){
            dut.io.data_from_mac.payload(r) #= huout_f(depth-1)(r, c)
          }
          dut.clockDomain.waitSampling()
        }
        dut.io.data_from_mac.valid #= false
        dut.clockDomain.waitSampling(rsd_cfg.kernel_size.head + 10)
      }

      // For d > 0, do ifft2d on data from MAC
      else {
        for(f <- rsd_cfg.freqRange){
          dut.io.push_start #= true
          dut.clockDomain.waitSampling()
          dut.io.push_start #= false
          println(s"Now is ($d, $f)")
          freq = f
          dut.io.fc #= f
          dut.clockDomain.waitSampling()
        }
        // After all freq fft out
        // wait for ifft done
        dut.clockDomain.waitActiveEdgeWhere(dut.io.ifft2d_comp_done.toBoolean)
        dut.io.data_from_mac.valid #= true
        for(c <- rsd_cfg.colRange){
          for(r <- rsd_cfg.rowRange){
            dut.io.data_from_mac.payload(r) #= huout_f(depth-1)(r, c)
          }
          dut.clockDomain.waitSampling()
        }
        dut.io.data_from_mac.valid #= false
        dut.clockDomain.waitSampling(rsd_cfg.kernel_size.head + 10)
      }

    }

//    // After all depth gone
//    println("Final pipe in")
//    dut.clockDomain.waitSampling()
//    dut.io.data_from_mac.valid #= true
//    for(c <- rsd_cfg.colRange){
//      for(r <- rsd_cfg.rowRange){
//        dut.io.data_from_mac.payload(r) #= huout_f(depth)(r, c)
//      }
//      dut.clockDomain.waitSampling()
//    }
//    dut.io.data_from_mac.valid #= false
//    dut.clockDomain.waitSampling()

    // All done
//    dut.clockDomain.waitSampling(201)
    dut.clockDomain.waitActiveEdgeWhere(dut.io.ifft2d_comp_done.toBoolean)
    simSuccess()
  }
}
