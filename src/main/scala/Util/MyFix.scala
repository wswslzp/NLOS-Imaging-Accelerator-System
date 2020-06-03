package Util
import spinal.core._
import spinal.lib._

case class MyUFix(width: Int) {
  val bit_vec = UInt(width bit)
  var fraction = 0

  def uq: QFormat = UQ(width, fraction)
  def maxExp: ExpNumber = (width - fraction) exp
  def minExp: ExpNumber = -fraction exp

  def fixTo(q: QFormat, roundType: RoundType): UFix = {
    require(!q.signed)
    val ret = UFix((q.width - q.fraction) exp, -q.fraction exp)
    val left_zeros_num = Math.max(
      (q.width - q.fraction) - (this.uq.width - this.uq.fraction), 0
    )
    val right_zeros_num = Math.max(
      q.fraction - this.uq.fraction, 0
    )
    val bit_vec_tmp = (
      B(left_zeros_num bit, default -> false) ## bit_vec.asBits ## B(right_zeros_num bit, default -> false)
    ).asUInt
    val uq_tmp = UQ(
      this.uq.width + left_zeros_num + right_zeros_num,
      this.uq.fraction + right_zeros_num
    )
    ret.assignFromBits(bit_vec_tmp.tag(uq_tmp).fixTo(q, roundType).asBits)
    ret
  }

  def fixTo(q: QFormat): UFix = {
    fixTo(q, getFixRound())
  }
}

object MyUFix {

  implicit def toMyUFix(that: UInt): MyUFix = {
    val ret = new MyUFix(that.getBitsWidth)
    ret.bit_vec := that
    ret.fraction = that.Q.fraction
    ret
  }

  implicit def toMyUFix(that: UFix): MyUFix = {
    val ret = new MyUFix(that.bitCount)
    ret.bit_vec := that.raw
    ret.fraction = -that.minExp
    ret
  }
}

case class MySFix(width: Int) {
  val bit_vec = SInt()
  var fraction = 0

  def sq: QFormat = SQ(width, fraction)
  def maxExpv: ExpNumber = (width - 1 - fraction) exp
  def minExpv: ExpNumber = -fraction exp

  // Need to explicit call the converter function
  def /(that: SFix)(implicit syn: Synthesizable): SFix = {
    if (syn.flag) {
      val a = SFix(width - 1 - fraction exp, -fraction exp)
      a.assignFromBits(bit_vec.asBits)
      DivUnit(a, that)
    }
    else {
      // unify the q_format
      val uni_q = SQ(
        Math.max(width, that.bitCount),
        Math.max(fraction, -that.minExp)
      )
      val bit_vec_t = bit_vec.tag(sq).fixTo(uni_q)
      val b = that.asBits.asSInt.tag(
        SQ(that.bitCount, -that.minExp)
      ).fixTo(uni_q)
      // append zeros to the right
      val a: SInt = bit_vec_t @@ S(bit_vec_t.getWidth bit, default -> false)
      val quo = a / that.asBits.asSInt //>> bit_vec_t.getWidth // ret has the same q_format as a
      val ret = SFix(uni_q.width - 1 - uni_q.fraction exp, -uni_q.fraction exp)
      ret.assignFromBits(
        quo.tag(SQ(2*bit_vec_t.getWidth, bit_vec_t.getWidth)).fixTo(SQ(bit_vec_t.getWidth, bit_vec_t.getWidth/2)).asBits
      )
      ret
    }
  }

  def %(that: SFix)(implicit syn: Synthesizable): SFix = {
    if (syn.flag) {
      val a = SFix(maxExpv, minExpv)
      a.assignFromBits(bit_vec.asBits)
      DivUnit.mod(a, that)
    }
    else {
      val uni_q = SQ(
        Math.max(width, that.bitCount),
        Math.max(fraction, -that.minExp)
      )
      val bit_vec_t = bit_vec.tag(sq).fixTo(uni_q)
      val a: SInt = bit_vec_t
      val rem = a % that.asBits.asSInt
      val mid = SFix(uni_q.width - 1 - uni_q.fraction exp, -uni_q.fraction exp)
      val ret = cloneOf(mid)
      mid.assignFromBits(
        rem.asBits
      )
      when(a.msb) {
        ret := mid + that
      } otherwise {
        ret := mid
      }
      ret
    }
  }

  def fixTo(q: QFormat, roundType: RoundType): SFix = {
    require(q.signed)
    val ret = SFix((q.width - q.fraction - 1) exp, -q.fraction exp)// an extra signed bit in integer
    val left_signs_num = Math.max(
      (q.width - q.fraction) - (this.sq.width - this.sq.fraction), 0
    )
    val right_zeros_num = Math.max(
      q.fraction - this.sq.fraction, 0
    )
    val bit_vec_tmp = (
      B(left_signs_num bit, default -> bit_vec.msb) ## bit_vec.asBits ## B(right_zeros_num bit, default -> false)
    ).asSInt
    val sq_tmp = SQ(
      this.sq.width + left_signs_num + right_zeros_num,
      this.sq.fraction + right_zeros_num
    )
    ret.assignFromBits(
      bit_vec_tmp.tag(sq_tmp).fixTo(q, roundType).asBits
    )
    ret
  }

  def fixTo(q: QFormat): SFix = {
    fixTo(q, getFixRound())
  }
}

object MySFix {

  implicit def toMySFix(that: SInt): MySFix = {
    val ret = new MySFix(that.getBitsWidth)
    ret.bit_vec := that
    ret.fraction = that.Q.fraction
    ret
  }

  implicit def toMySFix(that: SFix): MySFix = {
    val ret = new MySFix(that.bitCount)
    ret.bit_vec := that.raw
    ret.fraction = -that.minExp
    ret
  }

}
