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
    val img_in = slave(Flow( HComplex(cfg.getResultConfig) ))
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
  when(img_in_q.valid & ( img_in_q.payload > img_in_abs_max )) {
    img_in_abs_max := img_in_q.payload
  }
  val img_in_abs_min = Reg(cloneOf(img_in_q.payload)) init init_min
  when(img_in_q.valid & ( img_in_q.payload < img_in_abs_min )) {
    img_in_abs_min := img_in_q.payload
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

  val q_pix = RegNext(img_in_q.payload)
  val mem_prev_pix = result_mem.readSync(pixel_addr)
  val qin_larger = mem_prev_pix < q_pix
  val store_in_en = RegNext(img_in_q.valid) init False
  val w_pixel_addr = RegNext(pixel_addr.value) init 0
  result_mem.write(
    address = w_pixel_addr,
    data = qin_larger ? q_pix | mem_prev_pix,
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
  val output_pix_addr = pixel_cnt.value
//  val parallel_pixel_addrs = Array.tabulate(pixel_parallel){i=>
//    pixel_cnt.value * pixel_parallel + i
//  }.map(addressTrans)

  //  `result_ready` signal that all NLOS results have been stored and is ready to output
  //  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(img_in_q.valid.fall(False))
  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(store_in_en.fall(False)).clearWhen(pixel_cnt.willOverflow)
  val result_ready = RegNext(result_ready_prev) init False
  val pix_bfq_valid = RegNext(result_ready) init False
  val quan_cfg = HComplexConfig(img_in_q.payload.maxExp, -img_in_q.payload.minExp)
//  val quantizers = Array.fill(2)(PixelQuant(quan_cfg, quant_bit_width))
  val quantizer = PixelQuant(quan_cfg, quant_bit_width)
//  for(i <- 0 until pixel_parallel){
    val pix_bfq = result_mem.readSync(output_pix_addr)
    quantizer.io.upper_bound := img_in_abs_max
    quantizer.io.lower_bound := img_in_abs_min
    quantizer.io.pix_in.valid := pix_bfq_valid
    quantizer.io.pix_in.payload := pix_bfq
    io.img_out.payload := quantizer.io.pix_out.payload
//  }
  io.img_out.valid := quantizer.io.pix_out.valid
  when(io.img_out.fire){
    pixel_cnt.increment()
  }

  io.pp_done := io.img_out.valid.fall(False)

}
