import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import scala.util.Random._

object SramTest extends App{
  case class SramBB() extends Component {
    val io = new Bundle {
      val ad_sw = in Bool()
      val din = slave(Stream(UInt(32 bit)))
      val dout = master(Stream(UInt(32 bit)))
    }

    val memcfg = MemManager.MemConfig(
      dw = 32, aw = 14, vendor = Huali, name = "fft2d9k32bit8bank", needBwe = false
    )

    val int_mem = Ram1rw(memcfg)
    int_mem.addSimulationModel("tmp/simtmp/fft2d9k32bit8bank_ikos.v")

    val addr_data_sw = RegNext(io.ad_sw, init = True)

    when(io.din.fire){
      // start write int mem
      when(addr_data_sw) {
        // write address to mem
        int_mem.io.ap.addr := io.din.payload(8 downto 0).resized
        int_mem.io.ap.cs := True
        int_mem.io.dp.we := True
        int_mem.io.dp.din := io.din.payload.asBits
        io.dout.valid := False
        io.dout.payload := 0
      } otherwise {
        // write data to mem
        int_mem.io.ap.addr := io.din.payload(8 downto 0).resized
        int_mem.io.ap.cs := True
        int_mem.io.dp.we := False
        int_mem.io.dp.din := 0
        io.dout.valid := True
        io.dout.payload := int_mem.io.dp.dout.asUInt
      }
    }otherwise{
      int_mem.io.ap.addr := 0
      int_mem.io.dp.din := 0
      int_mem.io.ap.cs := False
      int_mem.io.dp.we := False
      io.dout.valid := False
      io.dout.payload := 0
    }

    io.din.ready := io.dout.ready

  }

  SimConfig
    .withWave
    .allOptimisation
    .addIncludeDir("tmp/simtmp")
    .addSimulatorFlag("--trace-underscore")
    .addSimulatorFlag("--bbox-unsup")
    .workspacePath("tb")
    .compile(SramBB())
    .doSim("SramBB_tb"){dut=>
      dut.clockDomain.forkStimulus(2)
      dut.io.din.valid #= false
      dut.io.dout.ready #= true
      dut.io.ad_sw #= true

      dut.clockDomain.waitSampling()

      val random_data = Array.tabulate(10){_ =>
        val d = nextInt(10)
        Math.max(
          d, -d
        )
      }
      for(d <- 0 until 10) {
        dut.io.din.payload #= random_data(d)
        dut.io.din.valid #= true
        dut.clockDomain.waitSampling()
      }

      dut.io.ad_sw #= false
      dut.clockDomain.waitSampling()
      for(d <- 0 until 10) {
        dut.io.din.payload #= random_data(d)
        dut.io.din.valid #= true
        dut.clockDomain.waitSampling()
      }

      dut.clockDomain.waitSampling(30)
      simSuccess()

    }

}
