import Config._
import Core.FFT2d
import spinal.core._
import Util.Synthesizable._

import scala.language.postfixOps

object FFTMain extends App{
  val fft_config = FFTConfig(
    hComplexConfig = HComplexConfig(8, 8),
    point = 128,
    row = 128
  )
  setSimulation(true)
  val config = SpinalConfig(
    targetDirectory = "../NLOS_RTL",headerWithDate = true
  )
  SpinalVerilog(config)(
    FFT2d(fft_config)
  ).printPruned()
}
