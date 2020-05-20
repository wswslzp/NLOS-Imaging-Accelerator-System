package Util
import scala.util.Random

class SimpleMat(row: Int, col: Int) {
  val mat_data = Array.fill(row, col)(0)
  def construct(seed: Long): SimpleMat = {
    Random.setSeed(seed)
    for{
      i <- 0 until row
      j <- 0 until col
    }{
      mat_data(i)(j) = Random.nextInt(10)
    }
    this
  }
  def apply(x: Int)(y: Int): Int = mat_data(x)(y)
}
