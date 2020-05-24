package Util

import spinal.core._

import scala.collection.mutable

case class ApplyMem(init_addr: Int, word_count: Int) {
  private var acc_addr = init_addr
  private val addr_range_record = mutable.Map.empty[Range, String]

  def allocateReg[T <: BaseType](data_type: T): (Range, T) = {
    val regs = Reg(data_type)
    val width = regs.getBitsWidth
    val addr_range = acc_addr until acc_addr + width/word_count + 1
    addr_range_record += addr_range -> regs.getName()
    acc_addr += width/word_count
    addr_range -> regs
  }

  def allocateRegVec[T <: Data](data_type: Vec[T]): (Range, Vec[T]) = {
    val reg_vec = Reg(data_type)
    val width = reg_vec.getBitsWidth
    val addr_range = acc_addr until acc_addr + width/word_count + 1
    addr_range_record += addr_range -> reg_vec.getName()
    acc_addr += width/word_count
    acc_addr += width/word_count
    addr_range -> reg_vec
  }

  def allocateRegArray[T <: Data](reg_array: Seq[T]):Map[Range, Data] = {
    val ret = mutable.Map.empty[Range, Data]
    reg_array foreach {
      case baseType: BaseType => ret += allocateReg(baseType)
      case vec: Vec[Data] => ret += allocateRegVec(vec)
      case _ => SpinalError("Incorrect type")
    }
    ret.toMap
  }

  def allocateRam[T <: Data](mem: Mem[T]): (Range, Mem[T]) = {
    val mem_size = mem.wordCount
    val addr_range = acc_addr until acc_addr + mem_size
    addr_range_record += addr_range -> mem.getName()
    acc_addr += mem_size
    addr_range -> mem
  }

  def finalAddr: Int = acc_addr
  def addrRange: String = {
    var s = ""
    addr_range_record foreach {pair=>
      s += s"${pair._2}:\t\t${pair._1.head} -> ${pair._1.last} \n"
    }
    s
  }
}

