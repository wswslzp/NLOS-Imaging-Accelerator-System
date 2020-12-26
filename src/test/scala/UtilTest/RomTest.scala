package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._

import java.io.File

object RomTest extends App{
  case class RomToy() extends Component {
    val io = new Bundle {
      val addr = in UInt(8 bit)
      val data = out UInt(8 bit)
    }

    val rom = Mem(
      Seq.tabulate(128){i=>
//        B(2*i, 8 bit).asUInt
//        U(B(2*i, 8 bit))
        B(BigInt( -2*i ), 8 bit)
      }
    )

    io.data := rom.readSync(io.addr.resized).asUInt
  }

  new File("rtl/RomToy").mkdir()

  SpinalConfig(
    targetDirectory = "rtl/RomToy"
  ).generateVerilog(RomToy())
}
