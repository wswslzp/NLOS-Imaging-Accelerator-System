package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi.{Axi4, Axi4Config, Axi4WriteOnly}

case class CoefLoadUnit(
                         cfg: RsdKernelConfig,
                         freq_num: Int,
                         init_addr: Int,
                         override val axi_config: Axi4Config
                       ) extends Component with AXI4WLoad {
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

//case class CoefLoadUnit(cfg: RsdKernelConfig, freq_num: Int) extends Component {
//  val io = new Bundle{
//    val coef_out = master (
//      Flow(Vec(Vec(Vec(HComplex(cfg.hComplexConfig), cfg.depth_factor), cfg.radius_factor), freq_num))
//    )
//    val data_bus_in = slave (
//      Axi4WriteOnly(
//        Axi4Config(
//          addressWidth = 32,
//          dataWidth = cfg.hComplexConfig.getComplexWidth,
//          idWidth = 4//,
//        )
//      )
//    )
//  }
//
//  // the number of bank, row, column must align with the power of 2
//  val bank_num = 1 << log2Up(freq_num)
//  val row_num  = 1 << log2Up(cfg.radius_factor)
//  val col_num  = 1 << log2Up(cfg.depth_factor)
//  require(
//    log2Up(freq_num) + log2Up(cfg.radius_factor) + log2Up(cfg.depth_factor) < 32
//  )
//  val bank_addr_range = log2Up(freq_num) + log2Up(cfg.radius_factor) + log2Up(cfg.depth_factor) - 1 downto
//                        log2Up(cfg.radius_factor) + log2Up(cfg.depth_factor)
//  val row_addr_range  = log2Up(cfg.radius_factor) + log2Up(cfg.depth_factor) - 1 downto
//                        log2Up(cfg.depth_factor)
//  val col_addr_range  = log2Up(cfg.depth_factor) - 1 downto 0
//
//  // always waiting for the input address and the data
//  io.data_bus_in.aw.ready := True
//  io.data_bus_in.w.ready  := True
//  // Assumption: awlen equals the column number of one ram bank
//  val awaddr = Reg( UInt(32 bit) )
//  when(io.data_bus_in.aw.valid) {
//    awaddr := io.data_bus_in.aw.addr
////    awlen  := io.data_bus_in.aw.len
//  }
//
//  io.data_bus_in.b.resp := Axi4.resp.OKAY
//  io.data_bus_in.b.valid := True
//  io.data_bus_in.b.id := io.data_bus_in.aw.id
//
//  // convert the address
//  val bank_addr       = awaddr(bank_addr_range)
//  val row_addr        = awaddr(row_addr_range)
//  val init_col_addr   = awaddr(col_addr_range)
//  val col_addr_cnt    = Counter(0 until col_num, io.data_bus_in.w.valid)
//  val col_addr        = (init_col_addr + col_addr_cnt).resized
//
//  val int_reg_array = Vec(
//    Vec(
//      Vec(
//        Reg(HComplex(cfg.hComplexConfig)), col_num
//      ), row_num
//    ), bank_num
//  )
//
//  when(io.data_bus_in.w.valid) {
//    int_reg_array(bank_addr)(row_addr)(col_addr).assignFromBits(io.data_bus_in.w.data)
//  }
//
//  val transfer_done = Reg(Bool()) init False
//  transfer_done.setWhen(awaddr === U"32'hffff_ffff")
//
//  io.coef_out.valid := transfer_done
//  for {
//    freq   <- 0 until freq_num
//    radius <- 0 until cfg.radius_factor
//    depth  <- 0 until cfg.depth_factor
//    tmp_reg = int_reg_array(freq)(radius)(depth)
//  } {
//    io.coef_out.payload(freq)(radius)(depth) := tmp_reg
//  }
//
//}
