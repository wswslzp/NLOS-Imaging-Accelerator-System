import spinal.core._
import spinal.lib._
import Core._
import Config._
import Util._

object PwMatMulMain extends App{
  val config = SpinalConfig(
    targetDirectory = "rtl"
  )
  SpinalVerilog(config)(
    PwMatMul(32, 256, 256)
  )
}
