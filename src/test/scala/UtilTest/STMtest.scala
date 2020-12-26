package UtilTest

import spinal.core._

object STMtest extends App {

  import spinal.core.sim._
  import spinal.lib.fsm._

  class TopLevel extends Component {
    val io = new Bundle {
      val result = out Bool
      val when_is_next = out Bool
      val int_fsm1_flag = out Bool
      val int_fsm2_flag = out Bool
        val on_entry = out Bool()
    }
    val counter = Reg(UInt(8 bits)) init (0)
    io.on_entry := False

    val fsm = new StateMachine {
      val stateA = new State with EntryPoint
      val stateB = new State
      val stateC = new StateDelay(10)

      def int_fsm1() = new StateMachine {
        val int1_s1 = new State with EntryPoint
        val int1_s2 = new StateDelay(3)

        int1_s1
          .whenIsActive {
            goto(int1_s2)
          }

        int1_s2
          .whenIsActive {
            io.int_fsm1_flag := True
          }
          .whenCompleted {
            exitFsm()
          }
      }

      def int_fsm2() = new StateMachine {
        val int2_s1 = new State with EntryPoint
        val int2_s2 = new StateDelay(6)

        int2_s1
          .whenIsActive {
            goto(int2_s2)
          }

        int2_s2
          .whenIsActive {
            io.int_fsm2_flag := True
          }
          .whenCompleted {
            exitFsm()
          }
      }

      val stateD = new StateParallelFsm(int_fsm1(), int_fsm2())

      // Boot state
      io.result := False
      io.when_is_next := False
      io.int_fsm1_flag := False
      io.int_fsm2_flag := False

      stateA
        .whenIsActive(goto(stateB))

      stateB
        .onEntry{
          counter := 0
          io.on_entry.set()
        } // The first cycle of the stateB
        .whenIsActive { // when current_state == stateB
          counter := counter + 1
          when(counter === 4) {
            goto(stateC)
          }
        }
        .whenIsNext { // when next_state == stateB
          io.when_is_next := True
        }
        .onExit(io.result := True) // The last cycle of the stateB

      stateC
        .whenCompleted {
          goto(stateD)
        }

      stateD
        .whenCompleted {
          goto(stateA)
        }
    }
  }

  SimConfig
    .withConfig(SpinalConfig(defaultClockDomainFrequency = FixedFrequency(1 GHz)))
    .withWave
    .withVerilator
    .workspacePath("tb")
    .allOptimisation
    .compile(new TopLevel())
    .doSim("STMTest") { dut =>
      dut.clockDomain.doStimulus(2)

      dut.clockDomain.waitSampling(100)

      simSuccess()
    }
}
