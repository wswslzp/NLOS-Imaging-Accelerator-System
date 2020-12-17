package Fpga

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import Util._

/**
 * Post-process of the final result. Pipe out two row per cycle because
 * up-sampling by 2. Output is 8-bit gray pixel.
 * @param cfg rsd_kernel_cfg
 * @param quant_bit_width The color depth of output image. Same as its bit width
 * @param over_sample_factor The up-sampling factor of output image.
 */
// TODO: Need verification
//  1. Do integration verification
//  2. Do single module verification
case class PostProcess(
                        cfg: RsdKernelConfig,
                        quant_bit_width: Int = 8,
                        over_sample_factor: Int = 2,
                        pixel_parallel: Int = 2
                      ) extends Component{
  require(( quant_bit_width >= 8 ) && ( over_sample_factor >= 1 ) && ( cfg.cols % pixel_parallel == 0 ))
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last
  val io = new Bundle {
    val done = in Bool()
    val img_in = slave(Flow( HComplex(cfg.getResultConfig) ))
    val img_out = master(Stream(Vec.fill(pixel_parallel)(UInt(quant_bit_width bit))))
  }

  // ***************** abs(result) ************************************************
  val img_in_1 = io.img_in.stage()
  val img_in_abs = img_in_1.translateWith(img_in_1.payload.abs)
  img_in_abs.simPublic()

  // ***************** quantize ***********************
  // TODO: simply catch upper bits can not work for small value.
  //  another robust quantize method should be taken
  val img_in_abs_1 = img_in_abs.stage()
  val img_in_q = img_in_abs_1.translateWith(img_in_abs_1.payload.asBits.resizeLeft(quant_bit_width).asUInt)
  img_in_q.simPublic()

  // ***************** store and compare **************
  // TODO: Needs to modify the logic
  //  memory should use BRAM/SRAM
  val os_rows = cfg.rows * over_sample_factor
  val os_cols = cfg.cols * over_sample_factor
//  val result_mem = Vec.fill(os_rows)(
//    Vec.fill(os_cols)(Reg(UInt(quant_bit_width bit)))
//  )
  val result_mem = Mem(UInt(quant_bit_width bit), BigInt(cfg.rows * cfg.cols))
//  val row_addr = Counter(0, cfg.rows)
  val pixel_addr = Counter(0, cfg.rows * cfg.cols)
//  val row_addr = pixel_addr / cfg.cols
//  val col_addr = pixel_addr % cfg.cols
//  val os_row_addr = ( row_addr * over_sample_factor ).resize(log2Up(os_rows))
  when(img_in_q.valid){
    pixel_addr.increment()
  }

  val q_pix = RegNext(img_in_q.payload)
  val mem_prev_pix = result_mem.readSync(pixel_addr)
  val qin_larger = mem_prev_pix < q_pix
  val store_in_en = RegNext(img_in_q.valid) init False
  val w_pixel_addr = RegNext(pixel_addr) init 0
  result_mem.write(
    address = w_pixel_addr,
    data = qin_larger ? q_pix | mem_prev_pix,
    enable = store_in_en
  )

  // ***************** signal status *******************
  //  `nlos_comp_done` signal that previous NLOS task is done
  val nlos_comp_done = Reg(Bool()).init(False).setWhen(io.done).clearWhen(io.img_in.valid.rise(False))

  // ***************** interpolate and output **********
  // Counter for image ( osf * cfg.rows * osf * cfg.cols/pixel_parallel )
  def addressTrans(os_pix_addr: UInt): UInt = {
    val os_row = os_pix_addr / (cfg.cols * over_sample_factor)
    val os_col = os_pix_addr % (cfg.cols * over_sample_factor)
    val nos_row = os_row / over_sample_factor
    val nos_col = os_col / over_sample_factor
    nos_row * cfg.cols + nos_col
  }

  val pixel_cnt = Counter(0, cfg.kernel_size.product * over_sample_factor * over_sample_factor/pixel_parallel)
  val parallel_pixel_addrs = Array.tabulate(pixel_parallel){i=>
    pixel_cnt.value * pixel_parallel + i
  }.map(addressTrans)

  //  `result_ready` signal that all NLOS results have been stored and is ready to output
  //  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(img_in_q.valid.fall(False))
  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(store_in_en.fall(False)).clearWhen(pixel_cnt.willOverflow)
  val result_ready = RegNext(result_ready_prev) init False
  io.img_out.valid := RegNext(result_ready) init False
  for(i <- 0 until pixel_parallel){
    io.img_out.payload(i) := result_mem.readSync(parallel_pixel_addrs(i))
  }
  when(io.img_out.fire){
    pixel_cnt.increment()
  }
//  result_ready.clearWhen(pixel_cnt.willOverflow)

}
