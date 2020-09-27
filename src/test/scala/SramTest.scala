import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._

object SramTest extends App{
  case class SramBB() extends Component {
    val io = new Bundle {
      val din = slave(Stream(UInt(32 bit)))
      val dout = master(Stream(UInt(32 bit)))
    }

    val memcfg = MemManager.MemConfig(
      dw = 32, aw = 32, vendor = Huali
    )

    val int_mem = Ram1rw(memcfg)
    int_mem.io.ap.bwe := B(int_mem.mc.dw bit, default->True)

    when(io.din.valid) {
      int_mem.io.ap.cs := True
      int_mem.io.dp.we := True
      int_mem.io.ap.addr := io.din.payload ^ (io.din.payload |>> 1)
      int_mem.io.dp.din := io.din.payload.asBits
    } otherwise {
      int_mem.io.ap.cs := False
      int_mem.io.dp.we := False
      int_mem.io.ap.addr := 0
      int_mem.io.dp.din := 0
    }
    val prev_addr = RegNextWhen(
      io.din.payload ^ (io.din.payload |>> 1), cond = io.din.valid
    )

    val ovalid = Delay(io.din.valid, 3, init = False)
    val fire = ovalid & io.dout.ready

    when(fire) {
      int_mem.io.ap.cs := True
      int_mem.io.dp.we := False
      int_mem.io.ap.addr := prev_addr
      io.dout.payload := int_mem.io.dp.dout.asUInt
    } otherwise {
      int_mem.io.ap.cs := False
      int_mem.io.dp.we := False
      int_mem.io.ap.addr := 0
      io.dout.payload := int_mem.io.dp.dout.asUInt
    }

    io.dout.valid := ovalid
    io.din.ready := io.dout.ready
  }

  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(SramBB())

}
