package Sim.MacArray

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Sim.Monitor._
import Fpga.MacArray._
import breeze.linalg.DenseMatrix
import breeze.math.Complex

object Monitor {

  def catchFuin(dut: RowMacArray): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.io.fft_out, row_out = false)
  }

  def catchRSDKernel(dut: RowMacArray): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.io.rsd_kernel, row_out = false)
  }

  def catchRsdFuinProd(dut: RowMacArray): DenseMatrix[Complex] = {
    catchVecData(dut.clockDomain, dut.rsd_fft_prod, dut.rsd_fft_prod_valid, row_out = false)
  }

  def catchMacResult(dut: RowMacArray): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.io.mac_result, row_out = false)
  }
}
