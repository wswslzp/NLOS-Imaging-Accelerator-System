package Util

import spinal.core._
import spinal.lib._
import Sim.loadDoubleMatrix

case class HotMap() extends Component {
  val io = new Bundle {
    val gray = in UInt(8 bit)
    val red = out UInt(8 bit)
    val green = out UInt(8 bit)
    val blue = out UInt(8 bit)
  }

  val hot_map = loadDoubleMatrix("src/main/resource/hotmap.csv")
  io.red := io.gray.muxList(
    for(i <- 0 until 256) yield (i, U( hot_map(i, 0).toInt ))
  )

  io.green := io.gray.muxList(
    for(i <- 0 until 256) yield (i, U( hot_map(i, 1).toInt ))
  )

  io.blue := io.gray.muxList(
    for(i <- 0 until 256) yield (i, U( hot_map(i, 2).toInt ))
  )
}
