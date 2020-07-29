import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core._
import Config._
import Util._

object PwMatMulTest extends App {
  val spinal_config = SpinalConfig(
    defaultClockDomainFrequency = FixedFrequency(10 MHz)
  )
  val width = 32
  val row = 32
  val col = row

  val bench_compiled = SimConfig
    .withConfig(spinal_config)
    .withWave
    .allOptimisation
    .workspaceName("tb")
    .compile(PwMatMul(width, row, col))

  bench_compiled.doSim("Mat Mul Test"){ dut =>
    dut.clockDomain.forkStimulus(100)

    val mat_1 = (new SimpleMat(row, col)).construct(1)
    val mat_2 = (new SimpleMat(row, col)).construct(2)

    for (i <- 0 until row) {
      for (j <- 0 until col) {
        dut.io.mat_a.payload(j) #= mat_1(i)(j)
        dut.io.mat_a.valid #= true
        dut.io.mat_b.payload(j) #= mat_2(i)(j)
        dut.io.mat_b.valid #= true
      }
      dut.clockDomain.waitSampling()
    }
    simSuccess()

  }
}
