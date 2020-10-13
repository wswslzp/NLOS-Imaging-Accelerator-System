package Core.LoadUnit

import spinal.core._
import spinal.lib._
import bus.amba4.axi._
import Config._
import Util._
import spinal.lib.bus.amba3.apb.Apb3SlaveFactory


case class TimeshiftLoadUnit(
                              cfg: RsdKernelConfig,
                              init_addr: Int
                            )(override implicit val axi_config: Axi4Config) extends Component with Axi4Slave {
  override val word_bit_count: Int = cfg.timeshift_cfg.getComplexWidth

  val io = new Bundle {
    val load_req = out Bool
    val ready_for_store = out Bool
    val data_enable = out Bool
    val push_ending = in Bool
    val timeshift = master (Flow(
//      Vec(HComplex(cfg.timeshift_cfg), cfg.depth_factor)
      HComplex(cfg.timeshift_cfg)
    ))
  }
  wReady(io.ready_for_store)
  awReady(io.ready_for_store)

  val local_mem_manager = ApplyMem(init_addr, word_bit_count)
  val (timeshift_reg_addr_map, timeshift_reg) = local_mem_manager.allocateReg(
//    HComplex(cfg.timeshift_cfg)
    Bits(cfg.timeshift_cfg.getComplexWidth bit)
  )
  val (transfer_done_map, transfer_done_reg) = local_mem_manager.allocateReg(
    Bool(), "transfer_done"
  )

  arrangeRegMapAddr( timeshift_reg_addr_map, transfer_done_map )
//  arrangeRegMapAddr( timeshift_reg_addr_map )
  loadData()

  // Create a Apb3 bridge
//  val axi2apb = Axi4SharedToApb3Bridge(
//    axi_config.addressWidth, axi_config.dataWidth, axi_config.idWidth
//  )
//  axi2apb.io.axi <> data_in.toShared()
//  val apb_bus = axi2apb.io.apb
//  val busCtrl = Apb3SlaveFactory(apb_bus)
//  val transfer_done_reg = busCtrl.createWriteOnly(Bool(), local_mem_manager.incrAddr)
  val transfer_req_reg = RegInit(True)

  transfer_req_reg.setWhen(io.push_ending)
  transfer_req_reg.clearWhen(transfer_done_reg)
  io.load_req := transfer_req_reg

  transfer_done_reg init False
  transfer_done_reg clearWhen io.push_ending

  io.timeshift.valid := transfer_done_reg
  io.data_enable := transfer_done_reg
  io.timeshift.payload := timeshift_reg
  io.ready_for_store := !transfer_done_reg


}
