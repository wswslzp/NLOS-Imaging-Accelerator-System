package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory
import spinal.core.sim._

case class DistanceLoadUnit(
                             cfg: RsdKernelConfig,
                             init_addr: Int
                           )(override implicit val axi_config: Axi4Config) extends Component with Axi4Slave {
  override val word_bit_count: Int = cfg.distance_cfg.getDataWidth

  val io = new Bundle {
    val load_req = out Bool
    val ready_for_store = out Bool // When the data store in the internal memory is all consumed, it will be high active.
    val data_enable = out Bool // The signal's high active indicates that this transaction is done and data is loaded.
    val push_ending = in Bool // indicate that the impulse of a radix has been all pushed out.
    val distance = master (Flow(
      SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    ))
    val cnt_incr = in Bool()
  }
  wReady(True)
  awReady(True)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (distance_reg_addr_map, distance_reg) = local_mem_manager.allocateReg(
    SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  arrangeRegMapAddr( distance_reg_addr_map, transfer_done_map )
  loadData()

  val transfer_req_reg = RegInit(True)
  val transfer_done_rise = transfer_done_reg.rise(False)

  // Make reg visible to simulation
  transfer_done_rise.simPublic()
  distance_reg.simPublic()

  transfer_req_reg.setWhen(io.cnt_incr)
  transfer_req_reg.clearWhen(transfer_done_rise)
  io.load_req := transfer_req_reg

  transfer_done_reg init False
  transfer_done_reg clearWhen io.cnt_incr

  io.distance.payload := distance_reg
  io.distance.valid := transfer_done_reg
  io.data_enable := transfer_done_rise
  io.ready_for_store := !transfer_done_reg

}
