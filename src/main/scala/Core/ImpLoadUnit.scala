package Core

// TODO: Need to modify the load logic

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
      Mem(Bits(cfg.hComplexConfig.getComplexWidth bit), BigInt(mem_size)).setWeakName("int_ram_array")
    )
  )
  val transfer_done_map = local_mem_manager.allocateReg(Bool().setWeakName("transfer_done"))

  int_ram_array_map foreach (arrangeMemAddr(_))
  arrangeRegAddr(transfer_done_map)

  printAddrRange
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

  private def buildAddrMap: ArrayBuffer[UInt] = {
    val addr_lut = ArrayBuffer.fill(1 << log2Up(cfg.kernel_size.product))(U(0))
//    val R = math.sqrt(cfg.kernel_size.map(_ / 2).map(math.pow(_, 2)).sum)
    for {
      idx <- 0 until 1 << log2Up(cfg.kernel_size.product)
      x = idx / cfg.kernel_size(0) - cfg.kernel_size(0)/2
      y = idx % cfg.kernel_size(1) - cfg.kernel_size(1)/2
      xs = (x + cfg.kernel_size(0)/2) % cfg.kernel_size(0)
      ys = (y + cfg.kernel_size(1)/2) % cfg.kernel_size(1)
      idxs = xs * cfg.kernel_size(0) + ys
      d_tmp = math.min(math.sqrt((x*x+y*y)/2).toInt, cfg.kernel_size(1)/2-1 )
    } {
//      addr_lut(idx) = U(d_tmp)
      addr_lut(idxs) = U(d_tmp)
    }
    addr_lut
  }

}
