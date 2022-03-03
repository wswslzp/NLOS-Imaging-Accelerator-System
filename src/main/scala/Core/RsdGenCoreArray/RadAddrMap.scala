package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Config._

import scala.collection.mutable.ArrayBuffer

case class RadAddrMap(cfg: RsdKernelConfig) extends Component{
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last

  val io = new Bundle {
    val col_addr = in UInt(log2Up(col_num) bit)
    val pixel_addrs = out Vec(UInt(log2Up(cfg.impulse_sample_point) bit), cfg.kernel_size.head)
  }

  val pixelAddrTransforms = Array.tabulate(cfg.kernel_size.head){ row_addr=>
    PixelAddrTransform(row_addr, cfg)
  }

  // todo here move the square op outward, eliminate the redundant multipliers.
  //  **high fanout leads to high latency.**
  val g_col_addr = cloneOf(io.col_addr)
  when(io.col_addr < col_num/2){
    g_col_addr := io.col_addr
  } otherwise {
    g_col_addr := ( col_num - io.col_addr ).resized
  }

  val g_col_addr2 = g_col_addr * g_col_addr

  pixelAddrTransforms.zipWithIndex.foreach{ case (transform, i) =>
//    transform.io.col_addr := io.col_addr
    transform.io.col_addr2 := g_col_addr2
    io.pixel_addrs(i) := (transform.io.rad_addr < (cfg.impulse_sample_point - 1)) ? transform.io.rad_addr | U(cfg.impulse_sample_point - 1)
  }
}
