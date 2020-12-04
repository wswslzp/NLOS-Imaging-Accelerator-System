package Core.FFT2d

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import FFT2d._

/** FFT2d core does following three things
*  1. When df = (0, x), it computes the fft2d of input image, sends the result to both output `data_out` and the
*      internal memories.
*  2. When df = (>1, >1), it computes the ifft2d of median results from previous Sum(rsd kernel * fft2d(image)).
*  3. When df = (>0, x), it reads previous fft2d(image) results from internal memories and sends them to output
*     `data_out`
*/
// TODO: Do angle quantization;
//  Need verification for actual running result.
//  Need verification for integration
case class FFT2dCore(cfg: FFTConfig, freq_factor: Int, depth_factor: Int) extends Component {
  val io = new Bundle {
    val dc = in UInt(log2Up(depth_factor) bit)
    val fc = in UInt(log2Up(freq_factor) bit)
    val push_ending = in Bool
    val fft2d_comp_done = out Bool()
    val fft2d_out_sync = out Bool
    val data_in = slave(Flow(HComplex(cfg.hComplexConfig)))
    val data_from_mac = slave(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
    val data_to_mac = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.row)))
//    val data_to_mac = master(Flow(Vec(Bool(), cfg.row)))
    val data_to_final = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.row)))
  }

  val inverse = io.dc > 0 //&& !(io.dc === 1 && io.fc === 0)

  //********* FFT2d Core *************
  val s2p_flow = Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
  s2p_flow.valid := countUpInside(io.data_in.valid, cfg.point).last
  s2p_flow.payload := Vec(History(io.data_in.payload, cfg.point, io.data_in.valid).reverse)
  val fft_data_in = inverse ? io.data_from_mac | s2p_flow
  val fft_out = fft2(fft_data_in, cfg.row)

//  // Binarize the `fft_out`
//  val fft_bin_out = fft_out.translateWith(
//    Vec.tabulate(fft_out.payload.length){i=>
//      fft_out.payload(i).real > 0
//    }
//  )
//
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

//  val int_mem = Array.fill(cfg.row)(
  val int_mem = Array.fill(4)(
    Ram1rw(MemConfig(
      dw = 32, //cfg.hComplexConfig.getComplexWidth,
      aw = log2Up(cfg.row * freq_factor), // quantize into one bit per pixel
      vendor = Huali,
      name = "fft2d9k32bit8bank",
      needBwe = false
    ))
  )
  int_mem.head.addSimulationModel("tmp/simtmp/fft2d9k32bit8bank_ikos.v")

  val int_mem_address: UInt = io.fc * freq_factor + col_addr_cnt

  when(io.dc === 0) {
    // The fft2d output is directly sent to output and int_mem
    // Delay one cycle after push_period
//    io.data_to_mac <-< fft_bin_out.takeWhen(push_period)
    io.data_to_rgca <-< fft_out.takeWhen(push_period)
    // When push start
    int_mem.zipWithIndex.foreach { case (ram1rw, i) =>
      ram1rw.io.ap.addr := int_mem_address
      ram1rw.io.ap.cs := push_period
      ram1rw.io.dp.we := push_period
      ram1rw.io.dp.din := fft_out.payload(i).asBits
      // TODO:
    }
  } otherwise {
    // The stored fft results are read out from internal SRAM.
    int_mem.zipWithIndex.foreach { case (ram1rw, i) =>
      ram1rw.io.ap.addr := int_mem_address
      ram1rw.io.ap.cs := push_period
      ram1rw.io.dp.we := False
      ram1rw.io.dp.din := B(0).resized
      // TODO:
      io.data_to_rgca.payload(i) := ram1rw.io.dp.dout
    }
    io.data_to_rgca.valid := RegNext(push_period, False) // data valid one cycle after address stream in.
  }

  // When inverse is activated, the ifft results will directly be sent to
  // output `data_to_final`
  io.data_to_final << fft_out.takeWhen(inverse) // o_valid = i_valid & inverse


}
