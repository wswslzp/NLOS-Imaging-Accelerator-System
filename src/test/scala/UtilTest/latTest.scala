package UtilTest

import spinal.core._
import spinal.lib._

object latTest extends App {

  case class LatTest() extends Component {
    val io = new Bundle {
      val ia = in Bool()
      val ib = in Bool()
      val ob = out Bits (2 bit)
    }

    val a = RegInit(UInt(3 bit))

    when(io.ia) {
      a := U"3'b001"
    }

    when(io.ib) {
      a := U"3'b010"
    }

    val ia3ob = Delay(io.ia, 3)
    val ia5ob = Delay(io.ia, 5)
    val ib2ob = Delay(io.ib, 2)

    io.ob(0) := ia3ob | ia5ob
    io.ob(1) := ib2ob


    val lat_iaob = LatencyAnalysis(io.ia, io.ob).toInt
    val lat_ibob = LatencyAnalysis(io.ib, io.ob).toInt
    println(s"The latency between ia and ob is $lat_iaob, and the ib and ob is $lat_ibob")
  }

  SpinalConfig(
    targetDirectory = "rtl1"
  ).generateVerilog(LatTest())

}
