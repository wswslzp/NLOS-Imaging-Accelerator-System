package Config

case class FFTConfig
(
  hComplexConfig: HComplexConfig,
  point:Int,
  row:Int,
  row_pipeline: Boolean = false,
  col_pipeline: Boolean = true
)
