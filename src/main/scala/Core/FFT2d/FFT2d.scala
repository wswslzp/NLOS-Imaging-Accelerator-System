package Core.FFT2d

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
  val fft_row: Flow[Vec[HComplex]] = fft(io.line_in, cfg.use_pipeline)
  fft_row.setName("fft_row")

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
  fft_col_in.valid := RegNext( col_addr_area.cond_period ) init False

  val fft_col_out: Flow[Vec[HComplex]] = fft(fft_col_in, cfg.use_pipeline)
  fft_col_out.setName("fft_col_out")
  fft_col_out >-> io.line_out

}

object FFT2d {
  /**
   * Do fft2d for an image.
   * Input one row per cycle, while activating `input.valid`
   * @param input input data flow carry with `valid` and `payload` data. The data is
   *              a vector of `HComplex` that represents a row of image.
   * @param row indicate the total row number of the image.
   * @return the output data flow carry with output data `payload` and `valid` signal.
   *         output one row per cycle
   */
  def fft2(input: Flow[Vec[HComplex]], row: Int): Flow[Vec[HComplex]] = {
    val hcfg = input.payload(0).config
    val point = input.payload.length
    val fft_config = FFTConfig(hcfg, point, row)
    val fft2d_core = FFT2d(fft_config)
    fft2d_core.io.line_in <> input
    fft2d_core.io.line_out
  }

  def fft2(input: Flow[Vec[HComplex]], inverse: Bool, row: Int, use_pipeline: Boolean = true): Flow[Vec[HComplex]] = {
    val hcfg = input.payload(0).config
    val point = input.payload.length
    val fft_config = FFTConfig(hcfg, point, row, use_pipeline)
    val fft2d_core = FFT2d(fft_config)
    fft2d_core.io.line_in <> input.translateWith(
      Vec(input.payload.map{dat=>
        inverse ? dat.conj | dat
      })
    )
    fft2d_core.io.line_out.translateWith(
      Vec(fft2d_core.io.line_out.payload.map{dat=>
        inverse ? dat.conj | dat
      })
    )
  }
  /**
   * Do fft2d for an image. Input one pixel per cycle, while activating `input.valid`
   * @param input input data flow carry with `valid` and `payload` data. The data is a
   *              HComplex data that represent a pixel of image.
   * @param row indicate the total row number of the input image
   * @param point indicate the total column number of one row of the input image
   * @return output one row per cycle
   */
  def fft2(input: Flow[HComplex], row: Int, point: Int): Flow[Vec[HComplex]] = {
    // The valid of input should be active during all the cycles of effective value.
    val hcfg = input.payload.config
    val fft2_in_flow = Flow(Vec(HComplex(hcfg), point))
    val data_in_row = Vec( History(input.payload, point, input.valid).reverse )
    fft2_in_flow.payload := data_in_row
    fft2_in_flow.valid := countUpInside(input.valid, point).last
    val fft_config = FFTConfig(hcfg, point, row, use_pipeline = false)
    val fft2d_core = FFT2d(fft_config)
    fft2d_core.io.line_in <> fft2_in_flow
    fft2d_core.io.line_out
  }

  /**
   * Do fft2d for an image. Input one pixel per cycle, while activating `input.valid`
   *
   * @param input input data flow carry with `valid` and `payload` data. The data is a
   *              HComplex data that represent a pixel of image.
   * @param inverse input signal to control whether do ifft2d for input image or fft2d.
   * @param row indicate the total row number of the input image
   * @param point indicate the total column number of one row of the input image
   * @return output one row per cycle
   */
  def fft2(input: Flow[HComplex], inverse: Bool, row: Int, point: Int): Flow[Vec[HComplex]] = {
    // The valid of input should be active during all the cycles of effective value.
    val hcfg = input.payload.config
    val fft2_in_flow = Flow(Vec(HComplex(hcfg), point))
    fft2_in_flow.valid := countUpInside(input.valid, point).last
    fft2_in_flow.payload := Vec(History(input.payload, point, input.valid).reverse.map{hcomp=>
      inverse ? hcomp.conj | hcomp
    })
    val fft_config = FFTConfig(hcfg, point, row)
    val fft2d_core = FFT2d(fft_config)
    fft2d_core.io.line_in <> fft2_in_flow
    fft2d_core.io.line_out.translateWith(
      Vec(fft2d_core.io.line_out.payload.map{hcomp=>
        inverse ? hcomp.conj | hcomp
      })
    )
  }
}

