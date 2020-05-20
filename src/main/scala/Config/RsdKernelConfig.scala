package Config

case class RsdKernelConfig(
                          hComplexConfig: HComplexConfig,
                          deltaw_factor: Int,
                          depth_factor: Int,
                          radius_factor: Int,
                          kernel_size: List[Int] = 128 :: 128 :: Nil,
                          less_mem_size: Boolean = true,
                          sub_mem_tag: Int = 0
                          ){
  require(kernel_size.length == 2)
  if (less_mem_size) {require(deltaw_factor == 1)}
}
