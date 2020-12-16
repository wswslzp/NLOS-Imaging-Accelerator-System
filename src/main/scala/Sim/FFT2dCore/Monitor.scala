package Sim.FFT2dCore

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Sim.Monitor._
import Fpga.{FFT2dCore, FFT2dCore_v1}
import breeze.linalg._
import breeze.math._

object Monitor {

  def catchMacResult(dut: FFT2dCore): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.io.data_from_mac, row_out = false)
  }

  def catchMacResult(dut: FFT2dCore_v1): DenseMatrix[Complex] = {
    catchFlowVecData(dut.clockDomain, dut.io.data_from_mac, row_out = false)
  }
}
