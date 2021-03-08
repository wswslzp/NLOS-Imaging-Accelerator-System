package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Core.LoadUnit._
import spinal.lib.bus.amba4.axi._

import scala.collection.mutable._
import spinal.core.sim._

case class RsdGenCoreArray(
                          cfg: RsdKernelConfig,
                          init_addr: Int
                          )(implicit val axi_config: Axi4Config) extends Component {
  val row_num = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)
  val Rlength = cfg.impulse_sample_point

  val io = new Bundle {
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val fft2d_out_sync = in Bool
    val clear_confirm = in Bool()
    val push_ending = out Bool()
    val push_start = out Bool()
    val cnt_incr = out Bool
    val load_req = out Bits(4 bit)
    val rsd_kernel: Flow[Vec[HComplex]] = master (
      Flow(Vec(HComplex(cfg.getKernelConfig), row_num))
    )
  }
  val fc_eq_0 = io.fc === 0
  val dc_eq_0 = io.dc === 0
  val data_in = slave(Axi4WriteOnly(axi_config))

  // ********************* Load Unit ***************************
  val loadUnitAddrs = Vector.tabulate(4) {i =>
    init_addr + i * (1 << 16) // Every load unit has 65kiB address space
  }

  val timeshift_load_unit: TimeshiftLoadUnit = LoadUnit.TimeshiftLoadUnit(
    cfg, loadUnitAddrs.head
  )
  timeshift_load_unit.io.timeshift.simPublic()

  val distance_load_unit: DistanceLoadUnit = LoadUnit.DistanceLoadUnit(
    cfg, loadUnitAddrs(1)
  )
  distance_load_unit.io.distance.simPublic()

  val wave_load_unit: WaveLoadUnit = LoadUnit.WaveLoadUnit(
    cfg, loadUnitAddrs(2)
  )
  wave_load_unit.io.wave.simPublic()

  val impulse_load_unit: ImpLoadUnit = LoadUnit.ImpLoadUnit(
    cfg, loadUnitAddrs(3)
  )
  impulse_load_unit.io.impulse_out.simPublic()

  // ******************** Axi Bus crossbar ***********************
  // Fork the AW channel of AXI bus into all the load units
  val axi_aw_streams = StreamFork(
    data_in.aw, 4
  )

  // Fork the W channel of AXI bus into all the load units
  val axi_w_streams = StreamFork(
    data_in.w, 4
  )

  axi_aw_streams(1) <> timeshift_load_unit.data_in.aw
  axi_w_streams(1) <> timeshift_load_unit.data_in.w

  axi_aw_streams(2) <> distance_load_unit.data_in.aw
  axi_w_streams(2) <> distance_load_unit.data_in.w

  // accumulate the wave load units' initial address
  wave_load_unit.data_in.aw <> axi_aw_streams(3)
  wave_load_unit.data_in.w <> axi_w_streams(3)

  // Below, instantiate three load units used to store the
  // impulse, time shift and distance.
  axi_aw_streams(0) <> impulse_load_unit.data_in.aw
  axi_w_streams(0) <> impulse_load_unit.data_in.w

  // collect all the B channels of AXI bus to be arbitrated
  var axi_b_streams = impulse_load_unit.data_in.b :: timeshift_load_unit.data_in.b :: distance_load_unit.data_in.b :: wave_load_unit.data_in.b :: Nil

  // The chosen arbiter strategy is round robin. I dont know how good it is.
  data_in.b <> StreamArbiterFactory.roundRobin.on(axi_b_streams)


  // ******************** main part of rsd core gen array *******************************

  // instantiate the rsd kernel core array
  // A rsd_kernel_gen pipe out a radius of rsd kernel
  val rsd_kernel_gen = RsdKernelGen(cfg)
  rsd_kernel_gen.io.wave <> wave_load_unit.io.wave
  rsd_kernel_gen.io.distance <> distance_load_unit.io.distance
  rsd_kernel_gen.io.timeshift <> timeshift_load_unit.io.timeshift
  rsd_kernel_gen.io.ring_impulse <> impulse_load_unit.io.impulse_out
  rsd_kernel_gen.io.rsd_comp_end := impulse_load_unit.io.rsd_comp_end

  val W2CLatency = rsd_kernel_gen.pRsdKernelGen.W2CLatency
  val D2CLatency = rsd_kernel_gen.pRsdKernelGen.coef_gen_core.D2Clatency

  // Control when should push the wave and start computing rsd kernel
  val compute_stage = dc_eq_0 ## fc_eq_0
  val rsd_comp_start = RegInit(False) simPublic()
