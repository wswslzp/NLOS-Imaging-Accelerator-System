import Core.PwMatMul
import spinal.core._

object PwMatMulMain extends App{
  val config = SpinalConfig(
    targetDirectory = "rtl"
  )
  SpinalVerilog(config)(
    PwMatMul(32, 256, 256)
  )
}
