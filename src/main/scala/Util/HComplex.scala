package Util

import spinal.core._
import scala.language.postfixOps
import Config._
import Util._

class Synthesizable(var flag: Boolean)
object Synthesizable {
  implicit var synthesizable = new Synthesizable(false)
  def setSimulation(x: Boolean): Unit = synthesizable.flag = !x
  def apply(flag: Boolean): Unit = {synthesizable.flag = flag}
}

case class HComplex(config:HComplexConfig) extends Bundle /*with Num[HComplex]*/{
  import MyUFix.toMyUFix
  import MySFix.toMySFix
  val dw = config.intw + config.fracw
  val real, imag = SFix(( config.intw - 1) exp, -config.fracw exp)
  val q_format = SQ(config.getDataWidth, config.fracw)

  def conj: HComplex = {
    val ret = HComplex(this.config)
    ret.real := this.real
    ret.imag.assignFromBits(( -this.imag.asBits.asSInt ).asBits)
    ret
  }

  def +(that: HComplex): HComplex = {
//    require(this.config == that.config)
    val result = HComplex(this.config + that.config)
    result.real := this.real + that.real
    result.imag := this.imag + that.imag
    result
  }

  def -(that: HComplex): HComplex = {
//    require(this.config == that.config)
    val result = HComplex(this.config + that.config)
    result.real := this.real - that.real
    result.imag := this.imag - that.imag
    result
  }

  def >>(that: UInt): HComplex = {
    val ret = HComplex(this.config)
    ret.real.assignFromBits(( real.asBits.asSInt |>> that ).asBits)
    ret.imag.assignFromBits(( imag.asBits.asSInt |>> that ).asBits)
    ret
  }

  def +(that: SInt): HComplex = {
    val ret = HComplex(this.config)
    ret.real := (this.real + that.toSFix).fixTo(this.q_format)
    ret.imag := this.imag
    ret
  }

  def *(that: HComplex)(implicit use_synthesizable_mul: Synthesizable): HComplex = {
    val result = HComplex(this.config * that.config)
    if (!use_synthesizable_mul.flag) {
      if(config.useGauss) {
        val k1 = ( (this.real + this.imag) * that.real )//.fixTo(q_format)
        val k2 = ( (that.imag - that.real) * this.real )//.fixTo(q_format)
        val k3 = ( (that.real + that.imag) * this.imag )//.fixTo(q_format)
        result.real := ( k1 - k3 ).fixTo(result.real.sq)
        result.imag := ( k1 + k2 ).fixTo(result.imag.sq)
      }else{
        result.real := ( this.real * that.real - this.imag * that.imag ).fixTo(result.real.sq)
        result.imag := ( this.real * that.imag + this.imag * that.real ).fixTo(result.imag.sq)
      }
    }
    else {
      if(config.useGauss) {
        val k1 = MulUnit(this.real + this.imag, that.real)//.fixTo(q_format)
        val k2 = MulUnit(that.imag - that.real, this.real)//.fixTo(q_format)
        val k3 = MulUnit(that.real + that.imag, this.imag)//.fixTo(q_format)
        result.real := ( k1 - k3 ).fixTo(result.real.sq)
        result.imag := ( k1 + k2 ).fixTo(result.imag.sq)
      }else{
        result.real := (MulUnit(this.real, that.real) - MulUnit(this.imag, that.imag)).fixTo(result.real.sq)
        result.imag := (MulUnit(this.real, that.imag) + MulUnit(this.imag, that.real)).fixTo(result.imag.sq)
      }
    }
    result
  }

  def /(that: SFix)(implicit use_synthesizable_div: Synthesizable): HComplex = {
    // complex number divide by the real number
    val ret_config = this.config + that.sq
    val ret = HComplex(ret_config)
    val real = this.real//.fixTo(ret_config.sq)
    val imag = this.imag//.fixTo(ret_config.sq)
    if (use_synthesizable_div.flag) {
      ret.real := DivUnit(real, that).fixTo(ret.real.sq)
      ret.imag := DivUnit(imag, that).fixTo(ret.imag.sq)
    }
    else {
      val sq_tmp = SQ(
        Math.max(this.config.getDataWidth, that.bitCount),
        Math.max(this.config.fracw, -that.minExp)
      )
      val ia_real = real.fixTo(sq_tmp)
      val ia_imag = imag.fixTo(sq_tmp)
      val ib = that.fixTo(sq_tmp)
      when(that.asBits.asUInt === U(0, that.bitCount bit)) {
        // When the divisor is zero, we set the value to the maximum
        // avoid popping up error signal.
        ret.real.assignFromBits(B(ret.config.getDataWidth bit, default -> true))
        ret.imag.assignFromBits(B(ret.config.getDataWidth bit, default -> true))
      }.otherwise {
        // the quotient is a signed integer
        // discard the remainder.
        // TODO: the remainder cannot be discarded.
        ret.real := ia_real / ib
        ret.imag := ia_imag / ib
      }
    }
    ret
  }

  def :=(that: HComplex): Unit = {
    if (this.real.sq.fraction < that.real.sq.fraction) {
//      SpinalInfo(s"this.sq = ${this.real.sq.toString()}")
//      SpinalInfo(s"that.sq = ${that.real.sq.toString()}, that's name is ${that.getName()}")
      this.real := that.real.fixTo(this.real.sq)
      this.imag := that.imag.fixTo(this.imag.sq)
    } else {
      this.real := that.real
      this.imag := that.imag
    }
  }

  def :=(that: Bits): Unit = {
    this := HComplex(this.config, that.resize(this.config.getComplexWidth))
  }
}

object HComplex {
  def apply(intw: Int, fracw: Int): HComplex = {
    HComplex(HComplexConfig(8, 8))
  }

  def apply(config: HComplexConfig, bits: Bits): HComplex = {
    val tmp = new HComplex(config)
    val dw = config.intw + config.fracw
    if (config.real_high) {
      tmp.real.assignFromBits(bits(dw*2-1 downto dw))
      tmp.imag.assignFromBits(bits(dw-1 downto 0))
      tmp
    } else {
      tmp.imag.assignFromBits(bits(dw*2-1 downto dw))
      tmp.real.assignFromBits(bits(dw-1 downto 0))
      tmp
    }
  }
}

object HC {
  def apply(real: Double, imag: Double): HComplex = {
    val ret = HComplex(16, 16)
    ret.real := real
    ret.imag := imag
    ret
  }

  def apply(that: UFix): HComplex = {
    val ret = HComplex(
      config = HComplexConfig(intw = that.maxExp, fracw = -that.minExp)
    )
    ret.real.assignFromBits(that.asBits)
    ret.imag := 0
    ret
  }
  def apply(that: SFix): HComplex = {
    val ret = HComplex(
      config = HComplexConfig(intw = that.maxExp + 1, fracw = -that.minExp)
    )
    ret.real := that
    ret.imag := 0
    ret
  }
  def apply(that: SInt): HComplex = {
    val ret = HComplex(
      config = HComplexConfig(intw = that.getBitsWidth, fracw = 0)
    )
    ret.real.raw := that
    ret.imag := 0
    ret
  }
  def apply(that: UInt): HComplex = {
    val ret = HComplex(
      config = HComplexConfig(intw = that.getBitsWidth - 1, fracw = 0)
    )
    ret.real.assignFromBits(that.asBits)
    ret.imag := 0
    ret
  }
}