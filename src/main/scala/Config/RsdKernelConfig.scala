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
//  point_facotr: Int,
  depth_factor: Int,
  radius_factor: Int,
  freq_factor: Int = 1,
  kernel_size: List[Int] = 128 :: 128 :: Nil,
  impulse_sample_point: Int = 101,
  less_mem_size: Boolean = true,
  sub_mem_tag: Int = 0
){
  require(kernel_size.length == 2)
  def getKernelConfig: HComplexConfig = coef_cfg * imp_cfg

  def getFFT2dConfig: FFTConfig = FFTConfig(getKernelConfig / 2, kernel_size(1), kernel_size(0))

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
  val wave = LoadData.loadDoubleMatrix("src/test/resource/data/wave.csv")
  val distance = LoadData.loadDoubleMatrix("src/test/resource/data/distance.csv")
  val rsd_cfg = RsdKernelConfig(
    wave_cfg = HComplexConfig(8, 8),
    distance_cfg = HComplexConfig(8, 8),
    timeshift_cfg = HComplexConfig(-4, 20),
    coef_cfg = HComplexConfig(-5, 21), // (-4 ,20) --> (-5, 21)
    imp_cfg = HComplexConfig(5, 11),
    depth_factor = wave.cols,
    radius_factor = wave.rows,
    freq_factor = distance.rows
  )
  val timeshift = LoadData.loadComplexMatrix(
    "src/test/resource/data/timeshift_real.csv",
    "src/test/resource/data/timeshift_imag.csv"
  )
  val impulse: DenseMatrix[Complex] = LoadData.loadComplexMatrix(
    "src/test/resource/data/impulse_rad_real.csv",
    "src/test/resource/data/impulse_rad_imag.csv"
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
