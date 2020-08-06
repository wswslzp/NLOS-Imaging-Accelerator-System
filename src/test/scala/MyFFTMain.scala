import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

object MyFFTMain extends App{
  case class SFFT() extends Component {
    val data_in = slave(Flow(HComplex(8, 8)))
    val data_out = master(Flow(HComplex(8, 8)))

    data_out <> MyFFT.sfft(data_in, 128)
  }

  SpinalConfig(
    targetDirectory = "../../fft/SFFT_tb/rtl"
//    targetDirectory = "../../fft/myfft_tb/rtl"
  ).generateVerilog(
//    MyFFT(
//      length = 128,
//      cfg = HComplexConfig(8, 8),
//      use_pipeline = false
//    )
    SFFT()
  )
}
