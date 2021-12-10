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
trait Axi4Slave extends Nameable {
  // a abstract config of axi4
  val axi_config: Axi4Config
  val word_bit_count: Int
  val byte_per_word: Int = word_bit_count / 8

  val data_in = slave(
//    Axi4(axi_config)
    Axi4WriteOnly(
      axi_config
    )
  ).setName("axi4")

  protected def awReady(set: Bool): Unit = data_in.aw.ready := set

  val aw_area = new Area {
    val awaddr_r = RegNextWhen(data_in.aw.addr, data_in.aw.valid) init 0
    val awlen_r = RegNextWhen(data_in.aw.len, data_in.aw.valid) init 0
    val awsize_r = RegNextWhen(data_in.aw.size, data_in.aw.valid) init 0
    val awid_r = RegNextWhen(data_in.aw.id, data_in.aw.valid) init 0
    val awburst_r = RegNextWhen(data_in.aw.burst, data_in.aw.valid) init 0
  }.setName("aw_area")

  val addr_reg_map: ListBuffer[(Range, Data)] = ListBuffer[(Range, Data)]()

  protected def arrangeRegAddr(addr_reg_pair: (Range, Data)*): Unit = {
    addr_reg_map.appendAll(addr_reg_pair)
  }
  protected def arrangeRegMapAddr(addr_reg_pairs: Map[Range, Data]*): Unit = {
    addr_reg_pairs.foreach { map=>
      map.foreach(addr_reg_map.append(_))
    }
  }

  val addr_mem_map: ListBuffer[(Range, Mem[_])] = ListBuffer[(Range, Mem[_])]()

  protected def arrangeMemAddr[T <: Data](addr_mem_pair: (Range, Mem[T])*): Unit = {
    addr_mem_map.appendAll(addr_mem_pair)
  }

  protected def wReady(set: Bool): Unit = data_in.w.ready := set

  // TODO: partially verified
  protected def loadData() = new Area {
    val wvalid = RegNext(data_in.w.valid) init False
    val wlast = RegNext(data_in.w.last) init False
    val incr = Counter(0 until 16)
    val transaction_first_addr = Axi4.incr(aw_area.awaddr_r, aw_area.awburst_r, aw_area.awlen_r, aw_area.awsize_r, byte_per_word)
    val current_addr: UInt = incr.value + transaction_first_addr -1

    when(wvalid) {
      // The burst assumption is INCR, len default to 16
      incr.increment()
      when(wlast) {
        incr.clear()
      }

      val wdata_r = RegNext(data_in.w.data).setWeakName("wdata_r")

      // write the input data into the register file
      // No support for bit mask yet
      // the reg's width must be less than the data bus width
      // TODO: addr_hit incur the high fanout of the current_addr, damage the timing!!
      addr_reg_map foreach { case (range, reg) =>
//        val single_addr = range.head == range.last
        val addr_hit = (range.head <= current_addr) && (current_addr <= range.last)// || ((current_addr === range.head) && Bool(single_addr))
        addr_hit.setWeakName("addr_hit")
        reg match {
          case _: Bool => //{
            // default behavior: assign the last bit of data
            reg := addr_hit ? wdata_r(0) | reg.asBits(0)
//          }
          case _: BitVector => //{
            require(
              reg.getBitsWidth <= axi_config.dataWidth,
              s"the reg's width ${reg.getBitsWidth} is larger than data bus width ${axi_config.dataWidth}"
            )
            // default behavior: low range of the wdata will assign to reg
            val tmp_data = (addr_hit ? wdata_r | reg.asBits.resized).resize(reg.getBitsWidth)
            reg.assignFromBits(tmp_data)
//          }
          case _ => SpinalError(s"reg: ${reg.getName()} has the wrong type--${reg.toString()}, other than Bool or BitVector")
        }
      }

      // write the input data into the memory
      addr_mem_map foreach { case (range: Range, mem: Mem[Data]) =>
        val addr_hit = (range.head <= current_addr) && (current_addr <= range.last)
        addr_hit.setWeakName("addr_hit")
        mem.write(
          address = ( current_addr - range.head ).resize(mem.addressWidth bit),
          data = wdata_r.resize(mem.width),
          enable = addr_hit
        )
      }
    }
  }.setWeakName("load_data_area")

  protected def exposeData() = new Area {
    ???
  }

  val b_area = new Area {
    data_in.b.valid := data_in.w.valid
    data_in.b.id := aw_area.awid_r
    data_in.b.resp := Axi4.resp.OKAY
  }.setName("b_area")

  protected def printAddrRange: Unit = {
    import java.io._
    var s = ""
    val file = new PrintWriter(new File(s"${super.getName()}_addr.txt"))
    addr_reg_map foreach {
      pair => s += s"${pair._2.getName()}:\t\t ${pair._1.head} -> ${pair._1.last}\n"
    }
    addr_mem_map foreach {
      pair => s += s"${pair._2.getName()}:\t\t ${pair._1.head} -> ${pair._1.last}\n"
    }
    file.write(s)
    file.close()
  }

//  component.addPrePopTask(() => loadData())

}
