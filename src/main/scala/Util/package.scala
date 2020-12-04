import Config.HComplexConfig
import spinal.core._
import spinal.lib._

package object Util {

  def countUpFrom(cond: Bool, range: Range, name: String = "", step: Int=1) = new Area {
    // cond is a one-cycle impulse, when the cond is active, counter inside will
    // count up from cond's falling edge to a specific number(0 until x)
    // useful tool for scheduling the task.
//    val cnt: Counter = Counter(range).setName(name + "_cnt")
    val cnt: Counter = Counter(range).setCompositeName(cond, name, weak = true)
    val cond_period_minus_1: Bool = Reg(Bool()) init(False)
//    cond_period_minus_1.setName(name + "_cond_period_minus_1")
    cond_period_minus_1.setCompositeName(cond, name+"_cond_period_minus_1", weak = true)
    when(cond) {
      cond_period_minus_1 := True
    }.elsewhen(cnt.willOverflow) {
      cond_period_minus_1 := False
    }
    val cond_period: Bool = cond | cond_period_minus_1
//    cond_period.setName(name + "_cond_period")
    cond_period.setCompositeName(cond, name+"_cond_period")
    when(cond_period) {
      // TODO: the counter in spinal lib is not support step increment
      // DO NOT USE delta factor
      for(_ <- 0 until step) {cnt.increment()}
    }/*.otherwise{
    cnt.clear()
  }*/
  }

//  def countUpFrom(cond: Bool, length: Int, name: String = "") = countUpFrom(cond, 0 until length, name)

  def countUpInside(cond: Bool, length: Int, name: String = "") = new Area {
//    val cnt = Counter(0, length-1).setName(s"${name}_cnt")
    val cnt = Counter(0, length-1).setCompositeName(cond, name, weak = true)
    when(cond) {
      cnt.increment()
    }
    val last = cnt.willOverflow
//    last.setName(s"${name}_last")
    last.setCompositeName(cond, name+"_last")
  }

  def countLtUInt(cond: Bool, stop: UInt): UInt =  {
    // cond is wvalid, and start is the initial address, stop is the initial address + (2^awlen)
    val cnt = Reg(cloneOf(stop)) init 0
    when(cond) {
      when(cnt < stop) {
        cnt := cnt + 1
      } otherwise {
        cnt := 0
      }
    } otherwise {
      cnt := 0
    }
    cnt
  }

  def complexScale(data: UInt, log2factor: Int): UInt = {
    val width = data.getWidth
    val data_real = data(width-1 downto width/2).asSInt |>> log2factor
    val data_imag = data(width/2-1 downto 0).asSInt |>> log2factor
    ( data_imag @@ data_real ).asUInt
  }

  def readmemh(path: String): Array[BigInt] = {
    import scala.collection.mutable.ArrayBuffer
    import scala.io.Source
    val buffer = new ArrayBuffer[BigInt]
    for (line <- Source.fromFile(path).getLines) {
      val tokens: Array[String] = line.split("(//)").map(_.trim)
      if (tokens.length > 0 && tokens(0) != "") {
        val i = Integer.parseInt(tokens(0), 16)
        buffer.append(i)
      }
    }
    buffer.toArray
  }

  def readmemb(path: String): Array[BigInt] = {
    import scala.collection.mutable.ArrayBuffer
    import scala.io.Source
    val buffer = new ArrayBuffer[BigInt]
    for (line <- Source.fromFile(path).getLines) {
      val tokens: Array[String] = line.split("(//)").map(_.trim)
      if (tokens.length > 0 && tokens(0) != "") {
        val i = Integer.parseInt(tokens(0), 2)
        buffer.append(i)
      }
    }
    buffer.toArray
  }

  object Conjugate {
    def apply(data: UInt, imag_range: Range): UInt = {
      val real_range = imag_range.end-1 downto 0
      val result = cloneOf(data)
      result := (U(0, 16 bit) @@ data(real_range) ) | ( (~data(imag_range) + U(1)).resized @@ U(0, 16 bit) )
      result
    }
  }

  def expfunclut(cfg: HComplexConfig, point: Int, data_range: (Double, Double)): (Vector[SFix], Vector[HComplex]) = {
    val func_table = Vector.fill(point)(HComplex(cfg))
    val range = (1 to point).toVector.map { p =>
      val idx = data_range._1 + (data_range._2 - data_range._1) * (p.asInstanceOf[Double] / point)
      idx
    }
    val indx_table = range.map { dat =>
      SF(dat, cfg.intw-1 exp, -cfg.fracw exp)
    }
    val output_cos_range = range.map { dat =>
      val cos_v = Math.cos(2 * Math.PI * dat)
//      SpinalInfo(s"cos($dat) = $cos_v")
      cos_v
    }.map(SF(_, cfg.intw-1 exp, -cfg.fracw exp))
    val output_sin_range = range.map { dat =>
      val sin_v = scala.math.sin(2 * scala.math.Pi * dat)
//      SpinalInfo(s"sin($dat) = $sin_v")
      sin_v
    }.map(SF(_, cfg.intw-1 exp, -cfg.fracw exp))
    for(i <- 0 until point) {
      func_table(i).real := output_cos_range(i)
      func_table(i).imag := output_sin_range(i)
    }
    indx_table -> func_table
  }

  def expfunclutInPeriod(cfg: HComplexConfig, sample_point: Int, period: Double): (Vector[SFix], Vector[HComplex]) = {
    val data_range = (0d, period)
    expfunclut(cfg, sample_point, data_range)
  }

  // resource-consuming!!
  def linearInterpolate(x: SFix, x1: SFix, x2: SFix, y1: HComplex, y2: HComplex): HComplex = {
    val xd = ( x1 - x2 ).setWeakName("xd")
    val yd = ( y1 - y2 ).setWeakName("yd")
    val k = ( yd / xd ).setWeakName("k")
    val deltax = HC(x - x1).setWeakName("deltax")
    k * deltax + y1
  }

  def nearestInterpolate(x: SFix, x1: SFix, x2: SFix, y1: HComplex, y2: HComplex): HComplex = {
    val mid_x = (x1 + x2) >> 1
    val x1_lt_x2 = x1 < x2
    (~(x1_lt_x2 ^ (x < mid_x))) ? y1 | y2
  }

  def meanApprox(y1: HComplex, y2: HComplex): HComplex = (y1 + y2) >> 1

  def simpleCountLeadingZeros(x: BitVector): UInt = {
    // This is the simple version of counting leading zeros of bit vector
    // whose pattern has leading continuous zeros and trailing continuous ones, like 00001111.
    // The hardware structure is composed of an adder tree.
    val x_inv = ~x.asBits
    val dw = x_inv.getBitsWidth
    val ret = UInt(log2Up(dw)+1 bit).setWeakName("ret")
    val xbits_padding = (0 until x_inv.getBitsWidth).toVector.map(x_inv(_).asUInt(ret.getWidth bit)) ++ Vector.fill(( 1<<log2Up(dw) )-dw)(U(0, ret.getWidth bit))
    val adder_tree = Vector.fill(xbits_padding.length-1)(UInt(log2Up(dw)+1 bit))
    var bg = 0 // bg(L-1)
    for(level <- 0 until log2Up(xbits_padding.length)) {
      if (level == 0) {
        for (idx <- 0 until xbits_padding.length/2) {
          adder_tree(idx) := xbits_padding(2*idx) + xbits_padding(2*idx + 1)
        }
      }
      else {
        val incr = xbits_padding.length / (1 << level) // incr(L-1)
        for (idx <- 0 until incr/2) {
          adder_tree(bg + incr + idx) := adder_tree(bg + 2*idx) + adder_tree(bg + 2*idx + 1)
        }
        bg += incr
      }
    }
    adder_tree.last
  }

  // No resource reuse, multiple and gates are duplicated
  def countLeadingZeros(x: BitVector): UInt = {
    val dw = x.getBitsWidth
    val ret = UInt(log2Up(dw)+1 bit).setWeakName("ret")
    val ld = (1 << log2Up(dw)) - dw
    val x_tmp = ( x ## B(ld bit, default -> True) ).setWeakName("x_tmp")
    val dw_t = x_tmp.getBitsWidth
    val all_zeros = Bits(ret.getBitsWidth bit).setAllTo(~x_tmp.orR).asUInt.setWeakName("all_zeros")
    val all_left_zeros = Bits(ret.getBitsWidth bit).setAllTo(~x_tmp(dw_t-1 downto dw_t/2).orR).asUInt.setWeakName("all_left_zeros")

    if (dw == 1) {
      U(~x(0))
    } else {
      val whole_bit_count = U(dw_t, ret.getBitsWidth bit).setWeakName("whole_bit_count")
      val left_bit_count = U(dw_t/2, ret.getBitsWidth bit).setWeakName("left_bit_count")
      val left_all_zeros_count = ( all_left_zeros & (
        left_bit_count + countLeadingZeros( x_tmp(dw_t/2-1 downto 0) ).resized
      ) ).setWeakName("left_all_zeros_count")
      val left_non_all_zeros_count = ( ~all_left_zeros & (
        countLeadingZeros( x_tmp(dw_t-1 downto dw_t/2) ).resized
      ) ).setWeakName("left_non_all_zeros_count")
      val non_zeros_lzc = ( left_all_zeros_count + left_non_all_zeros_count ).setWeakName("non_zeros_lzc").resized
      ret := ( ( all_zeros & whole_bit_count ) + ( ~all_zeros & non_zeros_lzc ) ).resized
      ret
    }

  }

  def countLeadingZeros(x: Seq[Bool]): UInt = {
    require(x.nonEmpty)
    countLeadingZeros(B(x))
  }

}
