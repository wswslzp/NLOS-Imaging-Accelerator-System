package Fpga.Driver

import Config.RsdKernelConfig._
import Config._
import breeze.math.Complex
import spinal.core._
import spinal.lib._
import spinal.lib.bus.amba4.axi.Axi4WriteOnly
import fsm._
import InnerMem._

// TODO: Need verification
case class KernelDriver(cfg: RsdKernelConfig, loadUnitAddrs: Vector[Int]) extends Component with DataTransform {
  val io = new Bundle {
    val kernel_data_out = master(Axi4WriteOnly(axi_config))
    val load_req = in Bits (4 bit)
  }

  def innerRom(memLen: Int, width: Int)(tab_func: Int=>BigInt) = new Area with InnerMem {
    override val memDepth = memLen
    override val rom = Mem(Seq.tabulate(memLen){
      i=> B(tab_func(i), width bit)
    })
  }

  // ************** Memory for Kernel data ******
  val ts = innerRom(cfg.depth_factor*cfg.freq_factor, cfg.timeshift_cfg.getComplexWidth){idx=>
    val freq = idx / cfg.depth_factor
    val depth = idx % cfg.depth_factor
    complexToSInt(timeshift(freq, depth), cfg.timeshift_cfg)
  }

  val ds = innerRom(cfg.depth_factor * cfg.freq_factor, cfg.distance_cfg.getDataWidth){ idx =>
    val freq = idx / cfg.depth_factor
    val depth = idx % cfg.depth_factor
    doubleToSInt(distance(freq, depth), cfg.distance_cfg)
  }

  val wv = innerRom(cfg.radius_factor * cfg.depth_factor, cfg.wave_cfg.getDataWidth){ idx =>
    val radius = idx / cfg.depth_factor
    val depth = idx % cfg.depth_factor
    doubleToSInt(wave(radius, depth), cfg.wave_cfg)
  }

  val imp = innerRom(cfg.radius_factor * cfg.impulse_sample_point, cfg.imp_cfg.getDataWidth){ idx=>
    val isp = idx / cfg.impulse_sample_point
    val radius = idx % cfg.impulse_sample_point
    doubleToSInt(impulse(isp, radius), cfg.imp_cfg)
  }

  // ************** Driver logic *****************
  // TODO: finish the kernel axi4 driver logic
  io.kernel_data_out.aw.setBurstINCR()
  io.kernel_data_out.aw.len := 0 // burst shot default is 1
  io.kernel_data_out.aw.size := U"3'h2" // 4 byte
  io.kernel_data_out.aw.valid := False
  io.kernel_data_out.aw.addr := 0
  io.kernel_data_out.w.valid := False
  io.kernel_data_out.w.last := False
  io.kernel_data_out.w.data := 0

  // The address send to axi interface
  val ts_lu_addr = loadUnitAddrs(0)
  val ds_lu_addr = loadUnitAddrs(1)
  val wv_lu_addr = Vec.tabulate(cfg.radius_factor / 16){i=> U(i * 16 + loadUnitAddrs(2))}
  val imp_lu_addr = Vec.tabulate(cfg.radius_factor*cfg.impulse_sample_point){i=> U(i * 16 + loadUnitAddrs(3))}

  val fsm_scan_record = RegInit(False)
  val drv_fsm = new StateMachine {
    val ts_drv_state = new StateFsm(fsm = oneShotDriverFSM(ts, U(ts_lu_addr), io.kernel_data_out))
    val ds_drv_state = new StateFsm(fsm = oneShotDriverFSM(ds, U(ds_lu_addr), io.kernel_data_out))
    val wv_drv_state = new StateFsm(fsm = burstDriverFSM(wv, wv_lu_addr, io.kernel_data_out, 16))
    val imp_drv_state = new StateFsm(fsm = burstDriverFSM(imp, imp_lu_addr, io.kernel_data_out, 16))

    val setup = new State with EntryPoint {
      whenIsActive {
        when(!fsm_scan_record) {
          fsm_scan_record.set()
          when(io.load_req(0)){
            goto(ts_drv_state)
          } otherwise {
            goto(ds_drv_state)
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
          goto(imp_drv_state)
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
