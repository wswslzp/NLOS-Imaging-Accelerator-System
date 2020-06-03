package Core
import spinal.core._
import spinal.lib._
import Config.{HComplexConfig, RsdKernelConfig}
import Util._

// TODO: Overall code is a mess, may cut the exp_func code part off.
case class CoefGenCore
(
  wave_cfg: HComplexConfig,
  distance_cfg: HComplexConfig,
  timeshift_cfg: HComplexConfig,
  coef_cfg: HComplexConfig
) extends Component with Pipeline {
  import Util.MySFix._

  val io = new Bundle {
    val wave = in SFix(wave_cfg.intw-1 exp, -wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(distance_cfg.intw-1 exp, -distance_cfg.fracw exp)
    val timeshift = in ( HComplex(timeshift_cfg) )
    val coef = out (HComplex(coef_cfg))
  }

  openAllStage()

  val wave = stage(io.wave, 0)
  val distance = stage(io.distance, 0)
  val timeshift = stage(io.timeshift, 0 to 4)

  val wd_prod = stage(wave * distance, 1)

  val exp_wd_prod = ExpFunc(wd_prod).setWeakName("exp_wd_prod") // with 2 stage pipeline inside.

  val exp_wd_prod_divw = stage( exp_wd_prod / stage(wave, 1 to 3) , 4)

  io.coef := stage( exp_wd_prod_divw * timeshift , 5)
}
