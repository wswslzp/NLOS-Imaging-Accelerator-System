package Core

import spinal.core._
import spinal.lib._
import Config._
import Util._
import spinal.lib.bus.amba4.axi.{Axi4, Axi4Config, Axi4WriteOnly}

// TODO: Coef now compute in the chip
case class CoefLoadUnit
(
   cfg: RsdKernelConfig,
   freq_num: Int,
   init_addr: Int,
   override val axi_config: Axi4Config
) extends Component with AXI4WLoad
{
  override val word_bit_count: Int = cfg.coef_cfg.getComplexWidth
  awReady(True)
  wReady(True)

  val io = new Bundle {
    val wave = master (
      Flow(
        Vec(Vec(SFix(cfg.wave_cfg.maxExp, cfg.wave_cfg.minExp), cfg.depth_factor), cfg.radius_factor)
      )
    )
    val distance = master (
      Flow(
        Vec(Vec(SFix(cfg.distance_cfg.maxExp, cfg.distance_cfg.minExp), cfg.depth_factor), freq_num)
      )
    )
    val timeshift = master(
      Flow(
        Vec(Vec(HComplex(cfg.timeshift_cfg), cfg.depth_factor), freq_num)
      )
    )
  }

  val local_mem_manager = ApplyMem(init_addr, cfg.coef_cfg.getComplexWidth)

  // allocate the address of registers
  val (transfer_done_map, transfer_done) = local_mem_manager.allocateReg(Bool())
  val (wave_addr_map, wave_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(cfg.radius_factor * cfg.depth_factor)(SFix(cfg.wave_cfg.maxExp, cfg.wave_cfg.minExp))
  )
//  SpinalInfo(s"wave_regs.width = ${wave_addr_map.values.toVector.head.getBitsWidth}")
  val (distance_addr_map, distance_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(freq_num * cfg.depth_factor)(SFix(cfg.distance_cfg.maxExp, cfg.distance_cfg.minExp))
  )
//  SpinalInfo(s"distance_regs.width = ${distance_addr_map.values.toVector.head.getBitsWidth}")
  val (timeshift_addr_map, timeshift_regs) = local_mem_manager.allocateRegArray(
    Vector.fill(freq_num * cfg.depth_factor)(HComplex(cfg.timeshift_cfg))
  )
//  SpinalInfo(s"timeshift_regs.width = ${timeshift_addr_map.values.toVector.head.getBitsWidth}")

  // arrange the address
  arrangeRegMapAddr(
    transfer_done_map,
    wave_addr_map,
    distance_addr_map,
    timeshift_addr_map
  )

  printAddrRange
  loadData()

  // output the wave, distance and the timeshift to the CoefGenArray
//  for {
//    f <- 0 until freq_num// 69
//    r <- 0 until cfg.radius_factor // 70
//    d <- 0 until cfg.depth_factor // 51
//  } {
//    io.wave.payload(r)(d) := wave_regs(r * cfg.depth_factor + d).asInstanceOf[SFix]
//    io.distance.payload(f)(d) := distance_regs(f * cfg.depth_factor + d).asInstanceOf[SFix]
//    io.timeshift.payload(f)(d) := timeshift_regs(f * cfg.depth_factor + d).asInstanceOf[HComplex]
//  }
  for ( d <- 0 until cfg.depth_factor ) {
    for (r <- 0 until cfg.radius_factor) {
      io.wave.payload(r)(d) := wave_regs(r * cfg.depth_factor + d)
    }
    for (f <- 0 until freq_num) {
      io.distance.payload(f)(d) := distance_regs(f * cfg.depth_factor + d)
      io.timeshift.payload(f)(d) := timeshift_regs(f * cfg.depth_factor + d)
    }
  }

  // when all data is transferred, master set the transfer_done signal high longer than N cycles.
  io.wave.valid := transfer_done
  io.distance.valid := transfer_done
  io.timeshift.valid := transfer_done
}

