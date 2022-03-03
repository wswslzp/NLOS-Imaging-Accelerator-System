package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Config._
import Util.SqrtImpl._

case class PixelAddrTransform(
                               row_addr: Int,
                               cfg: RsdKernelConfig
                             ) extends Component {
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last
  val io = new Bundle {
    val col_addr2 = in UInt(log2Up(cfg.kernel_size.last)*2 bit)
    val rad_addr = out UInt(log2Up(cfg.impulse_sample_point) bit)
  }

  val g_row_addr = (row_addr + row_num/2) % row_num - row_num/2
//  val g_col_addr = cloneOf(io.col_addr)
//  when(io.col_addr < col_num/2){
//    g_col_addr := io.col_addr
//  } otherwise {
//    g_col_addr := ( col_num - io.col_addr ).resized
//  }

  val g_row_addr2 = g_row_addr * g_row_addr
//  val g_col_addr2 = g_col_addr * g_col_addr

  val g_tmp_addr = io.col_addr2 + g_row_addr2

  val g_sqrt_addr = CompareSqrt.sqrt(g_tmp_addr)

  // TODO: The factor of 1.125 is only suitable for kernel size of 128
  io.rad_addr := ( g_sqrt_addr + (g_sqrt_addr >> 3) ).resized // multiply with 1.1

}
