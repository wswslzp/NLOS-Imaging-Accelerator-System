package Config

import spinal.core._

case class FFTConfig
(
  hComplexConfig: HComplexConfig,
  point:Int,
  row:Int,
  row_pipeline: Boolean = false,
  col_pipeline: Boolean = true
)

object FFTConfig {
  object FFTMode extends SpinalEnum(binarySequential) {
    val col_parallel, row_serial = newElement()
  }
  object ConjMode extends SpinalEnum(binarySequential) {
    val former_conj, back_conj = newElement()
  }
}