import spinal.core._
import Core._
import Config._
import spinal.lib.bus.amba4.axi.Axi4Config

object CoefLoadUnitMain extends App{
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    1, 51, 70
  )
  val axi_cfg: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = 32,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  SpinalConfig(
    targetDirectory = "rtl"
  ).generateVerilog(
    CoefLoadUnit(rsd_cfg, 69, 0, axi_cfg)
  )
}