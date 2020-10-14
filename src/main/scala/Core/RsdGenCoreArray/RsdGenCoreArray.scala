package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Core.LoadUnit._
import spinal.lib.bus.amba4.axi._

import scala.collection.mutable._

case class RsdGenCoreArray(
                          cfg: RsdKernelConfig,
                          init_addr: Int
//                          axi_config: Axi4Config
                          )(implicit val axi_config: Axi4Config) extends Component /*with Axi4Slave*/ {
//  override val word_bit_count: Int = cfg.timeshift_cfg.getComplexWidth
  val row_num = cfg.kernel_size(0)
  val col_num: Int = cfg.kernel_size(1)
  val Rlength = cfg.impulse_sample_point
//  val Rlength = 1 << log2Up(
//    Math.sqrt(
//      Math.pow(row_num/2, 2) + Math.pow(col_num/2, 2)
//    ).toInt
//  )

  private val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg

  private def buildAddrMap(sample_point: Int, mapping_factor: Double = 1.1): Array[UInt] = {
    // Return the address LUT
    // The index of the LUT represents the linear address of the original image's pixels
    // The value of the LUT represents the linear address of the radius of image.

    val result_mat = ArrayBuffer.fill(row_num, col_num)(0)
    for {
      x <- 0 until row_num
      y <- 0 until col_num
    } {
      val xp = (x + row_num/2) % row_num
      val yp = (y + col_num/2) % col_num
      result_mat(xp)(yp) = Math.max(
        Math.min(
          Math.sqrt(Math.pow(x - row_num/2, 2) + Math.pow(y - col_num/2, 2)) * mapping_factor,
          sample_point - 1
        ),
        0
      ).toInt
    }
    Array.tabulate(row_num * col_num){idx=>
      val row = idx / row_num
      val col = idx % row_num
      U(result_mat(row)(col))
    }
  }

  val io = new Bundle {
    val fft2d_out_sync = in Bool
//    val ready_for_store = in Bool()
//    val start = in Bool()
    val push_ending = out Bool()
    val load_req = out Bits(4 bit)
    val fc_eq_0 = in Bool
    val dc_eq_0 = in Bool
    val rsd_kernel: Flow[Vec[HComplex]] = master (
      Flow(Vec(HComplex(kernel_cfg), row_num))
    )
  }
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
//  var wave_acc_addr = distance_final_addr
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
  val push_ending = RegInit(False)

  // instantiate the rsd kernel core array
  // A rsd_gen_core contains a list of prsd_gen_core to pipe out a column of rsd kernel
  val rsd_gen_core = RsdKernelGen(cfg)
  rsd_gen_core.io.wave <> wave_load_unit.io.wave.payload
  rsd_gen_core.io.distance <> distance_load_unit.io.distance.payload
  rsd_gen_core.io.timeshift <> timeshift_load_unit.io.timeshift.payload
  rsd_gen_core.io.ring_impulse <> impulse_load_unit.io.impulse_out.payload

  //Wave load unit's control signal connection
  wave_load_unit.io.impulse_enable := impulse_load_unit.io.data_enable
  wave_load_unit.io.distance_enable := distance_load_unit.io.data_enable
  wave_load_unit.io.push_ending := push_ending

  // Timeshift load unit
  timeshift_load_unit.io.push_ending := push_ending

  // Distance load unit
  distance_load_unit.io.push_ending := push_ending

  // Impulse load unit
  impulse_load_unit.io.distance_enable := distance_load_unit.io.data_enable
  impulse_load_unit.io.wave_enable := wave_load_unit.io.data_enable

  // connect the fceq0 and dceq0
  impulse_load_unit.io.fc_eq_0 := io.fc_eq_0
  impulse_load_unit.io.dc_eq_0 := io.dc_eq_0
  wave_load_unit.io.fc_eq_0 := io.fc_eq_0
  wave_load_unit.io.dc_eq_0 := io.dc_eq_0

  // Store the rsd kernel
  val rsd_mem = Vec(Reg(HComplex(kernel_cfg)), Rlength)
  rsd_mem.zipWithIndex.foreach {case(dat, idx) =>
    when(wave_load_unit.io.rsd_comp_end) {
      dat := rsd_gen_core.io.kernel_array(idx)
    }
  }

  // The address transformation happens here
//  val addr_map = Vec( buildAddrMap(cfg.impulse_sample_point) )
  val addr_map = Vec(buildAddrMap(Rlength))

  // Push_start: A one-cycle square impulse active one cycle before actually push start
  // fft2d_out_sync is active at the last one cycle of the fft2d_valid
  val push_start = RegNext(
    io.dc_eq_0 ? io.fft2d_out_sync | push_ending,
    init = False
  )

  // count for row_num cycles from push_start signal active
  val count_col_addr = countUpFrom(push_start, 0 until col_num, "count_col_addr")
  val col_addr = count_col_addr.cnt
  val pixel_addrs: Array[UInt] = Array.tabulate(row_num){ id=>
    addr_map( ( col_addr.value + U(id * row_num) ).resized )
  }

//  when(count_col_addr.cond_period) {
//    for(id <- 0 until row_num) {
//      io.rsd_kernel.payload(id) := rsd_mem(pixel_addrs(id))
//    }
//  }

  for(id <- 0 until row_num) {
    when(count_col_addr.cond_period) {
      io.rsd_kernel.payload(id) := rsd_mem(pixel_addrs(id))
    } otherwise {
      io.rsd_kernel.payload(id) := 0
    }
  }

  io.rsd_kernel.valid := count_col_addr.cond_period
  push_ending := count_col_addr.cnt.willOverflow

  io.push_ending := push_ending
  //TODO: load req has problems.
  io.load_req := impulse_load_unit.io.load_req ## wave_load_unit.io.load_req ## distance_load_unit.io.load_req ## timeshift_load_unit.io.load_req

}
