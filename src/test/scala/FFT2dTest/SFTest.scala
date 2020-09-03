package FFT2dTest

import spinal.core._
import spinal.lib._
import spinal.lib.cpu.riscv.impl.{RiscvCore, RiscvCoreConfig}

object SFTest extends App{
  case class SFT() extends Component {
//    val in1 = slave(Stream(Bits(8 bit)))
//    val out3 = Vec(master(Stream(Bits(8 bit))), 3)
//
//    out3 <> StreamFork(in1, 3)

    val in3 = Vec(slave(Stream(Bits(8 bits))), 3)
    val out1 = master(Stream(Bits(8 bit)))

    implicit val config = RiscvCoreConfig()
    val cpu = new RiscvCore()

    out1 <> StreamArbiterFactory.roundRobin.on(in3)
  }

  SpinalConfig(
    targetDirectory = "tb/SFT_tb/rtl",
    oneFilePerComponent = true,
    defaultConfigForClockDomains = ClockDomainConfig(resetActiveLevel = LOW)
  ).generateVerilog(
    SFT()
  )
}
