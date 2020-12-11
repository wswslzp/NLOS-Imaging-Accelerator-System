package Config

import Sim.RsdGenCoreArray.LoadData
import breeze.linalg.DenseMatrix
import breeze.math.Complex
import spinal.lib.bus.amba4.axi.Axi4Config

case class RsdKernelConfig
(
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  coef_cfg: HComplexConfig,
  imp_cfg: HComplexConfig,
  depth_factor: Int,
  radius_factor: Int,
  freq_factor: Int = 1,
  kernel_size: List[Int] = 128 :: 128 :: Nil,
  impulse_sample_point: Int = 101,
  less_mem_size: Boolean = true,
  sub_mem_tag: Int = 0,
  fpga_impl: Boolean = false
){
  require(kernel_size.length == 2)
  def rows: Int = kernel_size.head
  def cols: Int = kernel_size.last

  def getKernelConfig: HComplexConfig = coef_cfg * imp_cfg/2 // HCC(0, 16)

  def getUinConfig: HComplexConfig = HComplexConfig(14, -6)

  def getFUinConfig: HComplexConfig = getUinConfig <> 2 // HCC(16, -4)

  def getMACDatConfig: HComplexConfig = getKernelConfig * getFUinConfig/2 // HCC(16, 12)

  def getResultConfig: HComplexConfig = getMACDatConfig <> 2

  def getFFT2dConfig: FFTConfig = FFTConfig(getUinConfig, cols, rows)

  def depthRange: Range = 0 until depth_factor
  def radiusRange: Range = 0 until radius_factor
  def freqRange: Range = 0 until freq_factor
  def rLengthRange: Range = 0 until impulse_sample_point
  def rowRange: Range = 0 until rows
  def colRange: Range = 0 until cols

  override def toString: String = {
    s"""
       |depth = $depth_factor
       |radius = $radius_factor
       |freq = $freq_factor
       |kernel_size = $kernel_size
       |impulse_sample_point = $impulse_sample_point
       |wave_cfg: {
       |  intw: ${wave_cfg.intw},
       |  fracw: ${wave_cfg.fracw},
       |}
       |distance_cfg: {
       |  intw: ${distance_cfg.intw},
       |  fracw: ${distance_cfg.fracw},
       |}
       |coef_cfg: {
       |  intw: ${coef_cfg.intw},
       |  fracw: ${coef_cfg.fracw},
       |}
       |imp_cfg: {
       |  intw: ${imp_cfg.intw},
       |  fracw: ${imp_cfg.fracw},
       |}
       |""".stripMargin
  }
}

object RsdKernelConfig {
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")// wave(radius, depth)
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")// distance(freq, depth)
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows,
    fpga_impl = true
  )
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )// timeshift(freq, depth)

  //impulse: (impulse_sample_point, radius)
  val impulse: DenseMatrix[Double] = LoadData.loadDoubleMatrix(
  "src/test/resource/data/impulse_rad_real.csv"
  )
  val uin = Array.tabulate(rsd_cfg.freq_factor){idx=>
    LoadData.loadComplexMatrix(
      real_part_filename = s"src/test/resource/data/real/uin_${idx+1}.csv",
      imag_part_filename = s"src/test/resource/data/imag/uin_${idx+1}.csv"
    )
  }
  implicit val axi_config: Axi4Config = Axi4Config(
    addressWidth = 32, dataWidth = 32,
    idWidth = 4, useRegion = false, useLock = false, useCache = false, useQos = false,
    useProt = false, useId = true, useBurst = true, useLen = true, useLast = true,
    useResp = true, useSize = true, useStrb = false
  )
}
