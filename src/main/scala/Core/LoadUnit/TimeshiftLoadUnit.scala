package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._


case class TimeshiftLoadUnit(
                              cfg: RsdKernelConfig,
                              init_addr: Int,
                              override val axi_config: Axi4Config
                            ) extends Component with AXI4WLoad {
  override val word_bit_count: Int = cfg.timeshift_cfg.getComplexWidth

  val io = new Bundle {
    val ready_for_store = in Bool
    val data_enable = in Bool

    val timeshift = master (Flow(
      Vec(HComplex(cfg.timeshift_cfg), cfg.depth_factor)
    ))
  }
  wReady(io.ready_for_store)
  awReady(io.ready_for_store)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (timeshift_reg_addr_map, timeshift_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(cfg.depth_factor)(HComplex(cfg.timeshift_cfg))
  )

  arrangeRegMapAddr( timeshift_reg_addr_map )
  loadData()

  io.timeshift.valid := io.data_enable
  io.timeshift.payload := Vec(timeshift_regs)


}
