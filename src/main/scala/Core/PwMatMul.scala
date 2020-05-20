package Core

import spinal.core._
import spinal.lib.{master, slave}

case class PwMatMul(
                   width: Int,
                   row: Int,
                   col: Int
                   ) extends Component {
  val io = new Bundle {
    val mat_a = slave Flow(
      Vec(UInt(width bit), col)
    )
    val mat_b = slave Flow(
      Vec(UInt(width bit), col)
    )
    val mat_product = master Flow(
      Vec(UInt(width bit), col)
    )
  }

  val mat_a_r = io.mat_a.toReg()
  val mat_b_r = io.mat_b.toReg()

  for(i <- 0 until row) {
    io.mat_product.payload(i) := MulUnit(mat_a_r(i), mat_b_r(i))
  }
  io.mat_product.valid := RegNext( io.mat_a.valid & io.mat_b.valid )

}
