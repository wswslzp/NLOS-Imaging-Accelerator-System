package Sim.NlosCore

import Fpga.{NlosCore, NlosNoDriver}
import breeze.linalg.DenseMatrix
import breeze.math.Complex
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Sim.SimComplex._
import Sim.Monitor._

object Monitor {

  /**
   * catch the result from NLOS system only once per invocation
   * @param dut
   * @return An output image result of a depth
   */
  def catchResult(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowData(dut.clockDomain, dut.io.result)
  }

  def catchMacResult(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.mac_array.io.mac_result, row_out = false)
  }

  def catchFUin(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.fft2d_core.io.data_to_mac, row_out = false)
  }

  def catchRSDK(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.rgca.io.rsd_kernel, row_out = false)
  }

  def catchResult(dut: NlosNoDriver): DenseMatrix[Double] = {
    dut.io.result.ready #= true
    dut.clockDomain.waitActiveEdgeWhere(dut.io.result.valid.toBoolean)
    val result = DenseMatrix.zeros[Double](dut.cfg.rows * dut.post_proc.over_sample_factor, dut.cfg.cols * dut.post_proc.over_sample_factor)
    for(r <- 0 until dut.cfg.rows * dut.post_proc.over_sample_factor){
      for(c <- 0 until dut.cfg.cols * dut.post_proc.over_sample_factor/dut.post_proc.pixel_parallel) {
        for(p <- 0 until dut.post_proc.pixel_parallel) {
          result(r, c * dut.post_proc.pixel_parallel + p) = dut.io.result.payload(p).toInt
        }
      }
    }
    result
  }

  def catchMacResult(dut: NlosNoDriver): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.nlos_core.mac_array.io.mac_result, row_out = false)
  }

  def catchFUin(dut: NlosNoDriver): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.nlos_core.fft2d_core.io.data_to_mac, row_out = false)
  }

  def catchRSDK(dut: NlosNoDriver): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.nlos_core.rgca.io.rsd_kernel, row_out = false)
  }
}
