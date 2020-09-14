package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._


case class DistanceLoadUnit(
                             cfg: RsdKernelConfig,
                             init_addr: Int,
                             override val axi_config: Axi4Config
                           ) extends Component with AXI4WLoad {
  override val word_bit_count: Int = cfg.distance_cfg.getDataWidth

  val io = new Bundle {
    val ready_for_store = in Bool // When the data store in the internal memory is all consumed, it will be high active.
    val start = out Bool // The signal's high active indicates that this transaction is done and data is loaded.

    val distance = master (Flow(
      SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    ))
  }
  wReady(io.ready_for_store)
  awReady(io.ready_for_store)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (distance_reg_addr_map, distance_reg) = local_mem_manager.allocateReg(
    SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(Bool())

  arrangeRegMapAddr( distance_reg_addr_map , transfer_done_map)
  loadData()

  io.distance.payload := distance_reg
  io.distance.valid := transfer_done_reg
  io.start := transfer_done_reg

}
