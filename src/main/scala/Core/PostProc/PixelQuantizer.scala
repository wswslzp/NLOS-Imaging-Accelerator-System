package Core.PostProc

import spinal.core._
import spinal.lib._

/**
 * Quantize the input complex pixel into SInt with specified bit width
 * @param in_bits pixel input bit width
 * @param out_bits output bit width
 */
case class PixelQuantizer(in_bits: Int, out_bits: Int) extends Component {
  val io = new Bundle {
    val dat_in = in UInt(in_bits bit)
    val dat_out = out UInt(out_bits bit)
  }
  // When `out_bits` < `in_bits`, only the upper `out_bits` bits will output
  //  to `dat_out`.
  // when `out_bits` > `in_bits`, then `0` will be appended at the rightmost
  //  of `dat_in` and output to `dat_out`.
  io.dat_out := io.dat_in.asBits.resizeLeft(out_bits)
}

object PixelQuantizer {
  def quanTo(dat_in: UInt, out_bits: Int): UInt = {
    val pixelQuantizer = PixelQuantizer(dat_in.getBitsWidth, out_bits)
    pixelQuantizer.io.dat_in := dat_in
    pixelQuantizer.io.dat_out
  }

  def q8(dat_in: UInt): UInt = quanTo(dat_in, 8)
}