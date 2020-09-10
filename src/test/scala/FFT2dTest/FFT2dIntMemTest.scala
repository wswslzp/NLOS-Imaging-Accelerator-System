package FFT2dTest

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.core.sim._

object FFT2dIntMemTest extends App{
  case class SramForTest() extends Component{
    val io = new Bundle {
      val CLK = in Bool
      val Q = out UInt(32 bit)
      val D = in UInt(32 bit)
      val ADR = in UInt(14 bit)
      val RM = in UInt(4 bit)
      val WE = in Bool()
      val ME = in Bool()
      val RME = in Bool
      val TEST1 = in Bool
      val LS = in Bool
      val rst = in Bool
    }

    val clkdom = ClockDomain(io.CLK)

    new ClockingArea(clkdom) {

      val int_mem = InternalMem(32, 14)
      int_mem.io.CLK <> io.CLK
      int_mem.io.Q <> io.Q
      int_mem.io.D <> io.D
      int_mem.io.ADR <> io.ADR
      int_mem.io.RM <> io.RM
      int_mem.io.WE <> io.WE
      int_mem.io.ME <> io.ME
      int_mem.io.RME <> io.RME
      int_mem.io.LS <> io.LS
      int_mem.io.TEST1 <> io.TEST1

    }

  }

  SimConfig
    .withWave
    .allOptimisation
    .workspacePath("tb/FFT2dIntMem_tb")
    .addSimulatorFlag("--bbox-unsup")
    .compile(SramForTest())
    .doSim("FFT2dIntMemTest") {dut =>
      import scala.util._
      dut.io.ADR #= 0
      dut.io.D #= 0
      dut.io.RM #= 2 // 4'b0010
      dut.io.ME #= false
      dut.io.WE #= false
      dut.io.RME #= false
      dut.io.LS #= false
      dut.io.TEST1 #= false

      dut.clkdom.doStimulus(2)
      dut.clkdom.waitSampling(10 )

      val random_gen = new Random()

      for(i <- 0 until 10) {
        dut.io.ME #= true
        dut.io.WE #= true
        dut.io.D #= random_gen.nextInt(1 << 16)
        dut.io.ADR #= i
        dut.clkdom.waitSampling()
      }

      dut.io.ADR #= 0
      dut.io.ME #= false
      dut.io.WE #= false
      dut.io.D #= 0
      dut.clkdom.waitSampling(5)

      for(i <- 0 until 10) {
        dut.io.ME #= true
        dut.io.WE #= false
        dut.io.ADR #= i
        dut.clkdom.waitSampling()
      }

      dut.clkdom.waitSampling(5)
      simSuccess()

    }
}
