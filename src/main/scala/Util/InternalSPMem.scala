package Util

import spinal.core._

trait InternalMemTrait extends Nameable {
  val data_width = 32
  val addr_width = 14
}

trait InternalSPMemTrait extends InternalMemTrait {
  val io = new Bundle {
    val CLK = in Bool()
    val Q = out UInt(data_width bit)
    val D = in UInt(data_width bit)
    val ADR = in UInt(addr_width bit)
    val RM = in UInt(4 bit)
    val WE = in Bool()
    val ME = in Bool()
    val RME = in Bool()
    val TEST1 = in Bool()
    val LS = in Bool()
  }
}

trait InternalDPMemTrait extends InternalMemTrait {
  val io_a = new Bundle {
    val CLKA = in Bool()
    val QA = out UInt(data_width bit)
    val DA = in UInt(data_width bit)
    val ADRA = in UInt(addr_width bit)
    val RMA = in UInt(4 bit)
    val WEA = in Bool()
    val MEA = in Bool()
    val RMEA = in Bool()
    val TEST1A = in Bool()
  }
  val io_b = new Bundle {
    val CLKB = in Bool()
    val QB = out UInt(data_width bit)
    val DB = in UInt(data_width bit)
    val ADRB = in UInt(addr_width bit)
    val RMB = in UInt(4 bit)
    val WEB = in Bool()
    val MEB = in Bool()
    val RMEB = in Bool()
    val TEST1B = in Bool()
  }
  val LS = in Bool()
}

case class InternalDPMemSim(
                           clocks: (ClockDomain, ClockDomain),
                           override val data_width: Int = 32,
                           override val addr_width: Int = 14
                           ) extends Component with InternalDPMemTrait {
  val int_sim_mem = Mem(UInt(data_width bit), ( 1 << addr_width ).toLong)

  clocks._1.clock <> io_a.CLKA
  clocks._2.clock <> io_b.CLKB

  val clock_area_1 = new ClockingArea(clocks._1){
    io_a.QA := int_sim_mem.readWriteSync(
      address = io_a.ADRA,
      data = io_a.DA,
      enable = io_a.MEA,
      write = io_a.WEA
    )
  }
  val clock_area_2 = new ClockingArea(clocks._2) {
    io_b.QB := int_sim_mem.readWriteSync(
      address = io_b.ADRB,
      data = io_b.DB,
      enable = io_b.MEB,
      write = io_b.WEB
    )
  }
}

case class InternalSPMemSim(
                         override val data_width: Int = 32,
                         override val addr_width: Int = 14
                         ) extends Component with InternalSPMemTrait {

  val clock = ClockDomain(io.CLK)

  val clock_area = new ClockingArea(clock) {
    val int_sim_mem = Mem(UInt(data_width bit), ( 1 << addr_width ).toLong)
    io.Q := int_sim_mem.readWriteSync(
      address = io.ADR,
      data = io.D,
      enable = io.ME,
      write = io.WE
    )
  }
}

case class InternalSPMemBB(
                      sim_model_file: String = "../tmp/sram_tb/rtl/fft2d9k32bit8bank_fast_func.v",
                      override val data_width: Int = 32,
                      override val addr_width: Int = 14
                      ) extends BlackBox with InternalSPMemTrait {
  noIoPrefix()
  mapCurrentClockDomain(clock = io.CLK)
  addRTLPath(sim_model_file)
  setDefinitionName("fft2d9k32bit8bank")

}

case class InternalDPMemBB(
                          clocka: ClockDomain,
                          clockb: ClockDomain,
                          sim_model_file: String = "../tmp/sram_tb/rtl/fft2d9k32bit8bank_fast_func.v",
                          override val data_width: Int = 32,
                          override val addr_width: Int = 14
                        ) extends BlackBox with InternalDPMemTrait {
  noIoPrefix()
  mapClockDomain(clocka, clock = io_a.CLKA)
  mapClockDomain(clockb, clock = io_b.CLKB)
  addRTLPath(sim_model_file)
  setDefinitionName("fft2d9k32bit8bank")

}

object InternalMem {
  def apply(data_width: Int, addr_width: Int)(implicit synthesizable: Synthesizable): InternalSPMemTrait = {
    if (synthesizable.flag) InternalSPMemBB( data_width = data_width, addr_width = addr_width ).setWeakName("mem_bb")
    else InternalSPMemSim(data_width, addr_width).setWeakName("mem_sim")
  }

  def apply(clocks: (ClockDomain, ClockDomain), data_width: Int, addr_width: Int)(implicit synthesizable: Synthesizable): InternalDPMemTrait = {
    if (synthesizable.flag) InternalDPMemBB(clocks._1, clocks._2, data_width = data_width, addr_width = addr_width).setWeakName("mem_bb")
    else InternalDPMemSim(clocks, data_width, addr_width).setWeakName("mem_sim")
  }

//  def apply(data_width: Int, addr_width: Int)(implicit synthesizable: Synthesizable): InternalSPMem = {
//    if (synthesizable.flag) InternalSPMem(InternalSPMemBB())
//    else InternalSPMem(InternalSPMemSim())
//  }
}

