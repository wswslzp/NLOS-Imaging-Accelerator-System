import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Core.LoadUnit._
import spinal.lib.bus.amba4.axi.Axi4Config

object TimeshiftLoadUnitMain extends App{
  val wave_cfg = HComplexConfig(8, 8)
  val distance_cfg = HComplexConfig(8, 8)
  val timeshift_cfg = HComplexConfig(-4, 20)
  val coef_cfg = HComplexConfig(-4, 20)
  val imp_cfg = HComplexConfig(5, 11)

  val rsd_cfg = RsdKernelConfig(
    wave_cfg, distance_cfg,timeshift_cfg, coef_cfg, imp_cfg, 1, 2
  )

  val axi_cfg = Axi4Config(
    addressWidth = 32, dataWidth = 32, idWidth = 4, useId = true, useBurst = true, useCache = false,
    useLast = true, useRegion = false, useLock = false, useLen = true, useSize = true, useQos = false,
    useResp = true, useProt = false, useStrb = false
  )

  SpinalConfig(
    targetDirectory = "../NLOS_RTL",
    headerWithDate = true
  ).generateVerilog(
    TimeshiftLoadUnit(
      cfg = rsd_cfg,
      init_addr = 0,
      axi_config = axi_cfg
    )
  )


}
