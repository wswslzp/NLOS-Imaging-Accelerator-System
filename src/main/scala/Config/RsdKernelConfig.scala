package Config

case class RsdKernelConfig
(
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  coef_cfg: HComplexConfig,
  imp_cfg: HComplexConfig,
  deltaw_factor: Int,
  depth_factor: Int,
  radius_factor: Int,
  kernel_size: List[Int] = 128 :: 128 :: Nil,
  less_mem_size: Boolean = true,
  sub_mem_tag: Int = 0
){
  require(kernel_size.length == 2)
  if (less_mem_size) {require(deltaw_factor == 1)}

  def getKernelConfig: HComplexConfig = coef_cfg * imp_cfg
}
