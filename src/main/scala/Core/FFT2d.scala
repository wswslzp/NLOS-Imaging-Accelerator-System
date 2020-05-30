package Core

import Config.FFTConfig
import Util._
import spinal.core._
import spinal.lib._

case class FFT2d(cfg: FFTConfig) extends Component {
  import MyFFT.fft
  val io = new Bundle {
    val line_in = slave(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
    val line_out= master(
      Flow(Vec(HComplex(cfg.hComplexConfig), cfg.point))
    )
  }

  // do the row fft
  val fft_row: Flow[Vec[HComplex]] = fft(io.line_in).setName("fft_row")

  // declare a reg array, and push the data into it
  val img_reg_array = Reg(
    Vec(Vec(HComplex(cfg.hComplexConfig), cfg.point), cfg.row)
  )
  val row_addr: Counter = Counter(0 until cfg.row, inc = fft_row.valid)
  when(fft_row.valid) {
    img_reg_array(row_addr) := fft_row.payload
  }

  val col_addr_area = countUpFrom(row_addr.willOverflow, 0 until cfg.point, "col_addr")
  val col_addr = RegNext( col_addr_area.cnt.value )
  val fft_col_in = Flow(
    Vec(HComplex(cfg.hComplexConfig), cfg.row)
  )
  fft_col_in.payload.zipWithIndex.foreach { case(dat, idx) =>
    dat := img_reg_array(idx)(col_addr)
  }
  fft_col_in.valid := RegNext( col_addr_area.cond_period )

  val fft_col_out: Flow[Vec[HComplex]] = fft(fft_col_in).setName("fft_col_in")
  fft_col_out >-> io.line_out

}

object FFT2d {
  def fft2(input: Flow[Vec[HComplex]], row: Int): Flow[Vec[HComplex]] = {
    val hcfg = input.payload(0).config
    val point = input.payload.length
    val fft_config = FFTConfig(hcfg, point, row)
    val fft2d_core = FFT2d(fft_config)
    fft2d_core.io.line_in <> input
    fft2d_core.io.line_out
  }
}

//case class FFT2d(config:FFTConfig) extends Component {
//  val io = new Bundle {
//    val next = in Bool()
//    val next_out = out Bool()
//    val line_in = in(Vec(UInt(config.data_width bit), config.fft_point))
//    val line_out = out(Vec(UInt(config.data_width bit), config.fft_point))
//  }
//
//  // instantiate two fft core
//  val fft_core_row = FFTWrapper(width = config.data_width, point = config.fft_point)
//  val fft_core_col_array = List.fill(config.fft_point)(FFTWrapper(width = config.data_width,
//    point = config.row))
//  fft_core_row.io.next <> io.next
//  for (idx <- 0 until config.fft_point) {
//    fft_core_row.io.data_in(idx) <> io.line_in(idx)
//  }
//
//  // because the fft core's delay is not constant and next_out
//  // of the fft core do not align with the result
//  // it should be define in the fft config and count up to
//  // signal the reg to store the intermediate data
//  val row_fft_done = Delay(io.next, config.fft_delay, init = False)// initial state for control signal is needed.
//  row_fft_done.setName("row_fft_done")
//
//  // the reg array to hold all the intermediate data
//  val img_reg_array = Vec(Vec(Reg( UInt(config.data_width bit) ), config.fft_point), config.row)
//  val current_row_addr = Counter(0 until config.row)
//  val fft_core_col_start = current_row_addr.willOverflow //& row_fft_done
//  val col_fft_done = RegNext( countUpFrom(fft_core_col_start, 0 until config.fft_delay).cnt.willOverflow )
//  col_fft_done.setName("col_fft_done")
//  when(row_fft_done) {
//    for(ii <- 0 until config.fft_point) {
//      img_reg_array(current_row_addr)(ii) := complexScale( fft_core_row.io.data_out(ii) , log2Up(config.fft_point))
//    }
////    img_reg_array(current_row_addr) := Vec( fft_core_row.io.data_out.map(complexScale(_, log2Up( config.fft_point ))) )
//    current_row_addr.increment()
//  }.elsewhen(col_fft_done) {
//    for(ii <- 0 until config.row) {
//      for(jj <- 0 until config.fft_point) {
//        img_reg_array(ii)(jj) := complexScale( fft_core_col_array(jj).io.data_out(ii), log2Up( config.row ))
//      }
//    }
//  }
//
//  // when reg array is filled, the reg array begin to send
//  // data to the fft col core
//  for(ii <- 0 until config.fft_point) {
//    fft_core_col_array(ii).io.next := fft_core_col_start
//    for(jj <- 0 until config.row) {
//      fft_core_col_array(ii).io.data_in(jj) := img_reg_array(jj)(ii)
//    }
//  }
//
//  val fft_done = RegNext(col_fft_done)
//  //io.next_out := fft_done
//  io.next_out := countUpFrom(fft_done, 0 until config.fft_point).cond_period
//  val out_row_addr = countUpFrom(fft_done, 0 until config.row).cnt
//  out_row_addr.setName("out_row_addr")
//  io.line_out := img_reg_array(out_row_addr)
//
//}
