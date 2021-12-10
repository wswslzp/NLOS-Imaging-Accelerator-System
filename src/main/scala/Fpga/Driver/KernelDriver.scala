package Fpga.Driver

import Config.RsdKernelConfig._
import Config._
import breeze.math.Complex
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi.Axi4WriteOnly
import fsm._

// TODO: Need verification
case class KernelDriver(cfg: RsdKernelConfig, loadUnitAddrs: Vector[Int]) extends Component with DataTransform {
  val io = new Bundle {
    val kernel_data_out = master(Axi4WriteOnly(axi_config))
    val load_req = in Bits (4 bit)
  }

  def innerRom(memLen: Int, width: Int)(tab_func: Int=>BigInt) = new InnerMem(memLen, width) {
    rom.init(Seq.tabulate(memLen){i=>
      B(tab_func(i), width bit)
    })
  }

  // ************** Memory for Kernel data ******
  val ts_mat = timeshift.t.map(_.conjugate).toDenseMatrix // TODO: Hermitt conj and transpose, like matlab !!!
  val ts = innerRom(cfg.depth_factor*cfg.freq_factor, cfg.timeshift_cfg.getComplexWidth){idx=>
    val depth = idx / cfg.freq_factor
    val freq = idx % cfg.freq_factor
    complexToSInt(ts_mat(depth, freq), cfg.timeshift_cfg)
  }

  val ds_mat = distance.t.toDenseMatrix
  val ds = innerRom(cfg.depth_factor * cfg.freq_factor, cfg.distance_cfg.getDataWidth){ idx =>
    val depth = idx / cfg.freq_factor
    val freq = idx % cfg.freq_factor
    doubleToSInt(ds_mat(depth, freq), cfg.distance_cfg)
  }

  val wv = innerRom(cfg.radius_factor * cfg.depth_factor, cfg.wave_cfg.getDataWidth){ idx =>
    val depth = idx / cfg.radius_factor
    val radius = idx % cfg.radius_factor
    doubleToSInt(wave(radius, depth), cfg.wave_cfg)
  }

  val imp = innerRom(cfg.radius_factor * cfg.impulse_sample_point, cfg.imp_cfg.getDataWidth){ idx=>
    val isp = idx / cfg.radius_factor
    val radius = idx % cfg.radius_factor
    doubleToSInt(impulse(isp, radius), cfg.imp_cfg)
  }

  // ************** Driver logic *****************
  io.kernel_data_out.aw.setBurstINCR()
  io.kernel_data_out.aw.len := 0 // burst shot default is 1
  io.kernel_data_out.aw.size := U"3'h2" // 4 byte
  io.kernel_data_out.aw.valid := False
  io.kernel_data_out.aw.addr := 0
  io.kernel_data_out.aw.id := 0
  io.kernel_data_out.w.valid := False
  io.kernel_data_out.w.last := False
  io.kernel_data_out.w.data := 0
  io.kernel_data_out.b.ready := True

  // The address send to axi interface
  val ts_lu_addr = loadUnitAddrs(0)
  val ds_lu_addr = loadUnitAddrs(1)
  val wv_lu_addr = loadUnitAddrs(2)
  val wv_done_addr = loadUnitAddrs(2) + cfg.radius_factor
  val imp_lu_addr = loadUnitAddrs(3)
  val imp_done_addr = loadUnitAddrs(3) + cfg.impulse_sample_point*cfg.radius_factor

  // The burst length
  val wv_burst_len = cfg.radius_factor/16 + 1 // 69/16 + 1
  val imp_burst_len = cfg.radius_factor * cfg.impulse_sample_point / 16 + 1

  val fsm_scan_record = RegInit(False)
  val drv_fsm = new StateMachine {
    val ts_drv_state = new StateFsm(_fsm = ts.oneShotDriverFSM(U(ts_lu_addr), io.kernel_data_out))
    val ds_drv_state = new StateFsm(_fsm = ds.oneShotDriverFSM(U(ds_lu_addr), io.kernel_data_out))
    val wv_drv_state = new StateFsm(_fsm = wv.burstDriverFSM(wv_lu_addr, wv_done_addr, io.kernel_data_out, wv_burst_len, 16, rsd_cfg.radius_factor))
    val imp_drv_state = new StateFsm(_fsm = imp.burstDriverFSM(imp_lu_addr, imp_done_addr, io.kernel_data_out, imp_burst_len, 16, rsd_cfg.radius_factor * rsd_cfg.impulse_sample_point))

    val setup = new State with EntryPoint {
      whenIsActive {
        when(!fsm_scan_record) {
          fsm_scan_record.set()
          when(io.load_req(0)){
            goto(ts_drv_state)
          }
        }
        when(io.load_req(0)){
          fsm_scan_record.clear()
        }
      }
    }

    ts_drv_state
      .whenCompleted {
        when(io.load_req(1)){
          goto(ds_drv_state)
        } otherwise {
          goto(wv_drv_state)
        }
      }

    ds_drv_state
      .whenCompleted {
        when(io.load_req(2)){
          goto(wv_drv_state)
        } otherwise {
          goto(setup)
        }
      }

    wv_drv_state
      .whenCompleted {
        when(io.load_req(3)){
          goto(imp_drv_state)
        } otherwise {
          goto(setup)
        }
      }

    imp_drv_state
      .whenCompleted {
        goto(setup)
      }
  }

}
