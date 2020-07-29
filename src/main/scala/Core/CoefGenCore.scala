package Core
import spinal.core._
import spinal.lib._
import Config.{HComplexConfig, RsdKernelConfig}
import Util._

case class CoefGenCore
(
  cfg: RsdKernelConfig
) extends Component with Pipeline {
  import Util.MySFix._

  val io = new Bundle {
    val wave = in SFix(cfg.wave_cfg.intw-1 exp, -cfg.wave_cfg.fracw exp)// these two data are real
    val distance = in SFix(cfg.distance_cfg.intw-1 exp, -cfg.distance_cfg.fracw exp)
    val timeshift = in ( HComplex(cfg.timeshift_cfg) )
    val coef = out (HComplex(cfg.coef_cfg))
  }

  openAllStage()

  val wave = stage(io.wave, 0) //A
  val distance = stage(io.distance, 0) //B
  val timeshift = stage(io.timeshift, 0 to 4) //C

  val wd_prod = stage(wave * distance, 1)

  val exp_wd_prod = ExpFunc(wd_prod).setWeakName("exp_wd_prod") // with 2 stage pipeline inside.
  exp_wd_prod.setWeakName("exp_wd_prod")

  val exp_wd_prod_divw = stage( exp_wd_prod / stage(wave, 1 to 3) , 4) // D

  io.coef := stage( exp_wd_prod_divw * timeshift , 5)
}
