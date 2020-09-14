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

  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)
  val Rlength = 1 << log2Up(
    Math.sqrt(
      Math.pow(row_num/2, 2) + Math.pow(col_num/2, 2)
    ).toInt
  )

  val io = new Bundle {
    val ready_for_store = in Bool
    val start = out Bool

    val wave = master (Flow(
      Vec(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp), Rlength)
    ))
  }

  wReady(io.ready_for_store)
  awReady(io.ready_for_store)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (wave_reg_addr_map, wave_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(cfg.radius_factor)(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp))
  )
  val (wave_push_start_map, wave_push_start_reg) = local_mem_manager.allocateReg(
    Bool(), "wave_push_start"
  )

  arrangeRegMapAddr( wave_reg_addr_map, wave_push_start_map )
  loadData()

//  io.wave.valid := io.data_enable
  io.wave.valid := wave_push_start_reg
  io.start := wave_push_start_reg
//  io.wave.payload := Vec(wave_regs)

  val radius_idx = countUpFrom(wave_push_start_reg, 0 until cfg.radius_factor, "radius_idx").cnt
  for (l <- 0 until Rlength) {
    io.wave.payload(l) := wave_regs(radius_idx.value)
  }

}
