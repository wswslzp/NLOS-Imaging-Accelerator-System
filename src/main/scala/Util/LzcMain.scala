package Util
import spinal.core._

object LzcMain extends App{
  class lzc extends Component {
    val a = in Bits(20 bit)
    val a_lzc = out UInt(20 bit)

    a_lzc := simpleCountLeadingZeros(a).resized
    GenerationFlags.synthesis {

    }
    GenerationFlags.simulation {

    }
    GenerationFlags.formal {

    }
  }
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "../tmp/lzc_tb/rtl"
  ).generate(
    new lzc
  )
}
