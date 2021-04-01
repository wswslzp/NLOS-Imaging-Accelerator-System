package Sim

import Util.HComplex
import breeze.linalg.DenseMatrix
import breeze.math.Complex
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Sim.SimComplex._

object Monitor {

  import Config.RsdKernelConfig._

  def catchVecData(clock: ClockDomain, payload: Vec[HComplex], valid: Bool, row_out: Boolean = true): DenseMatrix[Complex] = {
    val ret = DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    clock.waitActiveEdgeWhere(valid.toBoolean)
    if (row_out) {
      for (r <- rsd_cfg.rowRange) {
        for (c <- rsd_cfg.colRange) {
          ret(r, c) = payload(c).toComplex
        }
        clock.waitSampling()
      }
    } else {
      for (c <- rsd_cfg.colRange) {
        for (r <- rsd_cfg.rowRange) {
          ret(r, c) = payload(r).toComplex
        }
        clock.waitSampling()
      }
    }
    ret
  }

  def catchFlowData(clock: ClockDomain, flow: Flow[HComplex], row_out: Boolean = true): DenseMatrix[Complex] = {
    val ret = DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    clock.waitActiveEdgeWhere(flow.valid.toBoolean)

    if (row_out) {
      for (r <- rsd_cfg.rowRange) {
        for (c <- rsd_cfg.colRange) {
          ret(r, c) = flow.payload.toComplex
          clock.waitSampling()
        }
      }
    } else {
      for (c <- rsd_cfg.colRange) {
        for (r <- rsd_cfg.rowRange) {
          ret(r, c) = flow.payload.toComplex
          clock.waitSampling()
        }
      }
    }
    ret
  }

  def catchFlowVecData(clock: ClockDomain, flow: Flow[Vec[HComplex]], row_out: Boolean = true): DenseMatrix[Complex] = {
    catchVecData(clock, flow.payload, flow.valid, row_out)
  }

  //    val ret = DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  //    clock.waitActiveEdgeWhere(flow.valid.toBoolean)
  //    if(row_out){
  //      for(r <- rsd_cfg.rowRange){
  //        for(c <- rsd_cfg.colRange){
  //          ret(r, c) = flow.payload(c).toComplex
  //        }
  //        clock.waitSampling()
  //      }
  //    }else{
  //      for(c <- rsd_cfg.colRange){
  //        for(r <- rsd_cfg.rowRange){
  //          ret(r, c) = flow.payload(r).toComplex
  //        }
  //        clock.waitSampling()
  //      }
  //    }
  //    ret
  //  }
}