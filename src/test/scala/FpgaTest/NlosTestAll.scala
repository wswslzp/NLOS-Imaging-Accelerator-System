package FpgaTest

import spinal.core.sim._
import Config._
import Config.RsdKernelConfig._
import Sim.NlosCore.Driver._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga._
import breeze.linalg._
import java.io.File

object NlosTestAll extends App {
  val compiled = SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(NlosNoDriver(rsd_cfg))

  val rows = compiled.dut.post_proc.over_sample_factor * rsd_cfg.rows
  val cols = compiled.dut.post_proc.over_sample_factor * rsd_cfg.cols
  println(s"row is $rows, col is $cols")
  val uout_pp = DenseMatrix.zeros[Double](rows, cols)

  def testOnDataset(ds: Dataset): Unit ={
    compiled.doSim("NlosTestAll") { dut =>
      dut.clockDomain.forkStimulus(2)
      dutInit(dut)
      dut.clockDomain.waitSampling()

      fork {
        SimTimeout(40000000)
      }

      forkJoin(

        // Drive data
        () => driveRsdData(dut),
        () => driveImage(dut, ds),

        // Monitor result
        () => {
          uout_pp := catchResult(dut)
        }
      )
    }
  }

  new File("tmp/result_imgs").mkdirs()
  all_data_set.foreach{ds=>
    val dataset_name = ds.pathToData.split("/").last
    println(s"Testing dataset: $dataset_name")
    scala.sys.wait(10)
    testOnDataset(ds)
    testPostProc(uout_pp, ds, "tmp/result_imgs")
  }
}
