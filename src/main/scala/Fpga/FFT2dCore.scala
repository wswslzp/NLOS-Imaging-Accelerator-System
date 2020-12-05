package Fpga

import Config._
import Util.MemManager._
import Util._
import spinal.core._
import spinal.lib._
import Core.FFT2d.FFT2d._

/** FFT2d core does following three things
*  1. When df = (0, x), it computes the fft2d of input image, sends the result to both output `data_out` and the
*      internal memories.
*  2. When df = (>1, >1), it computes the ifft2d of median results from previous Sum(rsd kernel * fft2d(image)).
*  3. When df = (>0, x), it reads previous fft2d(image) results from internal memories and sends them to output
*     `data_out`
*/
case class FFT2dCore(rsd_cfg: RsdKernelConfig, freq_factor: Int, depth_factor: Int) extends Component {
  val cfg = rsd_cfg.getFFT2dConfig

  val io = new Bundle {
    val dc = in UInt(log2Up(depth_factor) bit)
    val fc = in UInt(log2Up(freq_factor) bit)
    val push_ending = in Bool
    val fft2d_comp_done = out Bool()
    val fft2d_out_sync = out Bool
    val data_in = slave(Flow(HComplex(rsd_cfg.getUinConfig)))
    // TODO: `data_from_mac` config needs to be modified.
    val data_from_mac = slave(Flow(Vec(HComplex(rsd_cfg.getMACDatConfig), cfg.row)))
    // TODO: `data_to_rgca` not Fuin
    val data_to_rgca = master(Flow(Vec(HComplex(rsd_cfg.getUinConfig), cfg.row)))
    val data_to_final = master(Flow(Vec(HComplex(rsd_cfg.getResultConfig), cfg.point)))
  }

  val inverse = io.dc > 0 //&& !(io.dc === 1 && io.fc === 0)

  //********* FFT2d Core *************
  val s2p_flow = Flow(Vec(HComplex(io.data_in.payload.config), cfg.point))
  s2p_flow.valid := countUpInside(io.data_in.valid, cfg.point).last
  s2p_flow.payload := Vec(History(io.data_in.payload, cfg.point, io.data_in.valid).reverse)
  val unified_cfg = io.data_from_mac.payload.head.config + io.data_in.payload.config
  val data_from_mac = io.data_from_mac.translateWith(
    Vec(io.data_from_mac.payload.map(_.fixTo(unified_cfg)))
  )
  val data_from_in = s2p_flow.translateWith(
    Vec(s2p_flow.payload.map(_.fixTo(unified_cfg)))
  )
//  val fft_data_in = inverse ? io.data_from_mac | s2p_flow
  val fft_data_in = inverse ? data_from_mac | data_from_in
  val fft_out = fft2(fft_data_in, inverse, cfg.row)

  io.fft2d_comp_done := fft_out.valid
  val fft2d_out_sync = fft_out.valid.rise(False)
  io.fft2d_out_sync := fft2d_out_sync

  val push_start1 = RegNext(io.push_ending, init = False)
  val col_addr_cnt_area = countUpFrom(
    (io.dc === 0) ? fft2d_out_sync | push_start1, // reuse the counter
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
    Mem(Bits(fft_out.payload.head.config.getComplexWidth bit), BigInt(cfg.point*freq_factor))
  )
  int_mem.foreach(_.addAttribute("ramstyle", "M20K"))
  val int_mem_address: UInt = io.fc * freq_factor + col_addr_cnt
  val dc_eq_0 = io.dc === 0
//  val mem_out = Vec.fill(cfg.row)(HComplex(cfg.hComplexConfig))
  val mem_out = Vec.fill(cfg.row)(HComplex(io.data_to_rgca.payload.head.config))
  for(i <- int_mem.indices){
    mem_out(i) := int_mem(i).readWriteSync(
      address = int_mem_address,
      data = dc_eq_0 ? fft_out.payload(i).asBits | B(0).resized,
      enable = push_period,
      write = push_period & dc_eq_0
    )
  }

//  val fft_to_rgca_channel = fft_out.takeWhen(push_period)
//  val fft_to_rgca_channel = cloneOf(io.data_to_rgca).translateFrom(fft_out){(this_chn, that_chn)=>
//    for(i <- this_chn.indices) this_chn(i) := that_chn(i)
//  }.takeWhen(push_period)
//  val fft_to_final_channel = cloneOf(io.data_to_final).translateFrom(fft_out){(this_chn, that_chn)=>
//    for(i <- this_chn.indices) this_chn(i) := that_chn(i)
//  }.takeWhen(inverse)

  val fft_to_rgca_channel = fft_out.translateWith(
    Vec(fft_out.payload.map(_.fixTo(io.data_to_rgca.payload.head.config)))
  ).takeWhen(push_period)
  val fft_to_final_channel = fft_out.translateWith(
    Vec(fft_out.payload.map(_.fixTo(io.data_to_final.payload.head.config)))
  ).takeWhen(inverse)
  when(io.dc === 0) {
    // The fft2d output is directly sent to output and int_mem
    // Delay one cycle after push_period
    io.data_to_rgca <-< fft_to_rgca_channel
  } otherwise {
    io.data_to_rgca.valid := RegNext(push_period, False) // data valid one cycle after address stream in.
    io.data_to_rgca.payload := mem_out
  }

  // When inverse is activated, the ifft results will directly be sent to
  // output `data_to_final`
  io.data_to_final << fft_to_final_channel // o_valid = i_valid & inverse


}