//  val wave_hit = (data_in.w.fire & (data_in.aw.payload.addr === loadUnitAddrs(2))).rise(False)
  val wave_hit = (data_in.aw.payload.addr === loadUnitAddrs(2)).fall(False) //& data_in.w.valid // todo check
  switch(compute_stage){
    is(B"2'b00") { // d != 0 && f != 0
      rsd_comp_start := Delay(distance_load_unit.io.data_enable, D2CLatency, init = False) // The latency of coefGenCore is 6
    }
    is(B"2'b01") { // d != 0 && f == 0
      val wave_push_latency = cfg.radius_factor / 16 + 1
      rsd_comp_start := Delay(wave_hit, wave_push_latency, init = False) // RSD kernel compute as soon as wave has loaded 3 elements
    }
    is(B"2'b10") { // d == 0 && f != 0
      rsd_comp_start := Delay(distance_load_unit.io.data_enable, D2CLatency, init = False) // The latency of coefGenCore is 6
    }
    is(B"2'b11") { // d == 0 && d == 0
      rsd_comp_start := impulse_load_unit.io.data_enable // RSD kernel compute as soon as impulse loaded
    }
  }

  val push_ending = RegInit(False)
  val push_ending_1 = RegNext(push_ending) init False

  //Wave load unit's control signal connection
  wave_load_unit.io.rsd_comp_start := rsd_comp_start
  wave_load_unit.io.rsd_comp_end := impulse_load_unit.io.rsd_comp_end

  // Timeshift load unit
  timeshift_load_unit.io.cnt_incr := io.cnt_incr

  // Distance load unit
  distance_load_unit.io.cnt_incr := io.cnt_incr

  // Impulse load unit
  impulse_load_unit.io.distance_enable := distance_load_unit.io.data_enable
  impulse_load_unit.io.rsd_comp_start := Delay(rsd_comp_start, W2CLatency, init = False)

  // connect the fceq0 and dceq0
  impulse_load_unit.io.fc_eq_0 := fc_eq_0
  impulse_load_unit.io.dc_eq_0 := dc_eq_0
  wave_load_unit.io.fc_overflow := io.fc === cfg.freq_factor-1

  //********************************* RSD Kernel memory*************************
  // Store the rsd kernel memory
  val rsd_mem = Vec(Reg(HComplex(cfg.getKernelConfig)), Rlength).simPublic()
  val prev_rsd_mem = Vec(Reg(HComplex(cfg.getKernelConfig)), Rlength).simPublic()

  when(rsd_kernel_gen.io.kernel.valid){
    for(idx <- rsd_mem.indices){
      prev_rsd_mem(idx) := rsd_kernel_gen.io.kernel.payload(idx)
    }
  }

  // for d0, f < f_max-1, rsd kernel rad store into mem right after kernel valid
  // while for other df cycles, valid rsd kernel rad should wait for push ending
  // because the mem storing previous rsd kernel must not be overwritten.
  for(idx <- rsd_mem.indices){
    when(dc_eq_0 && rsd_kernel_gen.io.kernel.valid){
      rsd_mem(idx) := rsd_kernel_gen.io.kernel.payload(idx)
    } elsewhen (!dc_eq_0 && push_ending){
      rsd_mem(idx) := prev_rsd_mem(idx)
    }
  }

  // Push_start: A one-cycle square impulse active one cycle of actually push start
  // fft2d_out_sync is active at the first one cycle of the fft2d_valid
  val cnt_incr_1 = RegNext(io.cnt_incr) init False
  val push_start = dc_eq_0 ? io.fft2d_out_sync | cnt_incr_1

  // count for row_num cycles from push_start signal active
  val count_col_addr = countUpFrom(push_start, 0 until col_num, "count_col_addr")
  val col_addr = count_col_addr.cnt
  col_addr.setName("col_addr")
  val rad_addr_map = RadAddrMap(cfg)
  rad_addr_map.io.col_addr := col_addr.value
  val pixel_addrs = rad_addr_map.io.pixel_addrs.map(
    RegNext(_, init = U(0)) simPublic()
  )

  //todo: From pixel address to mac array's rsd kernel,
  //  fanout is too high, resulting in severe congestion and bad timing
  for(id <- 0 until row_num){
    when(io.rsd_kernel.valid){
//      io.rsd_kernel.payload(id) := rsd_mem(pixel_addrs(id))
      io.rsd_kernel.payload(id) := RegNext(rsd_mem(pixel_addrs(id)))
    } otherwise {
      io.rsd_kernel.payload(id) := 0
    }
  }

//  io.rsd_kernel.valid := RegNext(count_col_addr.cond_period, init = False)
  io.rsd_kernel.valid := Delay(count_col_addr.cond_period, 2, init = False)
//  push_ending := RegNext(count_col_addr.cnt.willOverflow, init = False)
  push_ending := Delay(count_col_addr.cnt.willOverflow, 2, init = False)

  io.push_ending := push_ending // Push ending is the true increment signal tb used.
  io.push_start := push_start

  // indicate when the controller to do counter increment.
  io.cnt_incr := ( io.fc === cfg.freq_factor-1 ) ? io.clear_confirm | push_ending

  // define new load req
  val wave_req = wave_load_unit.io.load_req
  io.load_req := impulse_load_unit.io.load_req ## wave_load_unit.io.load_req ## distance_load_unit.io.load_req ## timeshift_load_unit.io.load_req

//  val sim = new Bundle {
//    val coef = out(HComplex(rsd_kernel_gen.sim.coef.config))
//    val rsd_prev = out(cloneOf(rsd_kernel_gen.sim.rsd_prev))
//  }
//  sim.coef := rsd_kernel_gen.sim.coef
//  sim.rsd_prev := rsd_kernel_gen.sim.rsd_prev
}
