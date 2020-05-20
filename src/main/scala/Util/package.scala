import spinal.core._
import spinal.lib._

package object Util {

  def countUpFrom(cond: Bool, range: Range, name: String = "") = new Area {
    // cond is a one-cycle impulse, when the cond is active, counter inside will
    // count up from cond's falling edge to a specific number(0 until x)
    // useful tool for scheduling the task.
    val cnt: Counter = Counter(range)
    val cond_period_minus_1: Bool = Reg(Bool()) init(False)
    when(cond) {
      cond_period_minus_1 := True
    }.elsewhen(cnt.willOverflow) {
      cond_period_minus_1 := False
    }
    val cond_period: Bool = cond | cond_period_minus_1
    when(cond_period) {
      cnt.increment()
    }/*.otherwise{
    cnt.clear()
  }*/
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

}
