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
    val ready_for_store = in Bool
    val start = out Bool
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
  io.start := imp_push_start

  val output_imp = new Area {
    // Now, when the outside bus write a impulse start signal into imp_push_start register,
    // the virtual memory address that iterate from the left top of the image to the bottom right,
    // start counting up from 0 to the end.
    // TODO: Now the impulse loader output a row per cycle. So the row address of the impulse changes
    //  per cycle, and the virtual address = row address * point_num + col_address is output parallel
    //  for a row, and shift out for each radius.

    val virtual_imp_radidx = countUpFrom(imp_push_start, 0 until radius_num).cnt
    virtual_imp_radidx.setWeakName("current_imp_row_addr")

    for {
      l <- 0 until Rlength
      ram = int_ram_array(l)
    } {
      io.impulse_out.payload(l) := ram(virtual_imp_radidx)
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
