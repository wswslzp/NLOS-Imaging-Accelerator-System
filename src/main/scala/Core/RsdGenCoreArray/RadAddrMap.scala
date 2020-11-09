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

  pixelAddrTransforms.zipWithIndex.foreach{ case (transform, i) =>
    transform.io.col_addr := io.col_addr
    io.pixel_addrs(i) := transform.io.rad_addr
  }
}
