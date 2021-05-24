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

  val uout_pp = DenseMatrix.zeros[Double](rsd_cfg.kernel_size.head*2, rsd_cfg.kernel_size.last*2)

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
    testOnDataset(ds)
    testPostProc(uout_pp, ds, "tmp/result_imgs")
  }
}
