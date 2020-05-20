package Core

import spinal.core._
import spinal.lib._
import Config.{RsdKernelConfig, HComplexConfig}
import Util.HComplex

case class RsdKernelGen(config: RsdKernelConfig) extends Component {
  val io = new Bundle {
    val ring_impulse = slave (
      Flow(Vec(Vec(HComplex(config.hComplexConfig), config.deltaw_factor), config.radius_factor))
    )
    val coef         = slave (
      Flow(Vec(Vec(HComplex(config.hComplexConfig), config.depth_factor), config.radius_factor))
    )
    val kernel       = master(
      Flow(Vec(Vec( HComplex(config.hComplexConfig), config.deltaw_factor ), config.depth_factor))
    )
  }

  //print
  println(config.radius_factor)
  println(config.depth_factor)

  // store the input data
  val ring_impulse_r: Vec[Vec[HComplex]] = io.ring_impulse.toReg()
  val coef_r: Vec[Vec[HComplex]] = io.coef.toReg()

  // store the middle results
  val mid_result_r = Vector.fill(config.radius_factor, config.depth_factor, config.deltaw_factor)(
    Reg(HComplex(config.hComplexConfig))
  )

  // build the array of the rsd kernel generator
  val dw = config.hComplexConfig.getComplexWidth
  for {
    radius <- 0 until config.radius_factor
    depth  <- 0 until config.depth_factor
    impulse = ring_impulse_r(radius)
    coef = coef_r(radius)(depth)
    last_input = if (radius==0) {
      List.fill(config.deltaw_factor)(HComplex(config.hComplexConfig, B(0, dw bit)))
    } else {
      mid_result_r(radius-1)(depth)
    }
  } {
    for(idx <- 0 until config.deltaw_factor) {
      mid_result_r(radius)(depth)(idx) := last_input(idx) + (impulse(idx) * coef)
      if(radius == config.radius_factor-1) {
        io.kernel.payload(depth)(idx) := mid_result_r(radius)(depth)(idx)
      }
    }
  }

  // drive the output valid signal
  io.kernel.valid := Delay(io.ring_impulse.valid, config.radius_factor)

}
