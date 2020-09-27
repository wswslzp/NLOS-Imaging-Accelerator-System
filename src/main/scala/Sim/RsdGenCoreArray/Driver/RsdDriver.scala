package Sim.RsdGenCoreArray.Driver

import Config._
import Core.RsdGenCoreArray.RsdGenCoreArray
import breeze.linalg._
import breeze.math._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi._

case class RsdDriver(dut: RsdGenCoreArray) {
  val axi4Config = dut.axi_config

  def driveData(data: DenseMatrix[Int], address: Long): Unit = {
    val totalElementNum = data.cols * data.rows
    val totalNumOfDataTransfer = totalElementNum / 16
    val flatData = data.t.flatten().toScalaVector() ++ List.fill(
      (totalNumOfDataTransfer + 1)*16
    )(0)
    val reshapeData = DenseVector.tabulate(flatData.length)(flatData(_)).toDenseMatrix.reshape(
      totalNumOfDataTransfer + 1, 16
    ).t

    // No out-of-order transfer happens.
    // First address transfer for a transaction, next
    // first data transfer for the same transaction.
    forkJoin(
      // Do address write on aw channel
      () => {
        var addr = address
        dut.data_in.aw.burst #= 1 // 2'b01 --> INCR
        dut.data_in.aw.len #= 15 // 4'b1111 --> 16
        dut.data_in.aw.size #= 2 // 3'b010 --> 4 byte
        for(i <- 0 until totalNumOfDataTransfer) {
          dut.data_in.aw.valid #= true
          dut.data_in.aw.addr #= addr
          waitUntil(dut.data_in.aw.fire.toBoolean)
          addr += 16
          dut.data_in.aw.valid #= false
          waitUntil(dut.data_in.w.last.toBoolean)
        }
      }
      ,
      // Do data write on w channel
      () => {
        for(i <- 0 until totalNumOfDataTransfer) {
          waitUntil(dut.data_in.aw.fire.toBoolean)
          for(j <- 0 until 16) {
            dut.data_in.w.valid #= true
            dut.data_in.w.last #= (j == 15)
            dut.data_in.w.data #= reshapeData(i, j)
            waitUntil(dut.data_in.w.fire.toBoolean)
            dut.clockDomain.waitSampling()
          }
          dut.data_in.w.valid #= false
        }
      }
    )
  }

  def driveDoubleData(data: DenseMatrix[Double], address: Long, fraction: Int): Unit = {
    val dataToDouble = data.map {dat=>
      ( dat * fraction ).toInt
    }
    driveData(dataToDouble, address)
  }

  def driveComplexData(data: DenseMatrix[Complex], address: Long, hComplexConfig: HComplexConfig): Unit = {
    val complexDataToDouble = data.map{ comp =>
      val realToInt = ( comp.real * hComplexConfig.fracw ).toInt
      val imagToInt = ( comp.imag * hComplexConfig.fracw ).toInt
      if (hComplexConfig.real_high) {
        realToInt * hComplexConfig.getDataWidth + imagToInt
      } else {
        imagToInt * hComplexConfig.getDataWidth + realToInt
      }
    }
    driveData(complexDataToDouble, address)
  }
}
