package Util

import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi._

import scala.collection.mutable.ListBuffer

// User need to extend this trait after component
// override the axi_config in the component's constructor parameter
// and the word_bit_count inside the constructor
// arrange the address range and call the loadData() function
// finally call the awReady() and the wReady() to set when the ready
// signal is valid.
trait AXI4WLoad {
  // a abstract config of axi4
  val axi_config: Axi4Config
  val word_bit_count: Int
  val byte_per_word: Int = word_bit_count / 8

  val data_in = slave(
    Axi4WriteOnly(
      axi_config
    )
  ).setName("axi4")

  def awReady(set: Bool): Unit = data_in.aw.ready := set

  val aw_area = new Area {
    //    data_in.aw.ready := True
    val awaddr_r: UInt = Reg(
      UInt(axi_config.addressWidth bit)
    )
    val awlen_r: UInt = Reg(
      UInt(8 bit)
    )
    val awsize_r: UInt = Reg(
      UInt(3 bit)
    )
    val awid_r: UInt = Reg(
      UInt(axi_config.idWidth bit)
    )
    when(data_in.aw.valid) {
      awaddr_r := data_in.aw.addr
      awlen_r := data_in.aw.len
      awsize_r := data_in.aw.size
      awid_r := data_in.aw.id
    }
  }.setName("aw_area")

  val addr_reg_map: ListBuffer[(Range, Data)] = ListBuffer[(Range, Data)]()

  def arrangeRegAddr(addr_reg_pair: (Range, Data)*): Unit = {
    addr_reg_map.appendAll(addr_reg_pair)
  }
  def arrangeRegAddr(addr_reg_pairs: Map[Range, Data]): Unit = {
    addr_reg_pairs.foreach(addr_reg_map.append(_))
  }

  val addr_mem_map: ListBuffer[(Range, Mem[_])] = ListBuffer[(Range, Mem[_])]()

  def arrangeMemAddr[T <: Data](addr_mem_pair: (Range, Mem[T])*): Unit = {
    addr_mem_map.appendAll(addr_mem_pair)
  }

  def wReady(set: Bool): Unit = data_in.w.ready := set

  def loadData() = new Area {
    val current_addr: UInt = Reg( UInt(axi_config.addressWidth bit) ) init 0
    when(data_in.w.valid) {
      // The burst assumption is INCR, len default to 16
      current_addr := Axi4.incr(aw_area.awaddr_r, data_in.aw.burst, aw_area.awlen_r, aw_area.awsize_r, byte_per_word)
      val wdata_r = RegNext(data_in.w.data)

      // write the input data into the register file
      // TODO: This block has not been verified yet.
      // No support for bit mask yet
      addr_reg_map foreach { case (range, reg) =>
        val addr_hit = (range.head <= current_addr) && (current_addr < range.last)
        reg match {
          case _: Bool => {
            SpinalInfo("match a bool")
            // default behavior: assign the last bit of data
            reg := addr_hit ? wdata_r(0) | reg.asBits(0)
          }
          case _: BitVector => {
            SpinalInfo("match the bit vector")
            val r = reg.asInstanceOf[BitVector]
            // default behavior: A address represent a word
            r := addr_hit ? wdata_r | r.asBits
          }
          case _: Vec[Data]@unchecked => { // Data is erased
            SpinalInfo("match Vec")
            val r = reg.asInstanceOf[Vec[Data]]
            // default behavior: A address of a register represent a word
            // WARNING: fanout maybe too large, restrict the size of the vector.
            val local_addr = ( current_addr - range.head ).resize(log2Up(r.getBitsWidth/word_bit_count))
            r(local_addr).assignFromBits(
              addr_hit ? wdata_r.asBits | r(local_addr).asBits
            )
          }
          case _ => SpinalError("Type wrong")
        }
      }

      // write the input data into the memory
      addr_mem_map foreach { case (range: Range, mem: Mem[Data]) =>
        val addr_hit = (range.head <= current_addr) && (current_addr < range.last)
        mem.write(
          address = ( current_addr - range.head ).resize(mem.addressWidth bit),
          data = data_in.w.data,
          enable = addr_hit
        )
      }
    }
  }.setWeakName("load_data_area")

  // unknown usage
  val b_area = new Area {
    data_in.b.valid := data_in.w.valid
    data_in.b.id := aw_area.awid_r
    data_in.b.resp := Axi4.resp.OKAY
  }.setName("b_area")

}
