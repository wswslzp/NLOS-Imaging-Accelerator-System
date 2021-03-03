package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._

import java.io.File

object MPortRamMain extends App{

  case class initModule() extends BlackBox {
    val ninit_done = out Bool()

  }

  case class MPortRam() extends Component {
    val io = new Bundle {
      val odat1 = out UInt(16 bit)
      val odat2 = out UInt(16 bit)

    }
    val init_module = initModule()

    val dut_clk = ClockDomain.internal("sys", ClockDomainConfig(resetActiveLevel = HIGH))
    dut_clk.clock := clockDomain.readClockWire
    dut_clk.reset := init_module.ninit_done | ~clockDomain.readResetWire

    val dut_clk_dom = new ClockingArea(dut_clk){
      val int_mem = Mem(UInt(16 bit), 64)
      int_mem.init(Seq.tabulate(64)(U(_, 16 bit)))

      val raddr1_cnt = CounterFreeRun(64)
      val raddr1 = raddr1_cnt.value
      val waddr = RegNext(raddr1)

      val mem_prev_data = int_mem.readSync(raddr1.resized)
      io.odat1 := mem_prev_data
      val mem_wdata = mem_prev_data + 1
      int_mem.write(waddr, mem_wdata)

      val cnt = CounterFreeRun(1024)
      val raddr2 = Delay(raddr1, 16, init = U(0, widthOf(raddr1) bit))
      val read_en = cnt.value > 768
      io.odat2 := int_mem.readSync(raddr2, read_en)
    }
  }

  new File("../rtl/MPortRam").mkdir()
  SpinalConfig(
    targetDirectory = "../rtl/MPortRam",
    headerWithDate = true,
    defaultConfigForClockDomains = ClockDomainConfig(
      resetActiveLevel = LOW
    )
  ).generateVerilog(MPortRam())

//  SimConfig
//    .withWave
//    .allOptimisation
//    .workspacePath("tb")
//    .compile(MPortRam())
//    .doSim("mport_ram_tb"){dut=>
//      dut.clockDomain.forkStimulus(2)
//      dut.clockDomain.waitSampling()
//
//      dut.clockDomain.waitSampling(4096)
//      simSuccess()
//    }
}
