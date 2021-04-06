package Sim.NlosCore

import Fpga.{NlosCore, NlosNoDriver}
import Config.RsdKernelConfig._
import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Sim.SimComplex._

object Driver {
  import Sim.RsdGenCoreArray.Driver._
  var dd = 0
  var ff = 0

  /**
   * Initialize the DUT at the beginning of test bench
   * @param dut DUT
   */
  def dutInit(dut: NlosCore): Unit = {
    dut.io.data_in.aw.valid #= false
    dut.io.data_in.aw.burst #= 0
    dut.io.data_in.aw.len #= 0
    dut.io.data_in.aw.size #= 0
    dut.io.data_in.aw.addr #= 0
    dut.io.data_in.w.valid #= false
    dut.io.data_in.w.data #= 0
    dut.io.data_in.b.ready #= true
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.io.img_in.valid #= false
  }

  /**
   * Initialize the DUT at the beginning of test bench
   * @param dut DUT
   */
  def dutInit(dut: NlosNoDriver): Unit = {
    dut.io.data_in.aw.valid #= false
    dut.io.data_in.aw.burst #= 0
    dut.io.data_in.aw.len #= 0
    dut.io.data_in.aw.size #= 0
    dut.io.data_in.aw.addr #= 0
    dut.io.data_in.w.valid #= false
    dut.io.data_in.w.data #= 0
    dut.io.data_in.b.ready #= true
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.io.img_in.valid #= false
    dut.io.result.ready #= true
  }

  /**
   * Drive the data that RSD kernel genertor needs, includes
   * ring impulse, timeshift, distance and wave.
   * @param dut
   */
  def driveRsdData(dut: NlosCore): Unit = {
    val rsdDriver = RsdDriver(dut.io.data_in, dut.clockDomain)

    for(d <- dut.cfg.depthRange) {
      dd = d
      dut.io.dc #= d
      for(f <- dut.cfg.freqRange) {
        ff = f
//        println(s"Now is ($d, $f)")
        dut.io.fc #= f
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 1) == 1) {
          rsdDriver.driveComplexData(timeshift(f, d), dut.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
          rsdDriver.driveData(1, dut.loadUnitAddrs(0) + 1)
        }
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 2) == 2) {
          rsdDriver.driveDoubleData(distance(f, d), dut.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
          rsdDriver.driveData(1, dut.loadUnitAddrs(1) + 1)
        }
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 4) == 4) {
          rsdDriver.driveDoubleData(wave(::, d), dut.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
          rsdDriver.driveData(1, dut.loadUnitAddrs(2) + dut.cfg.radius_factor)
        }
        dut.clockDomain.waitSampling()
        if((d == 0) && (f == 0)) {
          if((dut.io.load_req.toInt & 8) == 8) {
            rsdDriver.driveDoubleData(impulse, dut.loadUnitAddrs(3), rsd_cfg.imp_cfg.fracw)
            rsdDriver.driveData(1, dut.loadUnitAddrs(3) + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
          }
          dut.clockDomain.waitSampling()
        }
        dut.clockDomain.waitActiveEdgeWhere(dut.io.cnt_incr.toBoolean)
      }
    }

    // All the data to rgca now is completed
    println(s"Now waiting for result pipe out.")
//    dut.clockDomain.waitActiveEdgeWhere(dut.io.done.toBoolean)
    println(s"Done")
    dut.clockDomain.waitSampling(10)
    simSuccess()
  }

  /**
   * Drive the data that RSD kernel genertor needs, includes
   * ring impulse, timeshift, distance and wave.
   * @param dut
   */
  def driveRsdData(dut: NlosNoDriver): Unit = {
    val rsdDriver = RsdDriver(dut.io.data_in, dut.clockDomain)

    for(d <- dut.cfg.depthRange) {
      dd = d
      dut.io.dc #= d
      for(f <- dut.cfg.freqRange) {
        ff = f
        println(s"Now is ($d, $f)")
        dut.io.fc #= f
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 1) == 1) {
          rsdDriver.driveComplexData(timeshift(f, d), dut.nlos_core.loadUnitAddrs(0), dut.cfg.timeshift_cfg)
          rsdDriver.driveData(1, dut.nlos_core.loadUnitAddrs(0) + 1)
        }
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 2) == 2) {
          rsdDriver.driveDoubleData(distance(f, d), dut.nlos_core.loadUnitAddrs(1), dut.cfg.distance_cfg.fracw)
          rsdDriver.driveData(1, dut.nlos_core.loadUnitAddrs(1) + 1)
        }
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 4) == 4) {
          rsdDriver.driveDoubleData(wave(::, d), dut.nlos_core.loadUnitAddrs(2), dut.cfg.wave_cfg.fracw)
          rsdDriver.driveData(1, dut.nlos_core.loadUnitAddrs(2) + dut.cfg.radius_factor)
        }
        dut.clockDomain.waitSampling()
        if((dut.io.load_req.toInt & 8) == 8) {
          rsdDriver.driveDoubleData(impulse, dut.nlos_core.loadUnitAddrs(3), rsd_cfg.imp_cfg.fracw)
          rsdDriver.driveData(1, dut.nlos_core.loadUnitAddrs(3) + dut.cfg.radius_factor * dut.cfg.impulse_sample_point)
        }
        dut.clockDomain.waitSampling()
        dut.clockDomain.waitActiveEdgeWhere(dut.io.cnt_incr.toBoolean)
      }
    }

    // All the data to rgca now is completed
    println(s"Now waiting for result pipe out.")
    dut.io.dc #= (1 << dut.io.dc.getWidth)-1
    dut.io.fc #= (1 << dut.io.fc.getWidth)-1
    dut.clockDomain.waitActiveEdgeWhere(dut.io.done.toBoolean)
    dut.clockDomain.waitSampling(10)
    simSuccess()
  }

  /**
   * Drive the input image into the DUT that fft2d will use.
   * @param dut
   */
  def driveImage(dut: NlosCore): Unit = {
    for(f <- rsd_cfg.freqRange){
      dut.io.img_in.valid #= true
      for(x <- rsd_cfg.rowRange){
        for(y <- rsd_cfg.colRange){
          dut.io.img_in.payload #= uin(f)(x, y)
          dut.clockDomain.waitSampling()
        }
      }
      dut.io.img_in.valid #= false
      dut.clockDomain.waitActiveEdgeWhere(dut.io.fft_comp_end.toBoolean)
    }
  }

  /**
   * Drive the input image into the DUT that fft2d will use.
   * @param dut
   */
  def driveImage(dut: NlosNoDriver): Unit = {
    for(f <- rsd_cfg.freqRange){
      dut.io.img_in.valid #= true
      for(x <- rsd_cfg.rowRange){
        for(y <- rsd_cfg.colRange){
          dut.io.img_in.payload #= uin(f)(x, y)
          dut.clockDomain.waitSampling()
        }
      }
      dut.io.img_in.valid #= false
      dut.clockDomain.waitActiveEdgeWhere(dut.io.fft_comp_end.toBoolean)
    }
  }

}
