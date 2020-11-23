package RsdKenelGenTest

import Config._
import Core.RsdGenCoreArray._
import Sim.RsdGenCoreArray._
import Sim.SimComplex._
import Sim.SimFix._
import breeze.linalg.{DenseMatrix, DenseVector, csvwrite, fliplr}
import breeze.math.Complex
import spinal.core.sim._
import Config.RsdKernelConfig._
import breeze.signal._
import java.io._

import SimTest.NlosSystemSimTest.write_image

object RsdKernelGenMain1 extends App{

  val coef: Array[DenseMatrix[Complex]] = Computation.generateCoef(wave, distance, timeshift)
  val rsd: Array[Array[DenseVector[Complex]]] = Computation.generateRSDRadKernel(coef, impulse)
  val hardware_rsd = Array.fill(rsd.length, rsd.head.length){
    DenseVector.fill(rsd.head.head.length)(Complex(0d, 0d))
  }

  val withWave = true
  val waveDepth = 2
  val module_compiled = if(withWave){
    SimConfig
      .withWave(waveDepth)
      .allOptimisation
      .workspacePath("tb")
      .workspaceName("RsdKernelGen1_tb")
      .compile(RsdKernelGen(rsd_cfg))
  }else{
    SimConfig
      .allOptimisation
      .workspacePath("tb")
      .workspaceName("RsdKernelGen1_tb")
      .compile(RsdKernelGen(rsd_cfg))
  }

  var dd = 0
  var ff = 0

  module_compiled.doSim("RsdKernelGen1_tb"){dut=>
    dut.clockDomain.forkStimulus(2)
    dut.io.timeshift.valid #= false
    dut.io.distance.valid #= false
    dut.io.wave.valid #= false
    dut.io.ring_impulse.valid #= false

    dut.clockDomain.waitSampling()

    forkJoin(

      // Driver
      () => {
        for(d_id <- rsd_cfg.depthRange){
          for(f_id <- rsd_cfg.freqRange){
            dd = d_id
            ff = f_id
            dut.io.timeshift.payload #= timeshift(f_id, d_id) // df = (0, 0)
            dut.clockDomain.waitSampling()
            dut.io.timeshift.valid #= true

            dut.io.distance.payload #= distance(f_id, d_id)
            dut.clockDomain.waitSampling()
            dut.io.distance.valid #= true

            dut.clockDomain.waitSampling(7)

            forkJoin(
              // wave driver
              () => {
                for(r <- rsd_cfg.radiusRange){
                  dut.io.wave.valid #= true
                  dut.io.wave.payload #= wave(r, d_id)
                  dut.clockDomain.waitSampling()
                }
                dut.io.wave.valid #= false
              }
              ,
              // ring impulse driver
              () => {
                dut.clockDomain.waitSampling(dut.pRsdKernelGen.W2CLatency)
                for(r <- rsd_cfg.radiusRange){
                  dut.io.ring_impulse.valid #= true
                  dut.io.ring_impulse.payload.zipWithIndex.foreach{ case (dat, i) =>
                    dat #= impulse(i, r)
                  }
                  dut.clockDomain.waitSampling()
                }
                dut.io.ring_impulse.valid #= false
              }
            )

            dut.clockDomain.waitSampling(10)

            dut.io.timeshift.valid #= false
            dut.io.distance.valid #= false
          }
        }

        dut.clockDomain.waitSampling(10)
        simSuccess()
      }
      ,

      // Monitor
      () => {
        while(true) {
          val cur_d = dd
          val cur_f = ff
          dut.clockDomain.waitActiveEdgeWhere(dut.io.kernel.valid.toBoolean)
          println(s"Now dd is $dd and ff is $ff, but cur_d is $cur_d and cur_f is $cur_f")
          for(idx <- dut.io.kernel.payload.indices){
            hardware_rsd(cur_d)(cur_f)(idx) = dut.io.kernel.payload(idx).toComplex
          }
        }
      }


    )
  }


  val uin_fft = uin.map(fourierTr(_))
  val kernel_size = (uin.head.rows, uin.head.cols)
  new File("tb/RsdKernelGen1_tb/rad").mkdirs()
  for(did <- rsd_cfg.depthRange){
    val rad_d = DenseMatrix.fill(rsd_cfg.freq_factor, rsd_cfg.impulse_sample_point)(0d)
    for(fid <- rsd_cfg.freqRange){
      rad_d(fid, ::) := hardware_rsd(did)(fid).map(_.abs).t
    }
    csvwrite(
      new File(s"tb/RsdKernelGen1_tb/rad/rad_d$did.csv"),
      rad_d
    )
  }
  new File("tb/RsdKernelGen1_tb/uout").mkdirs()
  val uout = Array.tabulate(rsd_cfg.depth_factor) {depth =>
    val uout_f = DenseMatrix.fill(kernel_size._1, kernel_size._2)(Complex(0, 0))
    for(f <- 0 until rsd_cfg.freq_factor) {
      val rsd_kernel_rad = hardware_rsd(depth)(f)
      val rsd_kernel = Computation.restoreRSD(rsd_kernel_rad, kernel_size)
      uout_f += rsd_kernel *:* uin_fft(f)
    }
    val uout_d = iFourierTr(uout_f)
    uout_d
  }
  val uout_abs = uout.map(_.map(_.abs))
  val uout_abs_max: DenseMatrix[Double] = DenseMatrix.tabulate(kernel_size._1, kernel_size._2) { (x, y)=>
    var umax = 0d
    for(d <- 0 until rsd_cfg.depth_factor) {
      if (uout_abs(d)(x, y) > umax) {
        umax = uout_abs(d)(x, y)
      }
    }
    umax
  }
  println("Output image has been generated!")
  println(s"output size: cols = ${uout_abs_max.cols}")

  val uout_abs_max_flip = fliplr(uout_abs_max)
  write_image(uout_abs_max_flip, "tb/RsdKernelGen1_tb/nlos_out.jpg")

}
