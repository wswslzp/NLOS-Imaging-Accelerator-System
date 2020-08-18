package Core.LoadUnit

import Config.RsdKernelConfig
import Util._
import spinal.core._
import spinal.lib.bus.amba4.axi.Axi4Config
import spinal.lib._

import scala.collection.mutable.ArrayBuffer

case class ImpLoadUnit(
                        cfg: RsdKernelConfig,
                        init_addr: Int,
                        override val axi_config: Axi4Config
                      ) extends Component with AXI4WLoad{
  override val word_bit_count: Int = cfg.imp_cfg.getComplexWidth

  val row_num: Int = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)

  awReady(True)
  wReady(True)

  val io = new Bundle {
    val impulse_out = master (
      Flow(
        Vec(Vec(HComplex(cfg.imp_cfg), cfg.radius_factor), col_num)
      )
    )
  }

  // the number of sample radius should be the power of 2
  val local_mem_manager = ApplyMem(init_addr, cfg.imp_cfg.getComplexWidth)
  val radius_num = cfg.radius_factor
  val mem_size = 1 << log2Up(col_num / 2)

  // Apply for memories stored the impulse, with the ram amount same as radius_name
  val int_ram_array_map: Vector[(Range, Mem[Bits])] = Vector.fill(radius_num)(
    local_mem_manager.allocateRam(
      Mem(Bits(cfg.imp_cfg.getComplexWidth bit), BigInt(mem_size)).setWeakName("int_ram_array")
    )
  )
  // Apply for a register which indicate the beginning of the impulse pushing when it's written by AXI4 bus.
  val ( imp_push_start_map , imp_push_start_reg) = local_mem_manager.allocateReg(Bool().setWeakName("imp_push_start"))

  int_ram_array_map foreach (arrangeMemAddr(_))
  arrangeRegMapAddr(imp_push_start_map)

  printAddrRange
  loadData()

  // output the impulse
  val int_ram_array: Vector[Mem[Bits]] = int_ram_array_map.map(_._2)
  val imp_push_start = imp_push_start_reg

  io.impulse_out.valid := imp_push_start

  val output_imp = new Area {
    // Now, when the outside bus write a impulse start signal into imp_push_start register,
    // the virtual memory address that iterate from the left top of the image to the bottom right,
    // start counting up from 0 to the end.
    // TODO: Now the impulse loader output a row per cycle. So the row address of the impulse changes
    //  per cycle, and the virtual address = row address * point_num + col_address is output parallel
    //  for a row, and shift out for each radius.

    val virtual_imp_row_addr = countUpFrom(imp_push_start, 0 until row_num).cnt
    virtual_imp_row_addr.setWeakName("current_imp_row_addr")
    val virtual_imp_addr = (0 until col_num).map(virtual_imp_row_addr * col_num + _)

    val virtual_imp_addr_fifo = virtual_imp_addr.map(
      spinal.lib.History(_, radius_num)
    )

    // The line_mem_addr_map maps the virtual address to the true memory address of int_ram_array.
    // The true memory address is access the correct pixel in the internal register array
    // but the address used to access the memory at the same time is not identical.
    val line_mem_addr_map = Vec( buildAddrMap )
    for {
      col <- 0 until col_num
      radius <- 0 until cfg.radius_factor
      ram = int_ram_array(radius)
    } {
      // Due to the pipeline data pattern of the RSD kernel generation core array, the data
      // popped out from the io.impulse_out.payload comes from consecutive position of the whole image
      // in the same cycle, and the latter output ports have former address.
      // It's OK to have negative address result because we don't care about the data when effective results
      // haven't been reached.
      val address: UInt = line_mem_addr_map(virtual_imp_addr_fifo(col)(radius))
      io.impulse_out.payload(col)(radius) := ram(address.resize(ram.addressWidth))
    }
  }

  private def buildAddrMap: ArrayBuffer[UInt] = {
    val addr_lut = ArrayBuffer.fill(1 << log2Up(cfg.kernel_size.product))(U(0))
    for {
      idx <- 0 until 1 << log2Up(cfg.kernel_size.product)
      x = idx / cfg.kernel_size(0) - cfg.kernel_size(0)/2
      y = idx % cfg.kernel_size(1) - cfg.kernel_size(1)/2
      xs = (x + cfg.kernel_size(0)/2) % cfg.kernel_size(0)
      ys = (y + cfg.kernel_size(1)/2) % cfg.kernel_size(1)
      idxs = xs * cfg.kernel_size(0) + ys
      d_tmp = Math.min(Math.sqrt((x*x+y*y)/2).toInt, cfg.kernel_size(1)/2-1 )
    } {
      addr_lut(idxs) = U(d_tmp)
    }
    addr_lut
  }

}
