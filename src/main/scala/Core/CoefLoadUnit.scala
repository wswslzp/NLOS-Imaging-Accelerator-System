package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi.{Axi4, Axi4Config, Axi4WriteOnly}

// TODO: Coef now compute in the chip
case class CoefLoadUnit
(
   cfg: RsdKernelConfig,
   freq_num: Int,
   init_addr: Int,
   override val axi_config: Axi4Config
) extends Component with AXI4WLoad
{
  override val word_bit_count: Int = cfg.hComplexConfig.getComplexWidth
  awReady(True)
  wReady(True)

  val io = new Bundle {
    val coef_out = master (
      Flow(Vec(Vec(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor), cfg.radius_factor), freq_num))
    )
  }

  val local_mem_manager = ApplyMem(init_addr, cfg.hComplexConfig.getComplexWidth)

  // allocate the address of registers
  val bank_num = freq_num
  val row_num = cfg.radius_factor
  val col_num = cfg.depth_factor

  val (transfer_done_addr, transfer_done) = local_mem_manager.allocateReg(Bool())
  val int_reg_array_map = local_mem_manager.allocateRegArray(
    Vector.fill(bank_num, row_num)(Vec(HComplex(cfg.hComplexConfig), col_num)).flatten
  )
  val int_reg_array = int_reg_array_map.values.toVector

  // arrange the address
  arrangeRegAddr(
    transfer_done_addr -> transfer_done
  )
  arrangeRegAddr(
    int_reg_array_map
  )

  printAddrRange
  loadData()

  // output coef
  io.coef_out.valid := transfer_done
  for {
    freq   <- 0 until freq_num
    radius <- 0 until cfg.radius_factor
    depth  <- 0 until cfg.depth_factor
    row_addr = freq * row_num + radius
    // tmp = int_reg_array(row_addr)(depth)
    tmp_reg = int_reg_array(row_addr).asInstanceOf[Vec[HComplex]]
  } {
    //io.coef_out.payload(freq)(radius)(depth) := tmp_reg
    io.coef_out.payload(freq)(radius)(depth) := tmp_reg(depth)
  }

}

