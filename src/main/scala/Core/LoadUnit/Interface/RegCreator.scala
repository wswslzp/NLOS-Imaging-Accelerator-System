package Core.LoadUnit.Interface

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Core.LoadUnit.Interface.RegCreator._

class RegCreator(initAddress: Int, word_bit_count: Int) extends AddressRecorder(word_bit_count) {

  private val memManager = ApplyMem(initAddress, word_bit_count)

  def createReg[T <: Data](data: T, wr_type: WrType): T = {
    val (data_reg_addr_map, data_reg) = memManager.allocateReg(data)
    val ranges = data_reg_addr_map.keys
    wr_type match {
      case RegCreator.RO => {
        val regs = data_reg_addr_map.values.map(DataWithRO(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case RegCreator.WO => {
        val regs = data_reg_addr_map.values.map(DataWithWO(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case RegCreator.RW => {
        val regs = data_reg_addr_map.values.map(DataWithRW(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case _ =>
    }
    data_reg
  }

  def createRegArray[T <: Data](data_array: Seq[T], wrType: WrType): Seq[T] = {
    val (data_regarr_addr_map, arr_reg) = memManager.allocateRegArray(data_array)
    val ranges = data_regarr_addr_map.keys
    wrType match {
      case RegCreator.RO => {
        val regs = data_regarr_addr_map.values.map(DataWithRO(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case RegCreator.WO => {
        val regs = data_regarr_addr_map.values.map(DataWithWO(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case RegCreator.RW => {
        val regs = data_regarr_addr_map.values.map(DataWithRW(_))
        arrangeRegMapAddr(ranges.zip(regs).toMap)
      }
      case _ =>
    }
    arr_reg
  }

  def createRam[T <: Data](mem: Mem[T]): Mem[T] = {
    val (addr_range, mem_inst) = memManager.allocateRam(mem)
    arrangeMemAddr(addr_range -> mem_inst)
    mem_inst
  }

}

object RegCreator {
  trait WrType
  object RO extends WrType
  object WO extends WrType
  object RW extends WrType
}
