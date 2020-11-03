package UtilTest

import spinal.core._
import spinal.lib._

object HistoryTest extends App {

  case class HT() extends Component {
    val dati_valid = in Bool()
    val dati = in UInt (32 bit)
    val dato = out UInt (33 bit)

    val his: Vec[UInt] = History(dati, 10)
    val his_1: Vector[UInt] = his.zipWithIndex.map { case (d, i) =>
      d + i
    }.toVector

    val int_mem = Mem(UInt(8 bit), 32L)


    when(dati === 10) {
      int_mem(dati.resize(log2Up(32))) := his_1.last.resize(8)
    }

    dato := his_1.reduce(_ | _).resized
  }

  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(
    HT()
  )
}