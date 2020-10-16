package Core.FFT2d

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager._
import FFT2d._

case class FFT2dCore(cfg: FFTConfig, freq_factor: Int) extends Component {
  val io = new Bundle {
    val fc_eq_0 = in Bool()
    val dc_eq_0 = in Bool()
    val fft2d_comp_done = out Bool()
    val data_in = slave(Flow(HComplex(cfg.hComplexConfig)))
    val data_out = master(Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point)))
  }

  val fft_out: Flow[Vec[HComplex]] = fft2(io.data_in, cfg.row, cfg.point)
  val col_addr_cnt_area = countUpInside(fft_out.valid && io.dc_eq_0, cfg.point, "col_addr_cnt")
  val col_addr_cnt = col_addr_cnt_area.cnt

  val int_mems = Array.fill(cfg.row)(
    Ram1rw(MemConfig(
      dw = cfg.hComplexConfig.getComplexWidth,
      aw = log2Up(cfg.row * freq_factor),
      vendor = Huali
    ))
  )

  when(fft_out.valid) {
    when(io.dc_eq_0 === False) {
      io.data_out << fft_out
      int_mems.zipWithIndex.foreach { case (ram1rw, i) =>
        ???
      }
    }
  }


}
