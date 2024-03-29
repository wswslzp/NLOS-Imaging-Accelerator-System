package Sim.NlosCore

import Fpga.{NlosCore, NlosFpgaCore, NlosNoDriver}
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
//    catchFlowVecData(dut.clockDomain, dut.fft2d_core.io.data_to_mac, row_out = false)
    catchFlowVecData(dut.clockDomain, dut.mac_array.io.fft_out, row_out = false)
  }

  def catchRSDK(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.rgca.io.rsd_kernel, row_out = false)
  }

  def catchRsdFuinProd(dut: NlosCore): DenseMatrix[Complex] = {
    catchVecData(dut.clockDomain, dut.mac_array.rsd_fft_prod, dut.mac_array.rsd_fft_prod_valid, row_out = false)
  }

  def catchResult(dut: NlosNoDriver): DenseMatrix[Double] = {
    val rows = dut.cfg.rows * dut.post_proc.over_sample_factor
    val cols = dut.cfg.cols * dut.post_proc.over_sample_factor
    println(s"rows is $rows, cols is $cols")
    dut.io.result.ready #= true
    dut.clockDomain.waitActiveEdgeWhere(dut.io.result.valid.toBoolean)
    val result = DenseMatrix.zeros[Double](rows, cols)
    for(r <- 0 until dut.cfg.rows * dut.post_proc.over_sample_factor){
      for(c <- 0 until dut.cfg.cols * dut.post_proc.over_sample_factor) {
        for(p <- 0 until 1) {
          result(r, c * 1 + p) = dut.io.result.payload.toInt
        }
        dut.clockDomain.waitSampling()
      }
    }
    result
  }

  def catchResult(dut: NlosFpgaCore): DenseMatrix[Double] = {
    import scala.util.Random._
    dut.clockDomain.waitActiveEdgeWhere(dut.io.result.valid.toBoolean)
    val osf = dut.nlos_no_driver.post_proc.over_sample_factor
    val result = DenseMatrix.zeros[Double](dut.cfg.rows * osf, dut.cfg.cols * osf)
    for(r <- 0 until dut.cfg.rows * osf){
      for(c <- 0 until dut.cfg.cols * osf) {
        dut.io.result.ready #= false
        dut.clockDomain.waitSampling(nextInt(6))
        dut.io.result.ready #= true
        result(r, c) = dut.io.result.payload.toInt
        dut.clockDomain.waitSampling()
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
