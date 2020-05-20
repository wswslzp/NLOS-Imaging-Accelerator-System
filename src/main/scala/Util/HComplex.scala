package Util

import spinal.core._
import Config.HComplexConfig
import scala.language.postfixOps

case class HComplex(config:HComplexConfig) extends Bundle {
  val dw = config.intw + config.fracw
  val resizeRange = ( config.intw + config.fracw*2 - 1) downto config.fracw
  val real, imag = UInt(dw bit)//.tag(SQ(config.intw, config.fracw))

  def +(that: HComplex): HComplex = {
    require(this.config == that.config)
    val result = HComplex(this.config)
    result.real := this.real + that.real
    result.imag := this.imag + that.imag
    result
  }

  def *(that: HComplex): HComplex = {
    require(this.config == that.config)
    val result = HComplex(this.config)
    if(config.useGauss) {
      val k1 = ( (this.real + this.imag) * that.real ).fixTo(resizeRange)
      val k2 = ( (that.imag - that.real) * this.real ).fixTo(resizeRange)
      val k3 = ( (that.real + that.imag) * this.imag ).fixTo(resizeRange)
      result.real := k1 - k3
      result.imag := k1 + k2
      result
    }else{
      result.real := ( this.real * that.real - this.imag * that.imag ).fixTo(resizeRange)
      result.imag := ( this.real * that.imag + this.imag * that.real ).fixTo(resizeRange)
      result
    }
  }

  def :=(that: HComplex): Unit = {
    this.real := that.real
    this.imag := that.imag
  }

  def :=(that: Bits): Unit = {
    this := HComplex(this.config, that.resize(this.config.getComplexWidth))
  }
}

object HComplex {
  def apply(config: HComplexConfig, bits: Bits): HComplex = {
    val tmp = new HComplex(config)
    val dw = config.intw + config.fracw
    if (config.real_high) {
      tmp.real := bits(dw*2-1 downto dw).asUInt
      tmp.imag := bits(dw-1 downto 0).asUInt
      tmp
    } else {
      tmp.imag := bits(dw*2-1 downto dw).asUInt
      tmp.real := bits(dw-1 downto 0).asUInt
      tmp
    }
  }
}
