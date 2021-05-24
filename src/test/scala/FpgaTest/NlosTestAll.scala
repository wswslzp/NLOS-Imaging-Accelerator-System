package FpgaTest

import spinal.core.sim._
import Config._
import Config.RsdKernelConfig._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga.NlosFpgaCore
import breeze.linalg._

import java.io.File
import scala.sys.process.{Process, ProcessLogger}

object NlosTestAll extends App {
  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

  def moduleWithDataset(ds: Dataset): SimCompiled[NlosFpgaCore] = {
    SimConfig.allOptimisation.workspacePath("tb").compile({
      val module = NlosFpgaCore(rsd_cfg)
      module.nlos_driver.img_drver.setDataset(ds)
      module
    })
  }

  def testFunc(dut: NlosFpgaCore): Unit = {
    dut.clockDomain.forkStimulus(3)
    dut.hdmi_if.video_mem_read_clk.forkStimulus(2)
    dut.io.sys_init #= false
    dut.clockDomain.waitSampling()

    fork {
      while(true){
        dut.clockDomain.waitSamplingWhere(dut.nlos_driver.io.cnt_incr.toBoolean)
        println(s"now is (${dut.nlos_driver.io.dc.toInt}, ${dut.nlos_driver.io.fc.toInt})")
      }
    }

    dut.io.sys_init #= true
    uout_pp := catchResult(dut)
    dut.clockDomain.waitSamplingWhere(dut.io.done.toBoolean)
    simSuccess()
  }

  new File("tmp/result_imgs").mkdirs()
  all_data_set.foreach{ds=>
    val dataset_name = ds.pathToData.split("/").last
    println(s"Testing dataset: $dataset_name")
    moduleWithDataset(ds).doSim("NlosTestAll")(testFunc)
    testPostProc(uout_pp, "tmp/result_imgs", ds)
  }
}
