package Sim.NlosCore

import Fpga.NlosCore
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
    catchFlowVecData(dut, dut.io.result)
  }

  def catchMacResult(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut, dut.mac_array.io.mac_result, row_out = false)
  }

  def catchFUin(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut, dut.mac_array.io.fft_out, row_out = false)
  }

  def catchRSDK(dut: NlosCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut, dut.rgca.io.rsd_kernel, row_out = false)
  }

}
