package Fpga

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi._
import RsdKernelConfig._
import breeze.math.Complex


case class KernelDriver(cfg: RsdKernelConfig) extends Component {
  private def complexToBits(dat: Complex, cfg: HComplexConfig): Bits = {
    val ret = Bits(cfg.getComplexWidth bit)
    val real_p = ( dat.real * scala.math.pow(2, cfg.fracw) ).toInt
    val imag_p = ( dat.imag * scala.math.pow(2, cfg.fracw) ).toInt
    ret := if(cfg.real_high){
      B(real_p) ## B(imag_p)
    }
    ret
  }

  val io = new Bundle {
    val kernel_data_out = master(Axi4WriteOnly(axi_config))
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val load_req = in Bits(4 bit)
  }

  // ************** Memory for Kernel data ******
  val timeshift_rom = Mem(Array.tabulate(cfg.depth_factor * cfg.freq_factor){idx=>
    val freq = idx % cfg.depth_factor
    val depth = idx / cfg.depth_factor
    complexToBits(timeshift(freq, depth), cfg.timeshift_cfg)
  })

  val distance_rom = Mem(Array.tabulate(cfg.depth_factor * cfg.freq_factor){idx=>
    val freq = idx % cfg.depth_factor
    val depth = idx / cfg.depth_factor
    B(
      (distance(freq, depth) * scala.math.pow(2, cfg.distance_cfg.fracw)).toInt
    )
  })

  val wave_rom = Mem(Array.tabulate(cfg.radius_factor * cfg.depth_factor){idx=>
    val radius = idx % cfg.depth_factor
    val depth = idx / cfg.depth_factor
    B(
      (wave(radius, depth) * scala.math.pow(2, cfg.wave_cfg.fracw)).toInt
    )
  })

  // ************** Driver logic *****************

}
