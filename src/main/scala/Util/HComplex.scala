package Util

import spinal.core._
import scala.language.postfixOps
import Config._
import Util._

class Synthesizable(var flag: Boolean)
object Synthesizable {
  implicit var synthesizable = new Synthesizable(false)
  def apply(flag: Boolean): Unit = {synthesizable.flag = flag}
}

class HComplexMulStage(var stage: Int)
object HComplexMulStage {
  implicit var hComplexMulStage = new HComplexMulStage(0)
  def apply(stage: Int): Unit = {hComplexMulStage.stage = stage}
}

case class HComplex(config:HComplexConfig) extends Bundle /*with Num[HComplex]*/{
  import MyUFix.toMyUFix
  import MySFix.toMySFix
  val dw = config.intw + config.fracw
  val real, imag = SFix(( config.intw - 1) exp, -config.fracw exp)
  val q_format = SQ(config.getDataWidth, config.fracw)

  override def asBits: Bits = {
    if(config.real_high) real.asBits ## imag.asBits
    else imag.asBits ## real.asBits
  }

  def conj: HComplex = {
    val ret = HComplex(this.config)
    ret.real := this.real
    ret.imag.assignFromBits(( -this.imag.asBits.asSInt ).asBits)
    ret
  }

  def abs: UFix = {
    val ret = UFix(config.intw exp, -config.fracw exp)
    val abs_real = (real < 0) ? (-real.asBits.asSInt) | (real.asBits.asSInt)
    val abs_imag = (imag < 0) ? (-imag.asBits.asSInt) | (imag.asBits.asSInt)
    ret.assignFromBits((abs_real.asUInt + abs_imag.asUInt).asBits)
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

  def >>(that: Int): HComplex = {
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

  def *(that: SFix)(implicit use_synthesizable_mul: Synthesizable): HComplex = {
    val real_part = this.real * that
    val imag_part = this.imag * that
    val ret = HComplex(
      intw = real_part.getBitsWidth-real_part.fraction,
      fracw = real_part.fraction
    )
    ret.real := real_part
    ret.imag := imag_part
    ret
  }

  // No pipeline for multiplication
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

  // One stage multiplication
  // TODO: Multi-stage multiplication
  def *\*(that: HComplex)(implicit hComplexMulStage: HComplexMulStage): HComplex = {
    SpinalInfo(s"Current multiplication stage is ${hComplexMulStage.stage}")
    val result = HComplex(this.config * that.config)
    if(config.useGauss) {
      if(hComplexMulStage.stage == 0){
        val k1 = (this.real + this.imag) * that.real
        val k2 = (that.imag - that.real) * this.real
        val k3 = (that.real + that.imag) * this.imag
        result.real := ( k1 - k3 ).fixTo(result.real.sq)
        result.imag := ( k1 + k2 ).fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 1){
        val k1 = RegNext( (this.real + this.imag) * that.real )
        val k2 = RegNext( (that.imag - that.real) * this.real )
        val k3 = RegNext( (that.real + that.imag) * this.imag )
        result.real := ( k1 - k3 ).fixTo(result.real.sq)
        result.imag := ( k1 + k2 ).fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 2){
        val k1 = RegNext(this.real + this.imag)
        val k2 = RegNext(that.imag - that.real)
        val k3 = RegNext(that.real + that.imag)
        val l1 = RegNext(k1 * that.real)
        val l2 = RegNext(k2 * this.real)
        val l3 = RegNext(k3 * this.imag)
        result.real := (l1 - l3).fixTo(result.real.sq)
        result.imag := (l1 + l2).fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 3){
        val k1 = RegNext(this.real + this.imag)
        val k2 = RegNext(that.imag - that.real)
        val k3 = RegNext(that.real + that.imag)
        val l1 = RegNext(k1 * that.real)
        val l2 = RegNext(k2 * this.real)
        val l3 = RegNext(k3 * this.imag)
        val r1 = RegNext(l1 - l3)
        val r2 = RegNext(l1 + l2)
        result.real := r1.fixTo(result.real.sq)
        result.imag := r2.fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 4){
        val k1 = RegNext(this.real + this.imag).setName("k1")
        val k2 = RegNext(that.imag - that.real).setName("k2")
        val k3 = RegNext(that.real + that.imag).setName("k3")
        val l1 = RegNext(k1 * that.real).setName("l1")
        val l2 = RegNext(k2 * this.real).setName("l2")
        val l3 = RegNext(k3 * this.imag).setName("l3")
        val r1 = RegNext(l1 - l3).setName("r1")
        val r2 = RegNext(l1 + l2).setName("r2")
        result.real := RegNext( r1.fixTo(result.real.sq) )
        result.imag := RegNext( r2.fixTo(result.imag.sq) )
      } else {
        SpinalError(s"Invalid stage ${hComplexMulStage.stage} for Gauss multiplication")
      }
    }else{
      if(hComplexMulStage.stage == 0){
        result.real := ( this.real * that.real - this.imag * that.imag ).fixTo(result.real.sq)
        result.imag := ( this.real * that.imag + this.imag * that.real ).fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 1){
        val rr = RegNext(this.real * that.real)
        val ri = RegNext(this.real * that.imag)
        val ir = RegNext(this.imag * that.real)
        val ii = RegNext(this.imag * that.imag)
        result.real := (rr - ii).fixTo(result.real.sq)
        result.imag := (ri + ir).fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 2){
        val rr = RegNext(this.real * that.real)
        val ri = RegNext(this.real * that.imag)
        val ir = RegNext(this.imag * that.real)
        val ii = RegNext(this.imag * that.imag)
        val k1 = RegNext(rr - ii)
        val k2 = RegNext(ri + ir)
        result.real := k1.fixTo(result.real.sq)
        result.imag := k2.fixTo(result.imag.sq)
      } else if (hComplexMulStage.stage == 3){
        val rr = RegNext(this.real * that.real)
        val ri = RegNext(this.real * that.imag)
        val ir = RegNext(this.imag * that.real)
        val ii = RegNext(this.imag * that.imag)
        val k1 = RegNext(rr - ii)
        val k2 = RegNext(ri + ir)
        result.real := RegNext( k1.fixTo(result.real.sq) )
        result.imag := RegNext( k2.fixTo(result.imag.sq) )
      } else {
        SpinalError(s"Invalid stage ${hComplexMulStage.stage} for non-Gauss multiplication")
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
        ret.real := ia_real / ib
        ret.imag := ia_imag / ib
      }
    }
    ret
  }

  // TODO: When using assignment `:=` provided by spinallib e.g. Flow/Stream/Vec assignment
  //  This assignment method below will be skip and directly use the assignment from SFix.
  def :=(that: HComplex): Unit = {
    if (this.real.sq.fraction < that.real.sq.fraction) {
//      SpinalInfo(s"this.sq = ${this.real.sq.toString()}")
//      SpinalInfo(s"that.sq = ${that.real.sq.toString()}, that's name is ${that.getName()}")
      this.real := that.real.fixTo(this.real.sq)
      this.imag := that.imag.fixTo(this.imag.sq)
    } else {
      this.real := that.real.fixTo(this.real.sq)
      this.imag := that.imag.fixTo(this.imag.sq)
//      this.real := that.real
//      this.imag := that.imag
    }
  }

  def fixTo(cfg: HComplexConfig): HComplex = {
    val ret = HComplex(cfg)
    ret := this
    ret
  }

  def :=(that: Bits): Unit = {
    this := HComplex(this.config, that.resize(this.config.getComplexWidth))
  }
}

object HComplex {
  def apply(intw: Int, fracw: Int): HComplex = {
    HComplex(HComplexConfig(intw, fracw))
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
  def apply(real: Double, imag: Double, intw: Int = 16, fracw: Int = 16): HComplex = {
    val ret = HComplex(intw, fracw)
    ret.real := real
    ret.imag := imag
    ret
  }

  def apply(real: Double, imag: Double, cfg: HComplexConfig): HComplex = {
    val ret = HComplex(cfg)
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