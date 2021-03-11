package Sim.MacArray

import spinal.core.sim._
import Config.RsdKernelConfig._
import Sim.SimComplex._
import Fpga.MacArray._
import breeze.linalg._
import breeze.math._

object Driver {
  var depth = 0

  /**
   * Initialize the `RowMacArray` dut.
   * @param dut instance of `RowMacArray`
   */
  def dutInit(dut: RowMacArray): Unit = {
    dut.io.fc_overflow #= false
    dut.io.push_ending #= false
    dut.io.dc_eq_0 #= false
    dut.io.ifft2d_done #= false
    dut.io.fft_out.valid #= false
    dut.io.rsd_kernel.valid #= false
  }

  /**
   * Drive the Fourier domain of input signal into the `RowMacArray`
   * @param dut instance of RowMacArray
   * @param fuin Fourier domain of input signal
   */
  def driveFUin(dut: RowMacArray, fuin: Array[DenseMatrix[Complex]]): Unit = {
    for(d <- rsd_cfg.depthRange){
      dut.sim.dc #= d
      dut.io.dc_eq_0 #= d == 0
      for(f <- rsd_cfg.freqRange){
        dut.sim.fc #= f
        dut.io.fc_overflow #= (f == rsd_cfg.freq_factor-1)
        println(s"Now is ($d, $f).")
        depth = d
        if(d == 0) {
          dut.clockDomain.waitSampling(100)
        }
        else{
          dut.clockDomain.waitSampling(1)
        }
        dut.io.fft_out.valid #= true
        for(y <- 0 until rsd_cfg.kernel_size.last){
          dut.io.fft_out.payload.zipWithIndex.foreach{ case (complex, i) =>
            complex #= fuin(f)(i, y)
          }
          dut.clockDomain.waitSampling()
        }
        dut.io.fft_out.valid #= false
        dut.io.push_ending #= true
        dut.clockDomain.waitSampling()
        dut.io.push_ending #= false
      }
      dut.clockDomain.waitActiveEdgeWhere(dut.io.clear_confirm.toBoolean)
    }
    dut.io.fc_overflow #= false
  }

  /**
   * Drive the RSD kernel into the `RowMacArray` instance
   * @param dut design instance
   * @param rsdk the RSD kernel
   */
  def driveRSDKernel(dut: RowMacArray, rsdk: Array[Array[DenseMatrix[Complex]]]): Unit = {
    for(d <- rsd_cfg.depthRange){
      for(f <- rsd_cfg.freqRange){
        if(d == 0) {
          dut.clockDomain.waitSampling(100)
        }
        else{
          dut.clockDomain.waitSampling()
        }
        dut.io.rsd_kernel.valid #= true
        for(y <- 0 until rsd_cfg.kernel_size.last){
          dut.io.rsd_kernel.payload.zipWithIndex.foreach{ case (complex, i) =>
            complex #= rsdk(d)(f)(i, y)
          }
          dut.clockDomain.waitSampling()
        }
        dut.io.rsd_kernel.valid #= false
        dut.clockDomain.waitSampling()
      }
      if(d != 0){
        dut.clockDomain.waitSampling(1000)
        dut.io.ifft2d_done #= true
        dut.clockDomain.waitSampling()
        dut.io.ifft2d_done #= false
      }
      dut.clockDomain.waitActiveEdgeWhere(dut.io.clear_confirm.toBoolean)
    }
    dut.clockDomain.waitSampling(1000)
    simSuccess()
  }
}
