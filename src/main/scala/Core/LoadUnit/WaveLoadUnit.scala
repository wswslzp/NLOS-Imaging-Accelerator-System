package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import bus.amba3.apb._
import Config._
import Util._
import spinal.lib.bus.amba4.axilite._
import spinal.lib.bus.misc._

case class WaveLoadUnit(
                       cfg: RsdKernelConfig,
                       init_addr: Int
                       )(override implicit val axi_config: Axi4Config) extends Component with Axi4Slave {
  override val word_bit_count: Int = cfg.wave_cfg.getDataWidth

  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)
  val Rlength = cfg.impulse_sample_point

  val io = new Bundle {
    val fc_eq_0 = in Bool
    val dc_eq_0 = in Bool
    val push_ending = in Bool
    val ready_for_store = out Bool
    val load_req = out Bool
    val distance_enable = in Bool
    val impulse_enable = in Bool
    val data_enable = out Bool
    val rsd_comp_end = out Bool
    val rsd_comp_start = in Bool
    val wave = master(Flow(
      SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)
    ))
  }
  wReady(True)
  awReady(True)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (wave_reg_addr_map, wave_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(cfg.radius_factor)(SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp))
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  arrangeRegMapAddr(wave_reg_addr_map, transfer_done_map)
  loadData()

  //TODO: The code below may cause many trouble

  // When DC == 0, wave load unit needs new waves
  val transfer_done_rise = transfer_done_reg.rise(initAt = False)
  val transfer_req_reg = RegInit(True)
  transfer_req_reg.setWhen(!io.fc_eq_0)
  transfer_req_reg.clearWhen(transfer_done_rise)
  io.load_req := transfer_req_reg

  // Make internal reg visible to simulation
  import spinal.core.sim._
  wave_regs.foreach(_.simPublic())
  transfer_done_rise.simPublic()

  // When the impulse has done transfer, the valid and start signal set high
  io.wave.valid := io.impulse_enable

  // The master set the transfer done register to indicate that the data is on the port
  io.data_enable := transfer_done_rise

  val count_for_push_wave = countUpFrom(io.rsd_comp_start, 0 until cfg.radius_factor, "count_for_push_wave")

  // Indicate when the internal memory is ready for storing the new data
  io.ready_for_store := !transfer_done_reg

  // Clear the transfer done register when all the wave data have been pushed.
  transfer_done_reg init False
  transfer_done_reg clearWhen count_for_push_wave.cnt.willOverflow

  val radius_idx = count_for_push_wave.cnt
  radius_idx.setName("radius_idx")
  io.wave.payload := wave_regs(radius_idx.value)
  io.rsd_comp_end := radius_idx.willOverflow

}
