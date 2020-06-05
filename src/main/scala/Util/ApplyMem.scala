package Util

import Config.HComplexConfig
import spinal.core._
import spinal.lib.bus.amba4.axi.Axi4Config

import scala.collection.mutable

// init_addr: the initial address of the module
// word_count: the bit count in a word
case class ApplyMem(init_addr: Int, word_count: Int) {
  private var acc_addr = init_addr
  private val addr_range_record = mutable.Map.empty[Range, String]
  private val countWordsNum = (d: Int) => Math.ceil(d.toDouble / word_count).toInt

  def allocateBaseReg[T <: BaseType](data_type: T, name: String): (Range, T) = {
    val regs = if (data_type.isReg) {
      data_type.setWeakName(name)
    } else { Reg(data_type).setWeakName(name) }
    val width = regs.getBitsWidth
    // TODO: the address's increment (width / word_count) ??
    val words_num_per_word = countWordsNum(width)
    val addr_range = acc_addr until acc_addr + words_num_per_word// width/word_count + 1
    addr_range_record += addr_range -> regs.getName()
    acc_addr += words_num_per_word
    addr_range -> regs
  }

  def allocateBaseReg[T <: BaseType](dataType: T): (Range, T) = allocateBaseReg(dataType, "")

  // TODO: need verification
  def allocateMultiReg[T <: MultiData](data_type: T, name: String): (Map[Range, BaseType], T) = {
    // MultiData has elements with different data type inside
    // arrange the address in the order of the elements' definition in the Bundle
    var ret_regaddr_map: mutable.Map[Range, BaseType] = mutable.Map[Range, BaseType]()
    val ret_multidata = if (data_type.isReg) { data_type.setWeakName(name) } else { Reg(data_type).setWeakName(name) }
    // extract all the elements inside, elements may be baseType or MultiData!!
    val elements = ret_multidata.elements.map(_._2).toVector
    elements.foreach(println(_))
    elements.foreach {
      case baseType: BaseType => {
        // if the element is base type, then arrange a address for it
        SpinalInfo(s"${baseType.getName()} match BaseType")
        val addr_reg_pair = allocateBaseReg(baseType/*.asInstanceOf[R]*/, name)
        SpinalInfo(s"${baseType.getName()}'s addr_reg_pair = ${addr_reg_pair.toString()}")
        ret_regaddr_map += addr_reg_pair
      }
      case multiData: MultiData => {
        // if not, just recursively do it
        SpinalInfo(s"${multiData.getName()} match MultiData")
        val addr_reg_pair = allocateMultiReg(multiData, name)._1
        SpinalInfo(s"${multiData.getName()}'s addr_reg_pair = ${addr_reg_pair.toString()}")
        ret_regaddr_map ++= addr_reg_pair
      }
      case _ => SpinalError("Incorrect type")
    }
    ret_regaddr_map.toMap -> ret_multidata
  }

  def allocateMultiReg[T <: MultiData, R <: BaseType](dataType: T): (Map[Range, BaseType], T) = allocateMultiReg(dataType, "")

  def allocateReg[T <: Data](data: T, name: String = ""): (Map[Range, BaseType], T) = {
    val ret_map = mutable.Map[Range, BaseType]()
    data match {
      case baseType: BaseType => {
        val m: (Range, BaseType) = allocateBaseReg(baseType, name)
        ret_map += m
        ret_map.toMap -> m._2.asInstanceOf[T]
      }
      case multiData: MultiData => {
        val m = allocateMultiReg(multiData, name)
        ret_map ++= m._1
        ret_map.toMap -> m._2.asInstanceOf[T]
      }
    }
  }

  def allocateRegArray[T <: Data](reg_array: Seq[T], name: String):( Map[Range, T] , Seq[T]) = {
    val ret_seq = reg_array.map(Reg(_))
    val ret_addr_regs = mutable.Map.empty[Range, Data]
    reg_array foreach {
      case baseType: BaseType => ret_addr_regs += allocateBaseReg(baseType, name) // Enum, Bool, Bits, UInt, SInt
      case multiData: MultiData => ret_addr_regs ++= allocateMultiReg(multiData, name)._1 // Vec, Bundle, UFix, SFix
      case _ => SpinalError("Incorrect type")
    }
    ret_addr_regs.map(p => p._1 -> p._2.asInstanceOf[T]).toMap -> ret_seq
  }

  def allocateRegArray[T <: Data](reg_array: Seq[T]):( Map[Range, T] , Seq[T]) = allocateRegArray(reg_array, "")

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

object ApplyMemMain extends App {

  case class Amodule(override val axi_config: Axi4Config) extends Component with AXI4WLoad {
    import spinal.lib._
    import spinal.lib.graphic._
    override val word_bit_count: Int = 32
    val b = out ( HComplex(HComplexConfig(8, 8)) )
    val vb = out ( Rgb(8, 8, 8) )

    awReady(True)
    wReady(True)

    val a = HComplex(b.config)
    val va = cloneOf(vb)
    val local_mem_manager = ApplyMem(0, a.config.getDataWidth)

    val (a_map, a_reg) = local_mem_manager.allocateReg(a, "a")
    val (va_map, va_reg) = local_mem_manager.allocateReg(va, "va")

    println(local_mem_manager.addrRange)

    arrangeRegMapAddr(a_map, va_map)
    loadData()

    b := a_reg
    vb <> va_reg
  }

  val axi_cfg = Axi4Config(32, 32, 4)
  SpinalConfig(targetDirectory = "tb/Amodule_tb/rtl").generateVerilog(Amodule(axi_cfg))
}

