package Fpga.FFT2d

import Config.FFTConfig
import Util._
import spinal.core._
import spinal.lib._

case class IntMem(cfg: FFTConfig) extends Component {
  val io = new Bundle {
    val col_line_in = slave(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val row_pix_in = slave(
      Flow(HComplex(cfg.hComplexConfig))
    )
    val col_line_out = master(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val row_pix_out = master(
      Flow(HComplex(cfg.hComplexConfig))
    )
    val mode = in (Bool()) // 0: col line in/row pixel out ; 1: row pixel in/col line out
  }

  // Counter for column address
  val w_col_addr_incr = io.mode ? io.row_pix_in.valid | io.col_line_in.valid
  val w_col_addr = Counter(0, cfg.point, inc = w_col_addr_incr)

  // Counter for row address
  val w_row_addr_incr = io.mode & w_col_addr.willOverflow
  val w_row_addr = Counter(0, cfg.row, inc = w_row_addr_incr)

  val int_mem = Array.fill(cfg.row)(Mem(Bits(cfg.hComplexConfig.getComplexWidth bit), BigInt(cfg.point)))

  // write into int mem
  for(r <- 0 until cfg.row) {
    val write_data = io.mode ? io.row_pix_in.payload | io.col_line_in.payload(r)
    val write_en = (~io.mode) | (w_row_addr.value === r)
    int_mem(r).write(
      address = w_col_addr.value.resized,
      data = write_data.asBits,
      enable = write_en
    )
  }

  // read out from int mem
  // When half of int mem has been loaded for column line in or half of last row has been loaded
  //  for row pixel in, it's ready. mode = 0, col line in & row pixel out; 1: row pixel in & col line out
  val int_mem_ready = (w_col_addr === cfg.point/2) & ((!io.mode) | (w_row_addr === cfg.row))
  val r_pixel_addr_area = countUpFrom(int_mem_ready & (!io.mode), 0 until (cfg.row*cfg.point))
  val r_col_line_addr_area = countUpFrom(int_mem_ready & io.mode , 0 until cfg.point)

  val r_col_addr = io.mode ? r_col_line_addr_area.cnt.value | (r_pixel_addr_area.cnt.value % cfg.point)
  val r_row_addr = r_pixel_addr_area.cnt.value / cfg.point

  val read_datas = Vec.fill(cfg.row)(HComplex(cfg.hComplexConfig))
  for(r <- 0 until cfg.row) {
    val read_data = int_mem(r).readSync(
      address = r_col_addr.resized,
      enable = io.mode | (r_row_addr === r)
    )
    read_datas(r) := read_data
  }

  io.col_line_out.valid := r_col_line_addr_area.cond_period & io.mode
  io.col_line_out.payload := read_datas

  io.row_pix_out.valid := r_pixel_addr_area.cond_period & (~io.mode)
  io.row_pix_out.payload := read_datas(r_row_addr.resized)

}

