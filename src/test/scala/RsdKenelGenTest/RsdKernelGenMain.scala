package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray.RsdKernelGen
import spinal.core._
import spinal.lib.bus.amba4.axi.Axi4Config
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import Sim.SimFix._
import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.math.Complex
import spinal.core.sim._

// TODO: Test RsdKernelGen !!
object RsdKernelGenMain extends App{
  val h_cfg = HComplexConfig(8, 8)
  val axi_config: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = h_cfg.getComplexWidth,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false
  )
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse: DenseMatrix[Complex] = LoadData.loadComplexMatrix(
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
  )
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-4, 20),
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows
  )
  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)

  SimConfig
    .withWave
    .noOptimisation
    .workspacePath("tb")
    .compile(RsdKernelGen(rsd_cfg))
    .doSim("RsdKernelGen_tb"){dut=>
      
    }

}
