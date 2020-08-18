import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi._
import Core._
import Config._
import Core.RsdGenCoreArray.RsdKernelGen
import Util._

object RsdKernelGenMain extends App{
  val h_cfg = HComplexConfig(8, 8)
  val axi_config: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = h_cfg.getComplexWidth,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    1, 51
  )
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "rtl"
  ).generateVerilog(
    RsdKernelGen(rsd_cfg)
  )
}
