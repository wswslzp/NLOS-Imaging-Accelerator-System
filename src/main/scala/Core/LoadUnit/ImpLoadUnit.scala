package Core.LoadUnit

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.lib.bus.amba4.axi.{Axi4Config, Axi4SharedToApb3Bridge}
import spinal.lib._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory

import scala.collection.mutable.ArrayBuffer

case class ImpLoadUnit(
                        cfg: RsdKernelConfig,
                        init_addr: Int,
                        override val axi_config: Axi4Config
                      ) extends Component with Axi4Slave{
  override val word_bit_count: Int = cfg.imp_cfg.getComplexWidth

  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)

  // the number of sample radius should be the power of 2
  val local_mem_manager = ApplyMem(init_addr, cfg.imp_cfg.getComplexWidth)
  val radius_num = cfg.radius_factor
  //  val mem_size = 1 << log2Up(col_num / 2)
  val Rlength = 1 << log2Up(
    Math.sqrt(
      Math.pow(row_num/2, 2) + Math.pow(col_num/2, 2)
    ).toInt
  )

  awReady(True)
  wReady(True)

  val io = new Bundle {
    val fc_eq_0 = in Bool
    val dc_eq_0 = in Bool
    val distance_enable = in Bool()
    val wave_enable = in Bool()
    val ready_for_store = out Bool
    val load_req = out Bool
    val data_enable = out Bool
    val rsd_comp_end = out Bool
    val impulse_out = master (
      Flow(
        Vec(HComplex(cfg.imp_cfg), Rlength)
      )
    )
  }

  // Apply for memories stored the impulse, with the ram amount same as radius_name
  val int_ram_array_map: Vector[(Range, Mem[Bits])] = Vector.fill(Rlength)(
    local_mem_manager.allocateRam(
      Mem(Bits(cfg.imp_cfg.getComplexWidth bit), BigInt(radius_num)).setWeakName("int_ram_array")
    )
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  int_ram_array_map foreach (arrangeMemAddr(_))
  arrangeRegMapAddr(transfer_done_map)

  printAddrRange
  loadData()

  val transfer_req_reg = RegInit(False)

  transfer_req_reg.setWhen(io.dc_eq_0 & io.fc_eq_0 & io.distance_enable)
  io.load_req := transfer_req_reg

  // output the impulse
  val int_ram_array: Vector[Mem[Bits]] = int_ram_array_map.map(_._2)

  val compute_stage = io.dc_eq_0 ## io.fc_eq_0
  val rsd_comp_start = RegInit(False)
  switch(compute_stage){
    is(B"2'b00") {
      rsd_comp_start := transfer_done_reg
    }
    is(B"2'b01") {
//      rsd_comp_start := io.rsd_comp_end
      rsd_comp_start := Delay(io.distance_enable, 6, init = False) // The latency of coefGenCore is 6
    }
    is(B"2'b10") {
      rsd_comp_start := io.wave_enable
    }
    is(B"2'b11") {
      rsd_comp_start := Delay(io.distance_enable, 6, init = False) // The latency of coefGenCore is 6
    }
  }

  io.impulse_out.valid := rsd_comp_start
  io.data_enable := transfer_done_reg

  val output_imp = new Area {
    val virtual_imp_radix_area = countUpFrom(rsd_comp_start, 0 until radius_num, "count_for_push_imp")
    val virtual_imp_radix = virtual_imp_radix_area.cnt
    virtual_imp_radix.setWeakName("virtual_imp_radix")

    for {
      l <- 0 until Rlength
      ram = int_ram_array(l)
    } {
      io.impulse_out.payload(l) := ram(virtual_imp_radix)
    }
  }

  transfer_done_reg init False
  transfer_done_reg clearWhen output_imp.virtual_imp_radix.willOverflow
  io.rsd_comp_end := output_imp.virtual_imp_radix.willOverflow

}
