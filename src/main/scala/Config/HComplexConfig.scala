package Config

import spinal.core._

case class HComplexConfig
(
  intw: Int,
  fracw: Int,
  useGauss: Boolean = true,
  real_high: Boolean = false
)
{
  def getDataWidth: Int = intw + fracw
  def getComplexWidth: Int = (intw + fracw) * 2
  def getDataBitRange: Range = getComplexWidth-1 downto 0
  def getRealBitRange: Range = if (real_high) {
    getComplexWidth-1 downto getDataWidth
  } else {
    getDataWidth-1 downto 0
  }
  def getImagBitRange: Range = if (real_high) {
    getDataWidth-1 downto 0
  } else {
    getComplexWidth-1 downto getDataWidth
  }
  def getDataResolution: Double = math.pow(2, -fracw)
  def getDataMaxValue: Double = math.pow(2, intw-1) - math.pow(2, -fracw)
  def getDataMinValue: Double = -math.pow(2, intw)
  def getDataValueRange: Vector[Double] = (1 to getDataWidth).map(_.toDouble).map(_ / math.pow(2, intw-1)).toVector

  def +(that: HComplexConfig): HComplexConfig = {
    require(this.useGauss == that.useGauss && this.real_high == that.real_high)
    HComplexConfig(
      intw = Math.max(this.intw, that.intw),
      fracw = Math.max(this.fracw, that.fracw),
      useGauss, real_high
    )
  }

  def *(that: HComplexConfig): HComplexConfig = {
    require(this.useGauss == that.useGauss && this.real_high == that.real_high)
    HComplexConfig(
      intw = this.intw + that.fracw,
      fracw = this.fracw + that.fracw,
      useGauss, real_high
    )
  }

  def ==(that: HComplexConfig): Boolean = {
    (that.intw == this.intw) && (that.fracw == this.fracw) && (that.useGauss == this.useGauss) && (that.real_high == this.real_high)
  }
}
