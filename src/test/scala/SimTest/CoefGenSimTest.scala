package SimTest

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Sim.RsdGenCoreArray._

object CoefGenSimTest extends App{
  val wave = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/wave.csv")
  println(s"wave is \n ${wave(0 to 3, 0 to 3).toString()}")
  val distance = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/distance.csv")
  println(s"distance is \n ${distance(0 to 3, 0 to 3).toString()}")
  val timeshift = LoadData.loadComplexMatrix(
    "tb/RsdGenCoreArray_tb/data/timeshift_real.csv",
    "tb/RsdGenCoreArray_tb/data/timeshift_imag.csv"
  )
  println(s"timeshift is \n ${timeshift(0 to 3, 0 to 3).toString()}")
  // TODO: The input impulse is 101 instead of 128/2, why?
  //  A new sample algorithm is represented, which increase the sample points' number on the impulse R.
  //  So, R != rows/2 or cols/2 !!
  //  what effect may be caused?
  val impulse_rad = LoadData.loadComplexMatrix(
    "tb/RsdGenCoreArray_tb/data/impulse_rad_real.csv",
    "tb/RsdGenCoreArray_tb/data/impulse_rad_imag.csv"
  )
  println(s"impulse_rad is \n ${impulse_rad(0 to 3, 0 to 3).toString()}")

  val coef = Computation.generateCoef(wave, distance, timeshift)
//  println(coef(0)(0 to 9, 0 to 9))
  println(s"kernel_coef is ${coef(0)(0 to 3, 0 to 3).map(_.real).toString()}")

  val rsd = Computation.generateRSDRadKernel(coef, impulse_rad)
//  println(rsd(0)(0 to 9, 0 to 9))
  println(s"rsd is \n ${rsd(0)(0)(0 to 9).map(_.real).toString()}")

  //The

}
