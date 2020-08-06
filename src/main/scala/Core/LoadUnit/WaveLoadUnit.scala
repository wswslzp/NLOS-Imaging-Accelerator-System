package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._

case class WaveLoadUnit(
                       cfg: RsdKernelConfig,
                       init_addr: Int,
                       override val axi_config: Axi4Config
                       ) extends Component with AXI4WLoad {
  override val word_bit_count: Int = cfg.wave_cfg.getDataWidth

  val io = new Bundle {
    val ready_for_store = in Bool
    val data_enable = in Bool

    val wave = master (Flow(
      Vec(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp), cfg.radius_factor)
    ))
  }

  wReady(io.ready_for_store)
  awReady(io.ready_for_store)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (wave_reg_addr_map, wave_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(cfg.radius_factor)(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp))
  )

  arrangeRegMapAddr( wave_reg_addr_map )
  loadData()

  io.wave.valid := io.data_enable
  io.wave.payload := Vec(wave_regs)

}
