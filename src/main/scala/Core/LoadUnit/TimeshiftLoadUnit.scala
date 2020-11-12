package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory
import spinal.core.sim._

case class TimeshiftLoadUnit(
                              cfg: RsdKernelConfig,
                              init_addr: Int
                            )(override implicit val axi_config: Axi4Config) extends Component with Axi4Slave {
  override val word_bit_count: Int = cfg.timeshift_cfg.getComplexWidth

  val io = new Bundle {
    val load_req = out Bool
    val ready_for_store = out Bool
    val data_enable = out Bool
    val timeshift = master (Flow(
      HComplex(cfg.timeshift_cfg)
    ))
    val cnt_incr = in Bool()
  }
  wReady(True)
  awReady(True)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (timeshift_reg_addr_map, timeshift_reg) = local_mem_manager.allocateReg(
    Bits(cfg.timeshift_cfg.getComplexWidth bit)
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  arrangeRegMapAddr( timeshift_reg_addr_map, transfer_done_map )
  loadData()

  val transfer_req_reg = RegInit(True)
  val transfer_done_rise = (transfer_done_reg.rise(initAt = False))

  // Make internal reg visible to simulation
  transfer_done_rise.simPublic()

  transfer_req_reg.setWhen(io.cnt_incr)
  transfer_req_reg.clearWhen(transfer_done_rise)
  io.load_req := transfer_req_reg

  transfer_done_reg init False
  transfer_done_reg clearWhen io.cnt_incr

  io.timeshift.valid := transfer_done_reg
  io.timeshift.payload := timeshift_reg

  io.data_enable := transfer_done_rise
  io.ready_for_store := !transfer_done_reg


}
