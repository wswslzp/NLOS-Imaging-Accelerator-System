package Fpga

import Config._
import Core.FFT2d.FFT2d._
import Util._
import spinal.core._
import spinal.lib._

/** FFT2d core does following three things
*  1. When df = (0, x), it computes the fft2d of input image, sends the result to both output `data_out` and the
*      internal memories.
*  2. When df = (>1, >1), it computes the ifft2d of median results from previous Sum(rsd kernel * fft2d(image)).
*  3. When df = (>0, x), it reads previous fft2d(image) results from internal memories and sends them to output
*     `data_out`
*/
case class FFT2dCore_v1(rsd_cfg: RsdKernelConfig, freq_factor: Int, depth_factor: Int) extends Component {
  val cfg = rsd_cfg.getFFT2dConfig

  val io = new Bundle {
    val dc = in UInt(log2Up(depth_factor) bit)
    val fc = in UInt(log2Up(freq_factor) bit)
    val push_start = in Bool() // TODO Check
    val fft2d_comp_done = out Bool()
    val ifft2d_comp_done = out Bool()
    val fft2d_out_sync = out Bool
    val data_in = slave(Flow(HComplex(rsd_cfg.getUinConfig))) // HCC(19,-3)
    val data_from_mac = slave(Flow(Vec(HComplex(rsd_cfg.getMACDatConfig), cfg.row))) // HCC(38,26)
    val data_to_mac = master(Flow(Vec(HComplex(rsd_cfg.getFUinConfig), cfg.row))) // HCC(38,-6)
    val data_to_final = master(Flow(HComplex(rsd_cfg.getResultConfig)))
  }

  val inverse = io.dc > 0 //&& !(io.dc === 1 && io.fc === 0)
  val mode = inverse
  val unified_cfg = io.data_from_mac.payload.head.config + io.data_in.payload.config // HCC(38,26)

  // ********** FFT2d Core *************
  val fft2d_inst = FFT2d.FFT2dv1(FFTConfig(unified_cfg, rsd_cfg.cols, rsd_cfg.rows))
  fft2d_inst.io.col_line_in << io.data_from_mac
  fft2d_inst.io.row_pix_in << io.data_in
  fft2d_inst.io.inverse := inverse
  fft2d_inst.io.mode := mode

  val fft_out_to_mac = fft2d_inst.io.col_line_out

  io.fft2d_comp_done := fft_out_to_mac.valid.fall(False)
  val fft2d_out_sync = fft_out_to_mac.valid.rise(False)
  io.fft2d_out_sync := fft2d_out_sync

  // *************** internal memory ***********************
  val col_addr_cnt_area = countUpFrom(
    (io.dc === 0) ? fft2d_out_sync | io.push_start, // reuse the counter // TODO Check
    0 until cfg.point,
    "col_addr_cnt"
  )
  // The current column address used to access the internal memory
  //  when d == 0, it generate address when fft out generated.
  //  when d > 0, it generate address to read out results.
  val col_addr_cnt = col_addr_cnt_area.cnt.value
  col_addr_cnt.setName("col_addr_cnt_value")
  val push_period = col_addr_cnt_area.cond_period
  push_period.setName("push_period")

  val int_mem = Array.fill(cfg.row)(
    Mem(Bits(fft_out_to_mac.payload.head.config.getComplexWidth bit), BigInt(cfg.point*freq_factor))
  )
  int_mem.foreach(_.addAttribute("ramstyle", "M20K"))
  val int_mem_address: UInt = io.fc * freq_factor + col_addr_cnt
  val dc_eq_0 = io.dc === 0
  val mem_out = Vec.fill(cfg.row)(HComplex(fft_out_to_mac.payload.head.config))//HCC(38,26)
  for(i <- int_mem.indices){
    mem_out(i) := int_mem(i).readWriteSync(
      address = int_mem_address.resize(log2Up(cfg.point * freq_factor)),
      data = dc_eq_0 ? fft_out_to_mac.payload(i).asBits | B(0).resized,
      enable = push_period,
      write = push_period & dc_eq_0
    )
  }

  val fft_to_rgca_channel = fft_out_to_mac.translateWith(
    Vec(fft_out_to_mac.payload.map(_.fixTo(io.data_to_mac.payload.head.config))) // HCC(38,-6)
  ).takeWhen(push_period) // TODO: Takewhen may be redundant
  when(io.dc === 0) {
    // The fft2d output is directly sent to output and int_mem
    // Delay one cycle after push_period
    io.data_to_mac <-< fft_to_rgca_channel
  } otherwise {
    io.data_to_mac.valid := RegNext(push_period, False) // data valid one cycle after address stream in.
    io.data_to_mac.payload := Vec.tabulate(mem_out.length){idx =>
      mem_out(idx).fixTo(io.data_to_mac.payload.head.config)
    }
  }

  // When inverse is activated, the ifft results will directly be sent to
  // output `data_to_final`
  val fft_out_to_final = fft2d_inst.io.row_pix_out
  val fft_to_final_channel = fft_out_to_final.translateWith(
    fft_out_to_final.payload.fixTo(io.data_to_final.payload.config) // HCC(38,26)
  ).takeWhen(inverse)// TODO: Takewhen may be redundant
  io.data_to_final << fft_to_final_channel // o_valid = i_valid & inverse
  io.ifft2d_comp_done := fft_to_final_channel.valid.fall(False)

}
