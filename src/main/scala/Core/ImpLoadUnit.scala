package Core

// TODO: REWRITE the module, important logic is WRONG.

import spinal.core._
import Config.RsdKernelConfig
import Util.{AXI4WLoad, ApplyMem, HComplex, countUpFrom}
import spinal.lib.bus.amba4.axi.{Axi4Config, Axi4WriteOnly}
import spinal.lib.{Counter, Flow, master, slave}

import scala.collection.mutable.ArrayBuffer

case class ImpLoadUnit(
                        cfg: RsdKernelConfig,
                        init_addr: Int,
                        override val axi_config: Axi4Config
                      ) extends Component with AXI4WLoad{
  override val word_bit_count: Int = cfg.hComplexConfig.getComplexWidth

  awReady(True)
  wReady(True)

  val io = new Bundle {
    val impulse_out = master (
      Flow(
        Vec(Vec(HComplex(cfg.hComplexConfig), cfg.deltaw_factor), cfg.radius_factor)
      )
    )
  }

  // the number of sample radius should be the power of 2
  val local_mem_manager = ApplyMem(init_addr, cfg.hComplexConfig.getComplexWidth)
  val radius_num = cfg.radius_factor
  val mem_size   = if(cfg.less_mem_size) {
    1 << log2Up(cfg.kernel_size.product)
  } else {
    1 << log2Up(cfg.kernel_size(1)/2) // only store the data on the radius
  }

  val int_ram_array_map = Vector.fill(radius_num)(
    local_mem_manager.allocateRam(
      Mem(Bits(cfg.hComplexConfig.getComplexWidth bit), BigInt(mem_size))
    )
  )
  val transfer_done_map = local_mem_manager.allocateReg(Bool)

  int_ram_array_map foreach (arrangeMemAddr(_))
  arrangeRegAddr(transfer_done_map)

  loadData()

  // output the impulse
  val int_ram_array: Vector[Mem[Bits]] = int_ram_array_map.map(_._2)
  val transfer_done = transfer_done_map._2

  io.impulse_out.valid := transfer_done
  val output_imp = new Area {
    if (cfg.less_mem_size) {
      val virtual_mem_addr = countUpFrom(transfer_done, 0 until cfg.kernel_size.product).cnt
      virtual_mem_addr.setName("current_line_idx")
      val line_mem_addr_map = Vec( buildAddrMap )
      val true_mem_addr = line_mem_addr_map(virtual_mem_addr)
      for {
        radius <- 0 until cfg.radius_factor
        // because the transformation is not linear, so delta factor is not available here
        ram = int_ram_array(radius)
      } {
        io.impulse_out.payload(radius)(0) := ram(true_mem_addr.resize(ram.addressWidth))
      }
    } else {
      val current_mem_addr = countUpFrom(transfer_done, 0 until cfg.kernel_size.product, step = cfg.deltaw_factor).cnt
      current_mem_addr.setName("current_mem_addr")
      for {
        radius <- 0 until cfg.radius_factor
        delta  <- 0 until cfg.deltaw_factor
        ram = int_ram_array(radius)
      } {
        io.impulse_out.payload(radius)(delta) := ram(( current_mem_addr.value + U(delta) ).resized)
      }
    }
  }

//
//  val awaddr_r = Reg(UInt(32 bit))
//  val awlen_r  = Reg(UInt(8 bit))
//
//  io.data_in.aw.ready := True
//  io.data_in.w.ready  := True
////  io.data_in.b.ready  := True
//  when(io.data_in.aw.valid) {
//    awaddr_r := io.data_in.aw.addr
//    awlen_r  := io.data_in.aw.len
//  }
//
//  // convert the address and load the data into memory
//  val bank_addr = awaddr_r(radius_addr_range) // every radius correspond to a bank
//  val oh_bank_strobe = B(1, radius_num bit) << bank_addr
//  val init_mem_addr  = awaddr_r(mem_addr_range)
//  val mem_addr_cnt = countLtUInt(io.data_in.w.valid, awlen_r)
//
//  val mem_addr = UInt(log2Up(mem_size) bit)
//  if (cfg.less_mem_size) {
//    val mem_addr_map = Mem(UInt(log2Up(mem_size) bit), initialContent = buildAddrMap)
//    mem_addr := mem_addr_map(init_mem_addr + mem_addr_cnt)
//  } else {
//    mem_addr := init_mem_addr + mem_addr_cnt
//  }
//
//  when(io.data_in.w.valid) {
//    int_ram_array.zipWithIndex foreach { case(ram, idx) =>
//      ram.write(mem_addr, io.data_in.w.data, oh_bank_strobe(idx))
//    }
//  }
//
//  // when controller send efff_ffff to this register,
//  // all data transactions are over.
//  // pull up the valid of impulse out.
//  val transfer_done = Reg(Bool()) init False
//  transfer_done.setWhen(awaddr_r === U"32'hefff_ffff")
//
////  io.impulse_out.valid := transfer_done
//  val mem_read_out_area = Util.countUpFrom(transfer_done, 0 until cfg.kernel_size.product/cfg.deltaw_factor)
//  io.impulse_out.valid := mem_read_out_area.cond_period
//  val mem_read_addr = mem_read_out_area.cnt
//  when(io.impulse_out.valid) {
//    ???
//  }
//
  private def buildAddrMap: ArrayBuffer[UInt] = {
    val addr_lut = ArrayBuffer.fill(1 << log2Up(cfg.kernel_size.product))(U(0))
    for {
      idx <- 0 until 1 << log2Up(cfg.kernel_size.product)
      x = idx / cfg.kernel_size(0) - cfg.kernel_size(0)/2
      y = idx % cfg.kernel_size(1) - cfg.kernel_size(1)/2
      d_tmp = math.min( math.sqrt((x*x+y*y)/2).toInt, cfg.kernel_size(1)/2-1 )
    } {
      addr_lut(idx) = U(d_tmp)
    }
    addr_lut
  }

}
