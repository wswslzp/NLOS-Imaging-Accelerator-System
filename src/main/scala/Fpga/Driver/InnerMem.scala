package Fpga.Driver
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi.Axi4WriteOnly
import spinal.lib.fsm.{EntryPoint, State, StateMachine}

class InnerMem(memDepth: Int, width: Int) extends Area{ innerMem=>
  val rom: Mem[Bits] = Mem(Bits(width bit), memDepth)
  val romAddr = Counter(0 until memDepth).setCompositeName(this, "rom_addr")
  val en = False
  val data = rom.readSync(romAddr.value.resized, en)

  def addrIncr(): Unit = romAddr.increment()

  def oneShotDriverFSM(addr: UInt, bus: Axi4WriteOnly) = new StateMachine {
    setCompositeName(innerMem, s"_fsm")
    val one_shot = new State
    val done_addr_shot = new State
    val done_data_shot = new State

    val addr_shot = new State with EntryPoint {
      whenIsActive {
        // access ram
        en.set()

        // access axi4 address channel
        bus.aw.valid.set()
        bus.aw.addr := addr.resized
        bus.aw.len := 0 // length = 1

        when(bus.aw.fire){
          goto(one_shot)
        }
      }
    }

    one_shot
      .whenIsActive {
        en.set()

        bus.aw.valid.clear()
        bus.w.valid.set()
        bus.w.data := data.resized
        bus.w.last.set()
        when(bus.w.fire){
          goto(done_addr_shot)
        }
      }

    done_addr_shot
      .whenIsActive {

        bus.w.valid.clear()
        bus.w.last.clear()
        bus.aw.valid.set()
        bus.aw.addr := ( addr + 1 ).resized
        bus.aw.len := 0 // length = 1

        when(bus.aw.fire){
          goto(done_data_shot)
        }
      }

    done_data_shot
      .whenIsActive {
        bus.aw.valid.clear()
        bus.w.valid.set()
        bus.w.last.set()
        bus.w.data := 1

        when(bus.w.fire){
          addrIncr()
          exitFsm()
        }
      }

  }

  /**
   * function of FSM to drive data into AXI4 bus in burst mode
   * @param addr Burst transaction initial address
   * @param bus The master bus
   * @param burst_len The number of burst transaction
   * @param shot_len The length of burst transaction
   * @return return the corresponding FSM
   */
  def burstDriverFSM(addr: Int, done_addr: Int, bus: Axi4WriteOnly, burst_len: Int, shot_len: Int) = new StateMachine {
    setCompositeName(innerMem, s"_fsm")
    val burst_cnt = Counter(0 until burst_len).setCompositeName(innerMem, "burst_cnt") // counter for the index burst shot
    val shot_cnt = Counter(0 until shot_len).setCompositeName(innerMem, "shot_cnt") // counter for current index inside a burst transaction
    val burst_prim_addr = RegInit(U(addr, bus.aw.addr.getBitsWidth bit))

    val one_burst_shot = new State
    val done_addr_shot = new State
    val done_data_shot = new State
    val addr_shot = new State with EntryPoint {
      whenIsActive {
        // memory part
        en.set()
        addrIncr()

        // address channel
        bus.aw.valid.set()
        bus.aw.addr := burst_prim_addr.resized
        bus.aw.len := U(shot_len-1).resized
        when(bus.aw.fire){
          burst_prim_addr := burst_prim_addr + shot_len
          goto(one_burst_shot)
        }
      }
    }

    one_burst_shot
      .whenIsActive {
        en.set()

        when(shot_cnt.willOverflow) {
          burst_cnt.increment()
          when(burst_cnt.willOverflowIfInc){
            goto(done_addr_shot)
          } otherwise{
            goto(addr_shot)
          }
        } otherwise {
          when(bus.w.fire){
            addrIncr()
          }
        }

        when(bus.w.fire){
          shot_cnt.increment()
        }

        // address channel
        bus.aw.valid.clear()
        // data channel
        bus.w.valid.set()
        bus.w.data := data.resized
        bus.w.last := shot_cnt.willOverflow
      }

    done_addr_shot
      .whenIsActive {
        en.clear()
        // address channel
        bus.aw.valid.set()
        bus.aw.addr := U(done_addr).resized
        bus.aw.len := 0
        // data channel
        bus.w.valid.clear()
        when(bus.aw.fire){ goto(done_data_shot) }
      }

    done_data_shot
      .whenIsActive {
        en.clear()
        // address channel
        bus.aw.valid.clear()
        // data channel
        bus.w.valid.set()
        bus.w.data := 1

        when(bus.w.fire){exitFsm()}
      }
  }
}

