import spinal.core._
import spinal.lib._

object HistoryTest extends App {
  case class HT() extends Component {
    val dati = in UInt(32 bit)
    val dato = out UInt(33 bit)

    val his: Vec[UInt] = History(dati, 10)
    val his_1: Vector[UInt] = his.zipWithIndex.map {case (d, i) =>
      d + i
    }.toVector

    dato := his_1.reduce(_ | _).resized
  }

  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(
    HT()
  )
}
