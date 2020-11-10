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

  private val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg

  val io = new Bundle {
    val dc = in UInt(log2Up(cfg.depth_factor) bit)
    val fc = in UInt(log2Up(cfg.freq_factor) bit)
    val fft2d_out_sync = in Bool
    val push_ending = out Bool()
    val cnt_incr = out Bool
    val load_req = out Bits(4 bit)
    val fc_eq_0 = in Bool
    val dc_eq_0 = in Bool
    val rsd_kernel: Flow[Vec[HComplex]] = master (
      Flow(Vec(HComplex(kernel_cfg), row_num))
    )
    val t1 = out Bool
  }
  io.t1 := io.dc.andR | io.fc.orR
  val data_in = slave(Axi4WriteOnly(axi_config))

  // ********************* Load Unit ***************************
  val loadUnitAddrs = Vector.tabulate(4) {i =>
    init_addr + i * (1 << 16) // Every load unit has 65kiB address space
  }

  val timeshift_load_unit: TimeshiftLoadUnit = LoadUnit.TimeshiftLoadUnit(
    cfg, loadUnitAddrs.head
  )

  val distance_load_unit: DistanceLoadUnit = LoadUnit.DistanceLoadUnit(
    cfg, loadUnitAddrs(1)
  )

  val wave_load_unit: WaveLoadUnit = LoadUnit.WaveLoadUnit(
    cfg, loadUnitAddrs(2)
  )

  val impulse_load_unit: ImpLoadUnit = LoadUnit.ImpLoadUnit(
    cfg, loadUnitAddrs(3)
  )

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

  // Control when should push the wave and start computing rsd kernel
  val compute_stage = io.dc_eq_0 ## io.fc_eq_0
  val rsd_comp_start = RegInit(False)
  val wave_hit = (data_in.w.fire & (data_in.aw.payload.addr === loadUnitAddrs(2))).rise(False)
  switch(compute_stage){
    is(B"2'b00") { // d != 0 && f != 0
      rsd_comp_start := Delay(distance_load_unit.io.data_enable, 6, init = False) // The latency of coefGenCore is 6
    }
    is(B"2'b01") { // d != 0 && f == 0
      rsd_comp_start := Delay(wave_hit, 3, init = False) // RSD kernel compute as soon as wave has loaded 3 elements
    }
    is(B"2'b10") { // d == 0 && f != 0
//      rsd_comp_start := Delay(distance_load_unit.io.data_enable, 6, init = False) // The latency of coefGenCore is 6
      rsd_comp_start := Delay(distance_load_unit.io.data_enable, 6, init = False) // The latency of coefGenCore is 6
    }
    is(B"2'b11") { // d == 0 && d == 0
      rsd_comp_start := impulse_load_unit.io.data_enable // RSD kernel compute as soon as impulse loaded
    }
  }

  val push_ending = RegInit(False)
  val push_ending_1 = RegNext(push_ending) init False

  // instantiate the rsd kernel core array
  // A rsd_gen_core contains a list of prsd_gen_core to pipe out a column of rsd kernel
  val rsd_gen_core_array = Array.fill(Rlength)(RsdGenCore(cfg))
  rsd_gen_core_array.foreach(_.io.wave <> wave_load_unit.io.wave)
  rsd_gen_core_array.foreach(_.io.distance <> distance_load_unit.io.distance)
  rsd_gen_core_array.foreach(_.io.timeshift <> timeshift_load_unit.io.timeshift)
  rsd_gen_core_array.zipWithIndex.foreach{ case (core, i) =>
    core.io.ring_impulse <> impulse_load_unit.io.impulse_out.translateWith(
      impulse_load_unit.io.impulse_out.payload(i)
    )
  }

  //Wave load unit's control signal connection
//  wave_load_unit.io.impulse_enable := impulse_load_unit.io.data_enable
//  wave_load_unit.io.distance_enable := distance_load_unit.io.data_enable
//  wave_load_unit.io.push_ending := push_ending
  wave_load_unit.io.rsd_comp_start := rsd_comp_start

  // Timeshift load unit
  timeshift_load_unit.io.push_ending := push_ending

  // Distance load unit
  distance_load_unit.io.push_ending := push_ending

  // Impulse load unit
  impulse_load_unit.io.distance_enable := distance_load_unit.io.data_enable
//  impulse_load_unit.io.wave_enable := wave_load_unit.io.data_enable
  impulse_load_unit.io.rsd_comp_start := rsd_comp_start

  // connect the fceq0 and dceq0
  impulse_load_unit.io.fc_eq_0 := io.fc_eq_0
  impulse_load_unit.io.dc_eq_0 := io.dc_eq_0
  wave_load_unit.io.fc_eq_0 := io.fc_eq_0
//  wave_load_unit.io.dc_eq_0 := io.dc_eq_0

  // Store the rsd kernel
  val rsd_mem = Vec(Reg(HComplex(kernel_cfg)), Rlength) simPublic()
  rsd_mem.zipWithIndex.foreach {case(dat, idx) =>
    when(wave_load_unit.io.rsd_comp_end) {
//      dat := rsd_gen_core.io.kernel_array(idx)
      dat := rsd_gen_core_array(idx).io.kernel.payload
    }
  }

  // The address transformation happens here
//  val addr_map = Vec( buildAddrMap(cfg.impulse_sample_point) )
//  val addr_map = Vec(buildAddrMap(Rlength))

  // Push_start: A one-cycle square impulse active one cycle of actually push start
  // fft2d_out_sync is active at the first one cycle of the fft2d_valid
  val push_start = io.dc_eq_0 ? io.fft2d_out_sync | push_ending_1

  // count for row_num cycles from push_start signal active
  val count_col_addr = countUpFrom(push_start, 0 until col_num, "count_col_addr")
  val col_addr = count_col_addr.cnt
  col_addr.setName("col_addr")
  val rad_addr_map = RadAddrMap(cfg)
  rad_addr_map.io.col_addr := col_addr.value
  val pixel_addrs = rad_addr_map.io.pixel_addrs

  for(id <- 0 until row_num) {
    when(count_col_addr.cond_period) {
      io.rsd_kernel.payload(id) := rsd_mem(pixel_addrs(id))
    } otherwise {
      io.rsd_kernel.payload(id) := 0
    }
  }

  io.rsd_kernel.valid := count_col_addr.cond_period
  push_ending := count_col_addr.cnt.willOverflow

  io.push_ending := push_ending // Push ending is the true increment signal tb used.
  io.cnt_incr := push_ending_1 // TODO: if freq counter increment when it activate, the timing is not conform to tb!
  io.load_req := impulse_load_unit.io.load_req ## wave_load_unit.io.load_req ## distance_load_unit.io.load_req ## timeshift_load_unit.io.load_req

}
