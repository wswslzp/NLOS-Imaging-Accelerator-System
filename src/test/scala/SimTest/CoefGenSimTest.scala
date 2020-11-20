package SimTest

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Sim.RsdGenCoreArray._
import breeze.linalg.csvwrite
import java.io._

object CoefGenSimTest extends App{
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
//  csvwrite(new File("src/test/scala/SimTest/wave_wr.csv"), wave)
  println(s"wave size is (${wave.rows}, ${wave.cols})")
  println(s"wave is \n ${wave(20 to 23, 20 to 23).toString()}")
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  println(s"distance size is (${distance.rows}, ${distance.cols})")
  println(s"distance is \n ${distance(20 to 23, 20 to 23).toString()}")
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  println(s"timeshift size is (${timeshift.rows}, ${timeshift.cols})")
  println(s"timeshift is \n ${timeshift(20 to 23, 20 to 23).toString()}")
  val impulse_rad = LoadData.loadComplexMatrix(
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
  )
  println(s"rsd size is (${impulse_rad.rows}, ${impulse_rad.cols})")
  println(s"impulse_rad is \n ${impulse_rad(20 to 23, 20 to 23).toString()}")

  val coef = Computation.generateCoef(wave, distance, timeshift)
  println(s"coef size is (${coef.length}, ${coef.head.rows}, ${coef.head.cols})")
  println(s"kernel_coef is ${coef(0)(20 to 23, 20 to 23).map(_.real).toString()}")

}
