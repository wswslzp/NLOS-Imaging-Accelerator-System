package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Fpga.IP._

import java.io.File

object IpMain extends App{
  case class ToyCore() extends Component {
    val io = new Bundle {
      val din = slave(Stream(UInt(8 bit)))
      val dout = master(Stream(UInt(8 bit)))
    }

    io.dout <-/< io.din.translateWith(io.din.payload +| io.din.payload << 2)
  }

  case class Toy() extends Component {
    val io = new Bundle {
      val din = slave(Stream(UInt(8 bit)))
      val dout = master(Stream(UInt(8 bit)))
    }

    // Define a clock outside of chip, with clock name "ref_clk" and no reset.
    val ref_clk_domain = ClockDomain.external("ref", withReset = false)
    val iopll = IOPLL()
    iopll.refclk := ref_clk_domain.clock

    val por = POR()

    // define system clock
    val sys_clk_domain = ClockDomain.internal("sys")
    sys_clk_domain.clock := iopll.outclk_0
    sys_clk_domain.reset := por.ninit_done

    val sys_clk_area = new ClockingArea(sys_clk_domain){
      val toy_core = ToyCore()
      toy_core.io.din << io.din
      toy_core.io.dout >> io.dout
    }

  }

  new File("rtl/toy").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/Toy"
  ).generateVerilog(Toy())
}
