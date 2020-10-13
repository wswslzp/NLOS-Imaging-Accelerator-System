package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory


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
  }
  wReady(True)
  awReady(True)
//  wReady(io.ready_for_store)
//  awReady(io.ready_for_store)

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

  transfer_req_reg.setWhen(io.push_ending)
  transfer_req_reg.clearWhen(transfer_done_reg)
  io.load_req := transfer_req_reg

  transfer_done_reg init False
  transfer_done_reg clearWhen io.push_ending

  io.distance.payload := distance_reg
  io.distance.valid := transfer_done_reg
  io.data_enable := transfer_done_reg
  io.ready_for_store := !transfer_done_reg

}
