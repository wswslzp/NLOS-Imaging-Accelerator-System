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
    val fft2d_comp_done = out Bool()
    val data_in = slave(Flow(HComplex(cfg.hComplexConfig)))
    val data_out = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
  }

  // TODO: The inverse signal may have problems.
  val inverse = io.dc > 1 && io.fc > 1
  val fft_out = fft2(io.data_in, inverse, cfg.row, cfg.point)
  io.fft2d_comp_done := fft_out.valid

  val col_addr_cnt_area = countUpInside(fft_out.valid && (io.dc === 0), cfg.point, "col_addr_cnt")
  val col_addr_cnt = col_addr_cnt_area.cnt

  val int_mem = Array.fill(cfg.row)(
    Ram1rw(MemConfig(
      dw = cfg.hComplexConfig.getComplexWidth,
      aw = log2Up(cfg.row * freq_factor),
      vendor = Huali
    ))
  )

  val int_mem_address: UInt = io.dc * depth_factor + col_addr_cnt.value

  when(fft_out.valid) {
    when(io.dc === 0) {
      // The fft2d output is directly sent to output and int_mem
      io.data_out << fft_out
      int_mem.zipWithIndex.foreach { case (ram1rw, i) =>
        int_mem(i).io.ap.addr := RegNext(int_mem_address, U(0))
        int_mem(i).io.ap.cs := True
        int_mem(i).io.dp.we := True
        int_mem(i).io.dp.din := fft_out.payload(i).asBits
      }
    }
  }


}
