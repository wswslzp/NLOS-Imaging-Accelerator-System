package SimTest

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Sim.RsdGenCoreArray._

object CoefGenSimTest extends App{
  val wave = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/wave.csv")
  val distance = LoadData.loadDoubleMatrix("tb/RsdGenCoreArray_tb/data/distance.csv")
  val timeshift = LoadData.loadComplexMatrix(
    "tb/RsdGenCoreArray_tb/data/timeshift_real.csv",
    "tb/RsdGenCoreArray_tb/data/timeshift_imag.csv"
  )
  val impulse_rad = LoadData.loadComplexMatrix(
    "tb/RsdGenCoreArray_tb/data/kernel_rad_real.csv",
    "tb/RsdGenCoreArray_tb/data/kernel_rad_imag.csv"
  )

  val coef = Computation.generateCoef(wave, distance, timeshift)
  println(coef(0)(0 to 9, 0 to 9))

  val impulse = Computation.restoreImpl(impulse_rad)
  val rsd = Computation.generateRSDkernel(coef, impulse)
  println(rsd(0)(0 to 9, 0 to 9))

}
