package Config

import spinal.lib.bus.amba4.axi.Axi4Config

case class RsdKernelConfig
(
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  coef_cfg: HComplexConfig,
  imp_cfg: HComplexConfig,
//  point_facotr: Int,
  depth_factor: Int,
  radius_factor: Int,
  freq_factor: Int = 1,
  kernel_size: List[Int] = 128 :: 128 :: Nil,
  impulse_sample_point: Int = 101,
  less_mem_size: Boolean = true,
  sub_mem_tag: Int = 0
){
  require(kernel_size.length == 2)
  def getKernelConfig: HComplexConfig = coef_cfg * imp_cfg
}

object RsdKernelConfig {
  implicit val axi_config: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = 32,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false, useId = true, useBurst = true, useLen = true, useLast = true,
    useResp = true, useSize = true, useStrb = false
  )
}
