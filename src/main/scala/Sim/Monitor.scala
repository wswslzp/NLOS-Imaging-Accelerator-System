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

  def catchFlowVecData(dut: Component, flow: Flow[Vec[HComplex]], row_out: Boolean = true): DenseMatrix[Complex] = {
    val ret = DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    dut.clockDomain.waitActiveEdgeWhere(flow.valid.toBoolean)
    if(row_out){
      for(r <- rsd_cfg.rowRange){
        for(c <- rsd_cfg.colRange){
          ret(r, c) = flow.payload(c).toComplex
        }
        dut.clockDomain.waitSampling()
      }
    }else{
      for(c <- rsd_cfg.colRange){
        for(r <- rsd_cfg.rowRange){
          ret(r, c) = flow.payload(r).toComplex
        }
        dut.clockDomain.waitSampling()
      }
    }
    ret
  }

}
