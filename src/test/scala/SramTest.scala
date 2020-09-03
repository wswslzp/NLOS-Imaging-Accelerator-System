import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

object SramTest extends App{
  case class SramBB() extends Component {
    val io = new Bundle {
      val D = in UInt(32 bit)
      val Q = out UInt(32 bit)
      val ADR = in UInt(14 bit)
      val WE = in Bool()
      val ME = in Bool()
//      val LS = in Bool()
    }

    val int_mem = Mem(UInt(32 bit), 9152L)

    int_mem.generateAsBlackBox()

    io.Q := int_mem.readSync(
      address = io.ADR,
      enable = io.ME & (~io.WE)
    )

    int_mem.write(
      address = io.ADR,
      data = io.D,
      enable = io.ME & (~io.WE)
    )
//    when(io.ME === True) {
//      when(io.WE === False) {
//        // reading
//        io.Q := int_mem.readSync(
//          address = io.ADR
//        )
//      }
//    }

  }

  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(SramBB())

}
