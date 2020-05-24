import spinal.core._
import Core.{CoefLoadUnit, ImpLoadUnit, RsdKernelGen}
import Config.{HComplexConfig, RsdKernelConfig}
import spinal.lib.bus.amba4.axi.Axi4Config

object RsdKernelGenMain extends App{
  val h_cfg = HComplexConfig(8, 8)
  val axi_config: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = h_cfg.getComplexWidth,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  val rsd_cfg = RsdKernelConfig(
    hComplexConfig = h_cfg,
    less_mem_size = false,
    deltaw_factor = 2,
    depth_factor = 50,
    radius_factor = 50
  )
  SpinalConfig(
    mode = Verilog,
    targetDirectory = "rtl"
  ).generateVerilog(
    RsdKernelGen(rsd_cfg)
//    CoefLoadUnit(rsd_cfg, 1, 0, axi_config)
//    ImpLoadUnit(rsd_cfg, 0, axi_config)
  )//.printPruned()
}
