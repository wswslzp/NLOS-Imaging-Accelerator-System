package Sim.RsdGenCoreArray.Driver

import Config._
import Core.RsdGenCoreArray.RsdGenCoreArray_old
import breeze.linalg._
import breeze.math._
import spinal.core._
import spinal.core.sim._
import spinal.lib.bus.amba4.axi._
import spinal.sim.SimThread

case class RsdDriver(bus: Axi4WriteOnly, clockDomain: ClockDomain) {
  val maximum_value: Long = 1L << bus.config.dataWidth
  def intToUInt(x: Int) = (maximum_value + x) % maximum_value
  def intToUInt(x: Long) = (maximum_value + x) % maximum_value

  def driveData(data: Long, address: Long): Unit = {
    forkJoin(
      () => {
        bus.aw.burst #= 1 // 2'b01 --> INCR
        bus.aw.len #= 0 // 4'b0000 --> 1
        bus.aw.size #= 2 // 3'b010 --> 4 byte
        bus.aw.valid #= true
        bus.aw.addr #= address
        clockDomain.waitActiveEdgeWhere(
          bus.aw.valid.toBoolean && bus.aw.ready.toBoolean
        )
        bus.aw.valid #= false
        clockDomain.waitActiveEdgeWhere(
          bus.w.last.toBoolean
        )
      }
      ,
      () => {
        clockDomain.waitActiveEdgeWhere(
          bus.aw.valid.toBoolean && bus.aw.ready.toBoolean
        )
        bus.w.valid #= true
        bus.w.last #= true
        bus.w.data #= data.toLong
        clockDomain.waitActiveEdgeWhere(
          bus.w.valid.toBoolean && bus.w.ready.toBoolean
        )
        bus.w.valid #= false
        bus.w.last #= false
      }
    )
  }

  def driveData(data: DenseVector[Long], initAddress: Long): Unit = {
    val totalElementNum = data.length
    val totalNumOfDataTransfer = totalElementNum / 16
    val flatData = data.toScalaVector() ++ List.fill(
      16 - (totalElementNum % 16)
    )(0L)
    // In breeze, column is first
    val reshapeData = DenseVector.tabulate(flatData.length)(flatData(_)).toDenseMatrix.reshape(
      16, totalNumOfDataTransfer + 1
    ).t

    // No out-of-order transfer happens.
    // First address transfer for a transaction, next
    // first data transfer for the same transaction.
    forkJoin(
      // Do address write on aw channel
      () => {
        var addr = initAddress
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

  def driveData(data: DenseMatrix[Long], initAddress: Long, col_first: Boolean = true): Unit = {
    val totalElementNum = data.cols * data.rows
    val totalNumOfDataTransfer = totalElementNum / 16
    val flatData = data.t.flatten().toScalaVector() ++ List.fill(
      16 - (totalElementNum % 16)
    )(0L)
    // In breeze, column is first
    val reshapeData = DenseVector.tabulate(flatData.length)(flatData(_)).toDenseMatrix.reshape(
      16, totalNumOfDataTransfer + 1
    ).t

    // No out-of-order transfer happens.
    // First address transfer for a transaction, next
    // first data transfer for the same transaction.
    forkJoin(
      // Do address write on aw channel
      () => {
        var addr = initAddress
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

  def driveDoubleData(data: Any, initAddress: Long, fraction: Int): Unit = {
    def doubleToULong(x: Double): Long = {
      val toSInt = Math.round(x * (1 << fraction)).toInt
      val sintToUInt = intToUInt(toSInt)
      sintToUInt
    }
    data match {
      case double: Double =>
        val dataToInt = doubleToULong(double)
        driveData(dataToInt, initAddress)

      case denseVector: DenseVector[_] =>
        val dataToInt: DenseVector[Long] = denseVector map {dat =>
          doubleToULong(dat.asInstanceOf[Double])
        }
        driveData(dataToInt, initAddress)

      case denseMatrix: DenseMatrix[_] =>
        val dataToInt: DenseMatrix[Long] = denseMatrix map {dat =>
          doubleToULong(dat.asInstanceOf[Double])
        }
        driveData(dataToInt, initAddress)

      case _ => simFailure("Type match ERROR! Only for Double, DenseVector[Double] and DenseMatrix[Double]")
    }
  }

  def driveComplexData(data: Any, initAddress: Long, hComplexConfig: HComplexConfig): Unit = {
    def mergeComplex(complex: Complex): Long = {
      val real: Long = Math.round(
        complex.real * (1 << hComplexConfig.fracw)
      )
      val imag: Long = Math.round(
        complex.imag * (1 << hComplexConfig.fracw)
      )
      val high_mask: Long = (1 << hComplexConfig.getDataWidth) - 1
      if (hComplexConfig.real_high) {
        intToUInt( (real << hComplexConfig.getDataWidth) | (imag & high_mask) )
      } else {
        intToUInt( (imag << hComplexConfig.getDataWidth) | (real & high_mask) )
      }
    }
    data match {
      case complex: Complex =>
        driveData(mergeComplex(complex), initAddress)

      case denseVector: DenseVector[_] =>
        val dataToInt: DenseVector[Long] = denseVector map {dat =>
          val comp = dat.asInstanceOf[Complex]
          mergeComplex(comp)
        }
        driveData(dataToInt, initAddress)

      case denseMatrix: DenseMatrix[_] =>
        val dataToInt: DenseMatrix[Long] = denseMatrix map {dat =>
          val comp = dat.asInstanceOf[Complex]
          mergeComplex(comp)
        }
        driveData(dataToInt, initAddress)

      case _ => simFailure("Type match ERROR! Only for Complex, DenseVector[Complex] and DenseMatrix[Complex]")
    }

  }
}
