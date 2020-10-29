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
case class FFT2dCore(cfg: FFTConfig, freq_factor: Int, depth_factor: Int) extends Component {
  val io = new Bundle {
    val dc = in UInt(log2Up(depth_factor) bit)
    val fc = in UInt(log2Up(freq_factor) bit)
    val push_ending = in Bool
    val fft2d_comp_done = out Bool()
    val data_in = slave(Flow(HComplex(cfg.hComplexConfig)))
    val data_to_mac = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
    val data_to_final = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
  }

  // TODO: The inverse signal may have problems.
  val inverse = io.dc > 1 && io.fc > 1
  val fft_out = fft2(io.data_in, inverse, cfg.row, cfg.point)
  io.fft2d_comp_done := fft_out.valid

  val col_addr_cnt_area = countUpFrom(
    (io.dc === 0) ? fft_out.valid | io.push_ending, // reuse the counter
    0 until cfg.point,
    "col_addr_cnt"
  )
  val col_addr_cnt = col_addr_cnt_area.cnt
  val push_period = col_addr_cnt_area.cond_period

  val int_mem = Array.fill(cfg.row)(
    Ram1rw(MemConfig(
      dw = cfg.hComplexConfig.getComplexWidth,
      aw = log2Up(cfg.row * freq_factor),
      vendor = Huali,
      name = "fft2d9k32bit8bank"
    ))
  )
  int_mem.head.addSimulationModel("tmp/simtmp/fft2d9k32bit8bank_ikos.v")

  // TODO: Timing degrading due to multiplication and addition
  val int_mem_address: UInt = io.fc * freq_factor + col_addr_cnt.value

  when(io.dc === 0) {
    // The fft2d output is directly sent to output and int_mem
    // Delay one cycle after push_period
    io.data_to_mac <-< fft_out.takeWhen(push_period)
    // When push start
    int_mem.zipWithIndex.foreach { case (ram1rw, i) =>
      ram1rw.io.ap.addr := int_mem_address
      ram1rw.io.ap.cs := push_period
      ram1rw.io.ap.bwe := default -> True
      ram1rw.io.dp.we := push_period
      ram1rw.io.dp.din := fft_out.payload(i).asBits
    }
  } otherwise {
    // The stored fft results are read out from internal SRAM.
    int_mem.zipWithIndex.foreach { case (ram1rw, i) =>
      ram1rw.io.ap.addr := int_mem_address
      ram1rw.io.ap.cs := push_period
      ram1rw.io.ap.bwe := default -> True
      ram1rw.io.dp.we := False
      ram1rw.io.dp.din := B(0).resized
      io.data_to_mac.payload(i) := ram1rw.io.dp.dout
    }
    io.data_to_mac.valid := RegNext(push_period, False) // data valid one cycle after address stream in.
  }

  // When inverse is activated, the ifft results will directly be sent to
  // output `data_to_final`
  io.data_to_final << fft_out.takeWhen(inverse)


}
