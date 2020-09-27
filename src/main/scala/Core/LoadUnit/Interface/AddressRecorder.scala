package Core.LoadUnit.Interface

import spinal.core._

import scala.collection.mutable.ListBuffer

trait AccessTag
trait WriteOnly extends AccessTag
trait ReadOnly extends AccessTag
trait ReadAndWrite extends AccessTag

class DataWithTag[T <: Data](val _data: T)
case class DataWithRO[T <: Data](data: T) extends DataWithTag(data) with ReadOnly
case class DataWithWO[T <: Data](data: T) extends DataWithTag(data) with WriteOnly
case class DataWithRW[T <: Data](data: T) extends DataWithTag(data) with ReadAndWrite

class AddressRecorder(word_bit_count: Int) /*extends Nameable*/ {

  val byte_per_word: Int = word_bit_count / 8

  val addr_reg_map: ListBuffer[(Range, DataWithTag[_])] = ListBuffer[(Range, DataWithTag[_])]()
  val addr_mem_map: ListBuffer[(Range, Mem[_])] = ListBuffer[(Range, Mem[_])]()

  def arrangeRegAddr(addr_reg_pair: (Range, DataWithTag[_])*): Unit = {
    addr_reg_map.appendAll(addr_reg_pair)
  }

  def arrangeRegMapAddr(addr_reg_pairs: Map[Range, DataWithTag[_]]*): Unit = {
    addr_reg_pairs.foreach { map=>
      map.foreach(addr_reg_map.append(_))
    }
  }

  def arrangeMemAddr[T <: Data](addr_mem_pair: (Range, Mem[T])*): Unit = {
    addr_mem_map.appendAll(addr_mem_pair)
  }

//  def printAddrRange(): Unit = {
//    import java.io._
//    var s = ""
//    val file = new PrintWriter(new File(s"${super.getName()}_addr.txt"))
//    addr_reg_map foreach {
//      pair => {
//        val name = pair._2._data.asInstanceOf[Data].name
//        s += s"$name:\t\t ${pair._1.head} -> ${pair._1.last}\n"
//      }
//    }
//    addr_mem_map foreach {
//
//      pair => s += s"${pair._2.getName()}:\t\t ${pair._1.head} -> ${pair._1.last}\n"
//    }
//    file.write(s)
//    file.close()
//  }
//

}
