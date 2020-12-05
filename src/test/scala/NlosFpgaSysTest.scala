import java.io.File

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Config.RsdKernelConfig._
import Fpga._
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import Sim.SimComplex._
import SimTest.NlosSystemSimTest.write_image

import scala.sys.process.{Process, ProcessLogger}

object NlosFpgaSysTest extends App{
  import Sim.RsdGenCoreArray.Driver._

  val withWave = true
  val waveDepth = 1

  val compiled = if(withWave){
    SimConfig
      .allOptimisation
      .withWave(waveDepth)
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32 --trace-threads 32")
      .compile(NlosFpgaSys(rsd_cfg))
  }else{
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(NlosFpgaSys(rsd_cfg))
  }
  var dd = 0
  var ff = 0

  /**
   * Initialize the DUT at the beginning of test bench
   * @param dut DUT
   */
  def dutInit(dut: NlosFpgaSys): Unit = {
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
   * Drive the data that RSD kernel genertor needs, includes
   * ring impulse, timeshift, distance and wave.
   * @param dut
   */
  def driveRsdData(dut: NlosFpgaSys): Unit = {
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
    dut.io.dc #= 0
    dut.io.fc #= 0
    dut.clockDomain.waitActiveEdgeWhere(dut.io.done.toBoolean)
    dut.clockDomain.waitSampling(10)
    simSuccess()
  }

  /**
   * Drive the input image into the DUT that fft2d will use.
   * @param dut
   */
  def driveImage(dut: NlosFpgaSys): Unit = {
    for(f <- rsd_cfg.freqRange){
      dut.io.img_in.valid #= true
      for(x <- rsd_cfg.rowRange){
        for(y <- rsd_cfg.colRange){
          dut.io.img_in.payload #= uin(f)(x, y)
          dut.clockDomain.waitSampling()
        }
      }
      dut.io.img_in.valid #= false
    }
  }

  /**
   * catch the result from NLOS system only once per invocation
   * @param dut
   * @return An output image result of a depth
   */
  def catchResult(dut: NlosFpgaSys): DenseMatrix[Complex] = {
    val ret = DenseMatrix.zeros[Complex](dut.cfg.kernel_size.head, dut.cfg.kernel_size.last)
    dut.clockDomain.waitActiveEdgeWhere(dut.io.result.valid.toBoolean)
    for(r <- dut.cfg.rowRange){
      for(c <- dut.cfg.colRange){
        ret(r, c) = dut.io.result.payload(c).toComplex
      }
      dut.clockDomain.waitSampling()
    }
    ret
  }

  val uout = Array.fill(rsd_cfg.depth_factor)(
    DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
  )
  compiled.doSim("NlosFpgaSys_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dutInit(dut)
    dut.clockDomain.waitSampling()

    fork {
      SimTimeout(40000000)
    }

    forkJoin(

      // Drive data
      () => driveRsdData(dut),
      () => driveImage(dut),

      // Monitor result
      () => {
        while(true){
          uout(dd) = catchResult(dut)
        }
      }
    )
  }


  val uout_abs = uout.map(_.map(_.abs))

  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last) { (x, y)=>
    var umax = 0d
    for(d <- 0 until rsd_cfg.depth_factor) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/NlosFpgaSys/nlos_hard_out.jpg")

  if(withWave){
    val nullLogger = ProcessLogger(_=>{})
    println("Converting vcd to vpd...")
    Process("vcd2fsdb tb/NlosFpgaSys/NlosFpgaSys_tb.vcd tb/NlosFpgaSys/NlosFpgaSys_tb.vpd") ! nullLogger
    println("Convert done.")
    Process("dve -full64 -vpd tb/NlosFpgaSys/NlosFpgaSys_tb.vpd") !!
  }

}
