package Sim.RsdGenCoreArray.Driver

import Config._
import Core.RsdGenCoreArray.RsdGenCoreArray
import breeze.linalg._
import breeze.math._
import spinal.core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi._
import spinal.sim.SimThread

case class RsdDriver(bus: Axi4WriteOnly, clockDomain: ClockDomain) {
//  val axi4Config = dut.axi_config

  def driveData(data: DenseMatrix[Int], address: Long): Unit = {
    val totalElementNum = data.cols * data.rows
    val totalNumOfDataTransfer = totalElementNum / 16
    val flatData = data.t.flatten().toScalaVector() ++ List.fill(
      16 - (totalElementNum % 16)
    )(0)
    // In breeze, column is first
    val reshapeData = DenseVector.tabulate(flatData.length)(flatData(_)).toDenseMatrix.reshape(
      16, totalNumOfDataTransfer + 1
    ).t
    println(s"Total elements' number is $totalElementNum.\n Total Number of Data Transfer is ${totalNumOfDataTransfer + 1}")
    println(s"reshapeData is \n ${reshapeData.toString()}")

    // No out-of-order transfer happens.
    // First address transfer for a transaction, next
    // first data transfer for the same transaction.
    forkJoin(
      // Do address write on aw channel
      () => {
        var addr = address
        bus.aw.burst #= 1 // 2'b01 --> INCR
        bus.aw.len #= 15 // 4'b1111 --> 16
        bus.aw.size #= 2 // 3'b010 --> 4 byte
        for(_ <- 0 to totalNumOfDataTransfer) {
          bus.aw.valid #= true
          bus.aw.addr #= addr
          clockDomain.waitActiveEdgeWhere(bus.aw.valid.toBoolean && bus.aw.ready.toBoolean)
          addr += 16
          bus.aw.valid #= false
          clockDomain.waitActiveEdgeWhere(bus.w.last.toBoolean)
        }
      }
      ,
      // Do data write on w channel
      () => {
        for(i <- 0 to totalNumOfDataTransfer) {
          clockDomain.waitActiveEdgeWhere(bus.aw.ready.toBoolean && bus.aw.valid.toBoolean)
          for(j <- 0 until 16) {
            bus.w.valid #= true
            //TODO: The last signal activate when j==15 or the final data arrive.
            //   Do something
            bus.w.last #= (j == 15)
            bus.w.data #= reshapeData(i, j)
            clockDomain.waitActiveEdgeWhere(bus.w.valid.toBoolean && bus.w.ready.toBoolean)
          }
          bus.w.last #= false
          bus.w.valid #= false
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
