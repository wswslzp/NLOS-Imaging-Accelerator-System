package Core.FFT2d

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

case class FFT2dIntMem(
                      sim_model_file: String = "../tmp/sram_tb/rtl/fft2d9k32bit8bank_fast_func.v",
                      data_width: Int = 32,
                      addr_width: Int = 14,
                      mask_width: Int = 4
                      ) extends BlackBox {
  val io = new Bundle {
    val CLK = in Bool
    val Q = out UInt(data_width bit)
    val D = in UInt(data_width bit)
    val ADR = in UInt(addr_width bit)
    val RM = in UInt(mask_width bit)
    val WE = in Bool()
    val ME = in Bool()
    val RME = in Bool
    val TEST1 = in Bool
    val LS = in Bool
  }

  noIoPrefix()
  mapCurrentClockDomain(clock = io.CLK)
  addRTLPath(sim_model_file)

}
