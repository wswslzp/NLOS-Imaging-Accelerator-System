package Config

case class FFTConfig
(
  hComplexConfig: HComplexConfig,
  point:Int,
  row:Int,
  use_pipeline: Boolean = true
)
