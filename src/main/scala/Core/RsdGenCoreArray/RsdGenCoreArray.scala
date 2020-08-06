package Core.RsdGenCoreArray

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import spinal.lib.bus.amba4.axi._
import scala.collection.mutable.ListBuffer

case class RsdGenCoreArray(
                          cfg: RsdKernelConfig,
                          init_addr: Int,
                          override val axi_config: Axi4Config
                          ) extends Component with AXI4WLoad {
  override val word_bit_count: Int = cfg.timeshift_cfg.getComplexWidth

  private val kernel_cfg = cfg.coef_cfg * cfg.imp_cfg

  val io = new Bundle {
    val ready_for_store = in Bool()
    val start = in Bool()
    val done = out Bool()
    val rsd_kernel: Flow[Vec[HComplex]] = master (
      Flow(Vec(HComplex(kernel_cfg), cfg.depth_factor))
    )
  }

  // Fork the AW channel of AXI bus into all the load units
  val axi_aw_streams = StreamFork(
    data_in.aw, 3 + cfg.depth_factor
  )

  // Fork the W channel of AXI bus into all the load units
  val axi_w_streams = StreamFork(
    data_in.w, 3 + cfg.depth_factor
  )

  // Below, instantiate three load units used to store the
  // impulse, time shift and distance.
  val impulse_load_unit = LoadUnit.ImpLoadUnit(
    cfg = cfg, init_addr = init_addr, axi_config = axi_config
  )
  axi_aw_streams(0) <> impulse_load_unit.data_in.aw
  axi_w_streams(0) <> impulse_load_unit.data_in.w
  private val impulse_final_addr = impulse_load_unit.local_mem_manager.finalAddr

  val timeshift_load_unit = LoadUnit.TimeshiftLoadUnit(
    cfg, impulse_final_addr, axi_config
  )
  axi_aw_streams(1) <> timeshift_load_unit.data_in.aw
  axi_w_streams(1) <> timeshift_load_unit.data_in.w
  private val timeshift_final_addr = timeshift_load_unit.local_mem_manager.finalAddr

  val distance_load_unit = LoadUnit.DistanceLoadUnit(
    cfg, timeshift_final_addr, axi_config
  )
  axi_aw_streams(2) <> distance_load_unit.data_in.aw
  axi_w_streams(2) <> distance_load_unit.data_in.w
  private val distance_final_addr = distance_load_unit.local_mem_manager.finalAddr

  // collect all the B channels of AXI bus to be arbitrated
  var axi_b_streams = impulse_load_unit.data_in.b :: timeshift_load_unit.data_in.b :: distance_load_unit.data_in.b :: Nil

  // accumulate the wave load units' initial address
  var wave_acc_addr = distance_final_addr

  // instantiate the rsd kernel core array
  for(depth <- 0 until cfg.depth_factor) {
    val wave_load_unit = LoadUnit.WaveLoadUnit(
      cfg, wave_acc_addr, axi_config
    )
    wave_load_unit.data_in.aw <> axi_aw_streams(3+depth)
    wave_load_unit.data_in.w <> axi_w_streams(3+depth)
    axi_b_streams = axi_b_streams :+ wave_load_unit.data_in.b
    wave_load_unit.setWeakName(wave_load_unit.getName() + s"_depth_$depth")
    wave_acc_addr += wave_load_unit.local_mem_manager.finalAddr

    val rsd_gen_core = RsdKernelGen(cfg)
    rsd_gen_core.setWeakName(rsd_gen_core.getName() + s"_depth_$depth")
    rsd_gen_core.io.kernel <> io.rsd_kernel.payload(depth)
    rsd_gen_core.io.wave <> wave_load_unit.io.wave.payload
    rsd_gen_core.io.distance <> distance_load_unit.io.distance.payload(depth)
    rsd_gen_core.io.timeshift <> timeshift_load_unit.io.timeshift.payload(depth)
    rsd_gen_core.io.ring_impulse <> impulse_load_unit.io.impulse_out.payload
  }

  data_in.b <> StreamArbiterFactory.roundRobin.on(axi_b_streams)

  //TODO: the control part of the module here needs to be thought.
  //  the control signal here is: ready_for_store, start, done.
  //  * for ready_for_store, it indicates that the load units is all ready for the
  //    the data loading.
  //  * for start, it indicates that the cores could start computation when the
  //      controller sees all load units have data prepared or all the data have
  //      correctly stand by on the ports.
  //  * for done, it indicates that the cores have done computation.


}
