import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi._
import Core._
import Util._
import Config._

object CoefLoadUnitMain extends App{
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    1, 2, 2
  )
  val axi_cfg: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = 32,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  var init_addr = 0
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog {
    val coef_load_unit = CoefLoadUnit(rsd_cfg, 2, init_addr, axi_cfg)
    init_addr += coef_load_unit.local_mem_manager.finalAddr
    coef_load_unit
  }
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(
    ImpLoadUnit(rsd_cfg, init_addr, axi_cfg)
  )
}
