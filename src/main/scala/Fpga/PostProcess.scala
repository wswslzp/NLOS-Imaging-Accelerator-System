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
    val img_in = slave(Flow(
      Vec(HComplex(cfg.getResultConfig), col_num)
    ))
    val img_out = master(Stream(Vec.fill(pixel_parallel)(UInt(quant_bit_width bit))))
  }

  // ***************** abs(result) ************************************************
  val img_in_1 = io.img_in.stage()
  val img_in_abs = img_in_1.translateWith(Vec.tabulate(col_num){ idx=>
    val realp = img_in_1.payload(idx).real
    val imagp = img_in_1.payload(idx).imag
    val abs_real = (realp < 0) ? (-realp.asBits.asSInt) | realp.asBits.asSInt
    val abs_imag = (imagp < 0) ? (-imagp.asBits.asSInt) | imagp.asBits.asSInt
    ( abs_real + abs_imag ).asUInt
  })
  img_in_abs.simPublic()

  // ***************** quantize ***********************
  val img_in_abs_1 = img_in_abs.stage()
  val img_in_q = img_in_abs_1.translateWith(Vec.tabulate(col_num){idx=>
    img_in_abs_1.payload(idx).asBits.resizeLeft(quant_bit_width).asUInt
  })
  img_in_q.simPublic()

  // ***************** store and compare **************
  val os_rows = cfg.rows * over_sample_factor
  val os_cols = cfg.cols * over_sample_factor
  val result_mem = Vec.fill(os_rows)(
    Vec.fill(os_cols)(Reg(UInt(quant_bit_width bit)))
  )
  val row_addr = Counter(0, cfg.rows)
  val os_row_addr = ( row_addr.value * over_sample_factor ).resize(log2Up(os_rows))
  when(img_in_q.valid){
    row_addr.increment()
  }
  when(img_in_q.valid){
    for(c <- cfg.colRange){
      val os_col_addr = c * over_sample_factor
      val mem_lt_img_in = result_mem(os_row_addr)(os_col_addr) < img_in_q.payload(c)
      mem_lt_img_in.setName(s"mem_lt_img_in_$c")

      when(mem_lt_img_in){
        for(delta_c <- 0 until over_sample_factor){
          for(delta_r <- 0 until over_sample_factor){
            result_mem(os_row_addr + delta_r)(os_col_addr + delta_c) := img_in_q.payload(c)
          }
        }
      }

    }
  }
  result_mem.foreach(_.simPublic())

  // ***************** signal status *******************
  //  `nlos_comp_done` signal that previous NLOS task is done
  val nlos_comp_done = Reg(Bool()).init(False).setWhen(io.done).clearWhen(io.img_in.valid.rise(False))
  //  `result_ready` signal that all NLOS results have been stored and is ready to output
  val result_ready_prev = nlos_comp_done & Reg(Bool()).init(False).setWhen(img_in_q.valid.fall(False))
  val result_ready = RegNext(result_ready_prev) init False

  // ***************** interpolate and output **********
  // Counter for image ( osf * cfg.rows * osf * cfg.cols/pixel_parallel )
  val pixel_cnt = Counter(0, cfg.kernel_size.product * over_sample_factor * over_sample_factor/pixel_parallel)
  val pixel_cnt_row = pixel_cnt.value / (cfg.cols/pixel_parallel)
  val pixel_cnt_col = pixel_cnt.value % (cfg.cols/pixel_parallel)
  io.img_out.valid := result_ready
  for(i <- 0 until pixel_parallel){
    io.img_out.payload(i) := result_mem(pixel_cnt_row.resized)(( pixel_cnt_col * pixel_parallel + i ).resized)
  }
  when(io.img_out.fire){
    pixel_cnt.increment()
  }
  result_ready.clearWhen(pixel_cnt.willOverflow)

}
