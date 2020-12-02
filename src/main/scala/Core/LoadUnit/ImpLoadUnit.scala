package Core.LoadUnit

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.lib.bus.amba4.axi.{Axi4Config, Axi4SharedToApb3Bridge}
import spinal.lib._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory

import scala.collection.mutable.ArrayBuffer
import spinal.core.sim._

case class ImpLoadUnit(
                        cfg: RsdKernelConfig,
                        init_addr: Int
                      )(override implicit val axi_config: Axi4Config) extends Component with Axi4Slave{
  override val word_bit_count: Int = cfg.imp_cfg.getDataWidth

  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)

  // the number of sample radius should be the power of 2
  val local_mem_manager = ApplyMem(init_addr, cfg.imp_cfg.getDataWidth)
  val radius_num = cfg.radius_factor
  val Rlength = cfg.impulse_sample_point

  awReady(True)
  wReady(True)

  val io = new Bundle {
    val fc_eq_0 = in Bool()
    val dc_eq_0 = in Bool()
    val distance_enable = in Bool()
    val rsd_comp_start = in Bool()
    val rsd_comp_end = out Bool()
    val load_req = out Bool()
    val data_enable = out Bool()
    val impulse_out = master (
      Flow(
        Vec(SFix(cfg.imp_cfg.intw-1 exp, -cfg.imp_cfg.fracw exp), Rlength)
      )
    )
  }

  // Apply for memories stored the impulse, with the ram amount same as radius_name
  val int_ram_array_map: Array[(Range, Mem[Bits])] = Array.tabulate(Rlength) { idx=>
    local_mem_manager.allocateRam(
      Mem(Bits(cfg.imp_cfg.getDataWidth bit), BigInt(radius_num)).setWeakName(s"int_ram_$idx")
    )
  }
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  int_ram_array_map foreach (arrangeMemAddr(_))
  arrangeRegMapAddr(transfer_done_map)

  printAddrRange
  loadData()

  val transfer_req_reg = RegInit(True)

  transfer_req_reg.setWhen(io.dc_eq_0 & io.fc_eq_0 & io.distance_enable)
  val transfer_done_rise = transfer_done_reg.rise(initAt = False)
  transfer_req_reg clearWhen transfer_done_rise
  io.load_req := transfer_req_reg

  // output the impulse
  val int_ram_array: Array[Mem[Bits]] = int_ram_array_map.map(_._2)
  if(cfg.fpga_impl){
    transfer_done_rise.addAttribute("haha", "123")
    int_ram_array.foreach(_.addAttribute("ramstyle", "M20K"))
  }

  // Make internal memories visible to simulation
  transfer_done_rise.simPublic()
  int_ram_array.foreach(_.simPublic())
  //  val sim_int_ram_array = int_ram_array.map(_.simPublic())

  io.data_enable := transfer_done_rise

  val output_imp = new Area {
    val virtual_imp_radix_area = countUpFrom(io.rsd_comp_start, 0 until radius_num, "count_for_push_imp")
    val virtual_imp_radix = virtual_imp_radix_area.cnt
    virtual_imp_radix.setWeakName("virtual_imp_radix")

    for {
      l <- 0 until Rlength
      ram = int_ram_array(l)
    } {
//      io.impulse_out.payload(l) := ram(virtual_imp_radix)
      io.impulse_out.payload(l).assignFromBits(ram(virtual_imp_radix))
    }
    io.impulse_out.valid := virtual_imp_radix_area.cond_period
    io.rsd_comp_end := virtual_imp_radix.willOverflow
  }

  transfer_done_reg init False
  transfer_done_reg clearWhen output_imp.virtual_imp_radix.willOverflow

}
