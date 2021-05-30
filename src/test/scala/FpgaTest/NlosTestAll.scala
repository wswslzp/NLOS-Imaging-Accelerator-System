package FpgaTest

import spinal.core.sim._
import Config._
import Config.RsdKernelConfig._
import Sim.NlosCore.Driver._
import Sim.NlosCore.Monitor._
import Sim.NlosCore.Tester._
import Fpga._
import breeze.linalg._
import breeze.math._
import java.io.File

object NlosTestAll {

  def main(args: Array[String]): Unit = {
    val compiled = SimConfig
      .allOptimisation
      .workspacePath("tb")
      .addSimulatorFlag("-j 32 --threads 32")
      .compile(NlosCore(rsd_cfg))

    val rows = rsd_cfg.rows
    val cols = rsd_cfg.cols
    println(s"row is $rows, col is $cols")
    val uout_pp = DenseMatrix.zeros[Double](rows, cols)
    val uout = Array.fill(rsd_cfg.depth_factor)(
      DenseMatrix.zeros[Complex](rsd_cfg.kernel_size.head, rsd_cfg.kernel_size.last)
    )

    def testOnDataset(ds: Dataset, id: Int): Unit ={
      compiled.doSim(s"NlosTestAll-ds_$id") { dut =>
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
            var uout_d = 0
            while (true) {
              uout(uout_d) = catchResult(dut)
              uout_d += 1
            }
          }
        )
      }
    }

    new File("tmp/result_imgs").mkdirs()
    val ds_id = args.head.toInt
    val ds = all_data_set(ds_id)
    println(s"Testing dataset: ${ds.pathToData.split('/').last}")
    testOnDataset(ds, ds_id)
//    all_data_set.foreach{ds=>
//      val dataset_name = ds.pathToData.split("/").last
//      println(s"Testing dataset: $dataset_name")
//      Thread.sleep(1000)
//      testOnDataset(ds, all_data_set.indexOf(ds))
//      testPostProc(uout_pp, ds, "tmp/result_imgs")
    testFinal(uout, ds, "tmp/result_imgs")
//    }
  }
}
