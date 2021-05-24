package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Util._
import Config._

object MulDivMain extends App{
  FpgaImpl(true)
  case class MulDivT() extends Component {
    val io = new Bundle {
      val a = in(HComplex(HComplexConfig(16, 16)))
      val b = in(HComplex(HComplexConfig(16, 16)))
      val mul_sel = in Bool()
      val res = out(HComplex(HComplexConfig(16, 16)))
    }

    val mul_res = RegNext(io.a.doMulOp(io.b)(new Synthesizable(true)))
    val div_res = RegNext(io.a./(io.b.real)(new Synthesizable(true)))
    val mul_sel = Delay(io.mul_sel, 9, init = False)
//    io.res := mul_sel ? mul_res | div_res //todo never use ?| to mux HComplex
    when(mul_sel) {
      io.res := mul_res
    } otherwise {
      io.res := div_res
    }
  }

  SpinalConfig(
    targetDirectory = "../fpga/MulDivTest_tb/rtl"
  ).generateVerilog(
    MulDivT()
  )
}
