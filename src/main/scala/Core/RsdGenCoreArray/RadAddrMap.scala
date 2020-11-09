package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Config._

import scala.collection.mutable.ArrayBuffer

// TODO: Too large LUT here!
case class RadAddrMap(cfg: RsdKernelConfig) extends Component{
  val row_num = cfg.kernel_size.head
  val col_num = cfg.kernel_size.last

//  private def buildAddrMap(sample_point: Int, mapping_factor: Double = 1.1): Array[UInt] = {
//    // Return the address LUT
//    // The index of the LUT represents the linear address of the original image's pixels
//    // The value of the LUT represents the linear address of the radius of image.
//
//    val result_mat = ArrayBuffer.fill(row_num, col_num)(0)
//    for {
//      x <- 0 until row_num
//      y <- 0 until col_num
//    } {
//      val xp = (x + row_num/2) % row_num
//      val yp = (y + col_num/2) % col_num
//      result_mat(xp)(yp) = Math.max(
//        Math.min(
//          Math.sqrt(Math.pow(x - row_num/2, 2) + Math.pow(y - col_num/2, 2)) * mapping_factor,
//          sample_point - 1
//        ),
//        0
//      ).toInt
//    }
//    Array.tabulate(row_num * col_num){idx=>
//      val row = idx / row_num
//      val col = idx % row_num
//      U(result_mat(row)(col))
//    }
//  }
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
