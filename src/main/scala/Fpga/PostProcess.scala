package Fpga

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config._
import Core.PostProc.PixelQuant
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
                        over_sample_factor: Int = 2//,
//                        pixel_parallel: Int = 2
                      ) extends Component{
  require(( quant_bit_width >= 8 ) && ( over_sample_factor >= 1 ) )
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last
  val io = new Bundle {
    val done = in Bool()
    val img_in = slave(Flow( HComplex(cfg.getResultConfig) )) // HC(6,12)
//    val img_out = master(Stream(Vec.fill(pixel_parallel)(UInt(quant_bit_width bit))))
    val img_out = master(Stream(UInt(quant_bit_width bit)))
    val pp_done = out Bool()
  }

  // ***************** abs(result) ************************************************
  val img_in_1 = io.img_in.stage()
  val img_in_abs = img_in_1.translateWith(img_in_1.payload.abs)
  img_in_abs.simPublic()

  // ***************** quantize ***********************
  val img_in_abs_1 = img_in_abs.stage()
  val img_in_q = img_in_abs_1
  img_in_q.simPublic()

  // record the upper bound and lower bound of input image pixel
  val init_max = UF(img_in_q.payload.minValue, img_in_q.payload.maxExp exp, img_in_q.payload.minExp exp)
  val init_min = UF(img_in_q.payload.maxValue, img_in_q.payload.maxExp exp, img_in_q.payload.minExp exp)
  val img_in_abs_max = Reg(cloneOf(img_in_q.payload)) init init_max
  val get_max_flag = img_in_q.valid & ( img_in_q.payload > img_in_abs_max )
  when(get_max_flag) { // todo why max eq finally 0?
    img_in_abs_max := img_in_q.payload
  } elsewhen(io.pp_done) {
    img_in_abs_max := init_max
  }
  val img_in_abs_min = Reg(cloneOf(img_in_q.payload)) init init_min
  val get_min_flag = img_in_q.valid & ( img_in_q.payload < img_in_abs_min )
  when(get_min_flag) {
    img_in_abs_min := img_in_q.payload
  } elsewhen(io.pp_done) {
    img_in_abs_min := init_min
  }


  // ***************** store and compare **************
  val os_rows = cfg.rows * over_sample_factor
  val os_cols = cfg.cols * over_sample_factor
  val result_mem = Mem(cloneOf(img_in_q.payload), BigInt(cfg.rows * cfg.cols))
  result_mem.init(Seq.fill(cfg.rows * cfg.cols)(init_max))
  val pixel_addr = Counter(0 until cfg.rows*cfg.cols)
  when(img_in_q.valid){
    pixel_addr.increment()
  }

  val mem_raddr_sel = Bool()
  val output_pix_addr = UInt(16 bit)
  val mem_rdata = result_mem.readSync(
    address = ( mem_raddr_sel ? output_pix_addr | pixel_addr.value ).resized
  )

  val q_pix = RegNext(img_in_q.payload)
//  val mem_prev_pix = result_mem.readSync(pixel_addr)
//  val qin_larger = mem_prev_pix < q_pix
  val qin_larger = mem_rdata < q_pix
  val store_in_en = RegNext(img_in_q.valid) init False
  val w_pixel_addr = RegNext(pixel_addr.value) init 0
  result_mem.write(
    address = w_pixel_addr,
    data = qin_larger ? q_pix | mem_rdata,
    enable = store_in_en
  )

  // ***************** signal status *******************
  //  `nlos_comp_done` signal that previous NLOS task is done
  val nlos_comp_done = Reg(Bool()).init(False).setWhen(io.done).clearWhen(io.img_in.valid.rise(False))

  // ***************** interpolate, fliplr and output **********
  // Counter for image ( osf * cfg.rows * osf * cfg.cols/pixel_parallel )
  def addressTrans(os_pix_addr: UInt): UInt = {
    val os_row = os_pix_addr / (cfg.cols * over_sample_factor)
    val os_col = (cfg.cols*over_sample_factor) - os_pix_addr % (cfg.cols * over_sample_factor)
    val nos_row = os_row / over_sample_factor
    val nos_col = os_col / over_sample_factor
    nos_row * cfg.cols + nos_col
  }

  val pixel_cnt = Counter(0 until cfg.kernel_size.product * over_sample_factor * over_sample_factor)
//  val output_pix_addr = addressTrans(pixel_cnt.value)
  output_pix_addr := addressTrans(pixel_cnt.value)

  //  `result_ready` signal that all NLOS results have been stored and is ready to output
  //  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(img_in_q.valid.fall(False))
  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(store_in_en.fall(False)).clearWhen(pixel_cnt.willOverflow)
  val result_ready = RegNext(result_ready_prev) init False
  mem_raddr_sel := result_ready
  val pix_bfq_valid = RegNext(result_ready) init False
  val quan_cfg = HComplexConfig(img_in_q.payload.maxExp, -img_in_q.payload.minExp)
  val quantizer = PixelQuant(quan_cfg, quant_bit_width)
//  val pix_bfq = result_mem.readSync(output_pix_addr.resized)
//  pix_bfq.setName("pix_bfq")
  quantizer.io.upper_bound := img_in_abs_max
  quantizer.io.lower_bound := img_in_abs_min
  quantizer.io.pix_in.valid := pix_bfq_valid
//  quantizer.io.pix_in.payload := pix_bfq // todo pix_bfq has value but the io_pix_in_payload is zero constant?
  quantizer.io.pix_in.payload := mem_rdata
  io.img_out.payload := quantizer.io.pix_out.payload
  io.img_out.valid := quantizer.io.pix_out.valid
  when(io.img_out.fire){
    pixel_cnt.increment()
  }

  io.pp_done := io.img_out.valid.fall(False)

}
