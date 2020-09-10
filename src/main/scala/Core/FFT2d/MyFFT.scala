package Core.FFT2d

import Config._
import Util._
import spinal.core._
import spinal.lib._

case class MyFFT(length: Int, cfg: HComplexConfig, use_pipeline: Boolean = true, serial: Boolean = false) extends Component {
  val io = new Bundle {
    val data_in: Flow[Vec[HComplex]] = in( Flow( Vec(HComplex(cfg), length) ) )
    val data_out = out ( Flow( Vec(HComplex(cfg), length) ) )
  }

  val data_in = if (serial) {
    io.data_in.payload
  } else {
    io.data_in.toReg()
  }
//  val data_in: Vec[HComplex] = io.data_in.toReg()

  val twiddle_factor_table: Vec[HComplex] = TwiddleFactorTable.getw(length, cfg)

  val data_reorder: Vec[HComplex] = cloneOf(data_in)
  private def bitReverse(dat: Int, width: Int): Int = {
    var ret: Int = 0
    var dat_bin = dat.toBinaryString
    dat_bin = ( "0" * (width - dat_bin.length) + dat_bin )//.reverse
    dat_bin.zipWithIndex.foreach{case(c, i) =>
      ret += c.toString.toInt << i
    }
    ret
  }

  data_in.zipWithIndex.foreach { case(dat, idx) =>
    // Important note: When Vec's address is UInt, the data to write in cannot be Int
    // Unless it's declare as Reg!!!! Otherwise, LATCH will be detected!!!
    data_reorder(bitReverse(idx, log2Up(length))) := dat
  }

  val LL =  (1 to log2Up(length)).map(1 << _)//.map(U(_))
  val rr =  LL.map(length / _)
  val LL2 =  LL.map(_ / 2)

  if (use_pipeline) {
    val data_mid =  Reg(Vec(cloneOf(data_in), log2Up(length)+1)).setWeakName("data_mid")
    data_mid(0) := data_reorder
    for {
      level <- 1 to log2Up(length)
      l = LL(level-1)
      r = rr(level-1)
      l2 = LL2(level-1)
      k <- 0 until r
      j <- 0 until l2
    } {
      val tmp = twiddle_factor_table(l2 - 1 + j) * data_mid(level-1)(k*l + j + l2)
      data_mid(level)(k*l + j + l2) := (data_mid(level-1)(k*l + j) - tmp) >> U(1)
      data_mid(level)(k*l + j) := (data_mid(level-1)(k*l + j) + tmp) >> U(1)
    }

    io.data_out.valid := Delay(io.data_in.valid, log2Up(length)+2, init = False)
    io.data_out.payload := data_mid(log2Up(length))
  }

  else {
    val current_level = countUpFrom(
      RegNext(io.data_in.valid, init = False), 0 until log2Up(length)+1, "current_level"
    ).cnt
    current_level.setName("current_level")
    val data_mid = Reg(cloneOf(data_in)).setWeakName("data_mid")

    when(current_level === 0) {
      data_mid := data_reorder
    } otherwise {
      for {
        q <- 0 until log2Up(length)
        l = LL(q)
        r = rr(q)
        l2 = LL2(q)
        k <- 0 until r
        j <- 0 until l2
      } {
        when(current_level === (q+1)) {
          // LONG critical timing path!! need further pipeline
          val tmp = twiddle_factor_table(l2 - 1 + j) * data_mid(k*l + j + l2)
          data_mid(k*l + j + l2) := (data_mid(k*l + j) - tmp) >> U(1)
          data_mid(k*l + j) := (data_mid(k*l + j) + tmp) >> U(1)
        }
      }
    }
    io.data_out.valid := RegNext(current_level.willOverflow, init = False)
    io.data_out.payload := data_mid

  }

}

object MyFFT {
  def fft(input: Flow[Vec[HComplex]], use_pipeline: Boolean = true): Flow[Vec[HComplex]] = {
    // support for any points FFT
    val cfg = input.payload(0).config
    val length = input.payload.length
    val points = 1 << log2Up(length)
    val add0_input = Flow(Vec(HComplex(cfg), points))
    add0_input.valid := input.valid
    add0_input.payload.zipWithIndex.foreach { case (dat, i) =>
      if (i < length) {
        dat := input.payload(i)
      } else {
        dat := 0
      }
    }
    val fft_core = MyFFT(points, cfg, use_pipeline)
    fft_core.io.data_in <> input
    fft_core.io.data_out
  }

  def fft(input: Flow[HComplex], length: Int, use_pipeline: Boolean): Flow[Vec[HComplex]] = {
    val sdata_in = Vec( History(input.payload, length, input.valid).reverse ).setWeakName("sdata_in")
    val fft_input_flow = Flow(cloneOf(sdata_in)).setWeakName("fft_input_flow")
    fft_input_flow.payload := sdata_in
    fft_input_flow.valid := countUpFrom(input.valid, 0 until length).cnt.willOverflow
    fft(fft_input_flow, use_pipeline).setWeakName("sdata_out")
  }

  def sfft(input: Flow[HComplex], length: Int): Flow[HComplex] = {
    val sdata_in = Vec( History(input.payload, length, input.valid).reverse ).setWeakName("sdata_in")
    val fft_input_flow = Flow(cloneOf(sdata_in)).setWeakName("fft_input_flow")
    fft_input_flow.payload := sdata_in
    fft_input_flow.valid := countUpInside(input.valid, length).last
//    fft_input_flow.valid := countUpFrom(input.valid, 0 until length).cnt.willOverflow
    val sdata_out = fft(fft_input_flow, false).setName("sdata_out")
    val sdata_out_regs_addr_area = countUpFrom(RegNext( sdata_out.valid , init = False), 0 until length, name = "sdata_out_regs_addr_area")
    val sdata_out_regs = sdata_out.toReg().setName("sdata_out_regs")
    val output = Flow(HComplex(sdata_out.payload(0).config)).setName("output")
    output.payload := sdata_out_regs(sdata_out_regs_addr_area.cnt.value)
    output.valid := sdata_out_regs_addr_area.cond_period
    output
  }
  //def fft_latency(length: Int): Int = log2Up(length) + 2
}
