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

  def allocateBaseReg[T <: BaseType](data_type: T, name: String): (Map[Range, T], T) = {

    require(!data_type.isInput, s"$data_type is input port")
    val nameReg = (reg: T, name: String) => {
      if (name.isEmpty) reg
      else reg.setWeakName(name)
    }
    val ret_map = mutable.Map[Range, T]()

    // add reg to data if the data is not a reg
    val ret_reg = if (data_type.isReg) {
      nameReg(data_type, name)
    } else {
      nameReg(Reg(data_type), name)
    }

    // get the bit width of the data
    val width = ret_reg.getBitsWidth

    // count the total word num of the data
    val words_num_per_word = countWordsNum(width)

    // determine the address range according to the word num of the data
    if (words_num_per_word > 1) {
      require(width % word_count == 0, s"multi-word data width($width bit) must align with the word_count($word_count bit)")
      val reg = ret_reg.asInstanceOf[BitVector]
      for {
        wn <- 0 until words_num_per_word
      } {
        // get the address of the reg
        val addr_range = acc_addr until acc_addr + 1
        acc_addr += 1

        // get the slice of the ret_reg and arrange the address for it
        val reg_slice_range = (wn + 1)*word_count-1 downto wn*word_count
        val reg_slice = reg(reg_slice_range).asInstanceOf[T]
        ret_map += addr_range -> reg_slice

        // record the address range
        addr_range_record += addr_range -> reg_slice.getName()
      }
    }
    else {
      val addr_range = acc_addr until acc_addr + 1// width/word_count + 1
      ret_map += addr_range -> ret_reg
      addr_range_record += addr_range -> ret_reg.getName()
      acc_addr += words_num_per_word
    }

    ret_map.toMap -> ret_reg
  }

  def allocateBaseReg[T <: BaseType](dataType: T): (Map[Range, T], T) = allocateBaseReg(dataType, "")

  def allocateMultiReg[T <: MultiData](data_type: T, name: String): (Map[Range, BaseType], T) = {
    // MultiData has elements with different data type inside
    // arrange the address in the order of the elements' definition in the Bundle
    var ret_regaddr_map: mutable.Map[Range, BaseType] = mutable.Map[Range, BaseType]()
    val ret_multidata = if (data_type.isReg) { data_type.setWeakName(name) } else { Reg(data_type).setWeakName(name) }

    // extract all the elements inside, elements may be baseType or MultiData!!
    val elements = ret_multidata.elements.map(_._2).toVector
    elements.foreach {

      case baseType: BaseType => {
        // if the element is base type, then arrange a address for it
        val addr_reg_pair = allocateBaseReg(baseType, name)
        ret_regaddr_map ++= addr_reg_pair._1
      }

      case multiData: MultiData => {
        // if not, just recursively do it
        val addr_reg_pair = allocateMultiReg(multiData, name)._1
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
        val m = allocateBaseReg(baseType, name)
        ret_map ++= m._1
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
    val ret_map = mutable.Map[Range, T]()
    val ret_reg = mutable.ListBuffer[T]()
    reg_array.zipWithIndex.foreach { case (reg, idx) =>
      val (reg_map, reg_inst) = if(!name.isEmpty) {
        allocateReg(reg, s"${name}_$idx")
      } else {
        allocateReg(reg)
      }
      ret_map ++= reg_map.map(p => p._1 -> p._2.asInstanceOf[T])
      ret_reg.append(reg_inst)
    }
    ret_map.toMap -> ret_reg
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
    val b = out ( HComplex(HComplexConfig(16, 16)) )
    val vb = out ( Rgb(8, 8, 8) )
    val c = out (Bits(128 bit))
    val d = out (Vec(HComplex(8, 8), 32))
    val e = out Bool

    awReady(True)
    wReady(True)

    val a = HComplex(b.config)
    val va = cloneOf(vb)
    val vd = Vector.fill(32)(HComplex(8, 8))
    val local_mem_manager = ApplyMem(0, a.config.getDataWidth)

    val (a_map, a_reg) = local_mem_manager.allocateReg(a, "a")
    val (va_map, va_reg) = local_mem_manager.allocateReg(va, "va")
    val (c_map, c_reg) = local_mem_manager.allocateReg(cloneOf(c), "c")
    val (d_map, d_reg) = local_mem_manager.allocateRegArray(vd, "d")

    println(local_mem_manager.addrRange)

    arrangeRegMapAddr(a_map, va_map, c_map, d_map)
    loadData()

    c := c_reg
    b := a_reg
    vb <> va_reg
    d_reg.zipWithIndex.foreach { case(dat, idx) =>
      d(idx) := dat
    }

    val awvalid_r = Delay(data_in.aw.valid, 8)
    e := awvalid_r

    try {
      println(
        s"the lat is ${LatencyAnalysis(data_in.aw.valid, awvalid_r)}"
      )
    } catch {
      case e: Exception => println("no path")
    }

  }

  val axi_cfg = Axi4Config(32, 32, 4)
  SpinalConfig(targetDirectory = "tb/Amodule_tb/rtl").generateVerilog(Amodule(axi_cfg))
}

