import spinal.core._
import spinal.core.sim._
import spinal.lib._
import Core._
import Util._
import Config._
import breeze._
import scala.collection.mutable._

object MyFFTMain extends App{
  val length = 128

  case class SFFT(length: Int) extends Component {
    val data_in = slave(Flow(HComplex(8, 8)))
    val data_out = master(Flow(HComplex(8, 8)))

    data_out <> MyFFT.sfft(data_in, length)
  }

  SimConfig
    .withConfig(SpinalConfig(defaultClockDomainFrequency = FixedFrequency(1 GHz)))
    .withWave
    .workspacePath("tb")
    .allOptimisation
    .compile(SFFT(length))
    .doSim("SFFT_tb") {dut =>
      import linalg._
      import stats.distributions._
      println("begin simu")
      dut.data_in.valid #= false
      dut.clockDomain.doStimulus(2)
      SimTimeout(1000)

      // generate the test vector
      val test_vector_x = DenseVector.rangeF(0, 10, step = (10/length))(0 until length)
      val noise = Gaussian(0, 1)
      val test_vector_y: DenseVector[Double] = test_vector_x.map( d =>
        Math.cos(d) + 0.4 * Math.sin(3*d) + d/10 + noise.sample()
      )

      println(s"input: ${test_vector_y.toString()}")

      // driver
      val driver = fork {
        dut.data_in.valid #= true
        for (i <- 0 until length) {
          dut.data_in.payload.real.raw #= (256 * test_vector_y(i)).toInt
          dut.data_in.payload.imag.raw #= 0
          dut.clockDomain.waitSampling()
        }
        dut.data_in.valid #= false
        dut.clockDomain.waitSampling(2)
      }

      // monitor
      val output_real_queue = Queue[Double]()
      val output_imag_queue = Queue[Double]()
      dut.clockDomain onSamplings {
        if (dut.data_out.valid.toBoolean) {
          output_real_queue.enqueue(
            dut.data_out.payload.real.raw.toInt / 256f
          )
          output_imag_queue.enqueue(
            dut.data_out.payload.imag.raw.toInt / 256f
          )
        }
      }

      // model
      val fft_sind = breeze.signal.fourierTr(test_vector_y).map(_ / length)

      // scoreboard
      driver.join()
      dut.clockDomain.waitSampling(20)
      println(s"real_output: ${output_real_queue.toString()}")
      println(s"imag_output: ${output_imag_queue.toString()}")
      println(s"true_result: ${fft_sind.toString()}")
      import scala.util.control._
      val loop = new Breaks
      loop.breakable {
        for(i <- 0 until length) {
          val out_real = output_real_queue.dequeue()
          val out_imag = output_imag_queue.dequeue()
          val real_near = Math.abs(fft_sind(i).real - out_real) < 1
          val imag_near = Math.abs(fft_sind(i).imag - out_imag) < 1
          if (!real_near || !imag_near) {
            simFailure(s"The fft simulation failed, the $i th output is ${out_real} + $out_imag j, but the result is ${fft_sind(i).real} + ${fft_sind(i).imag} j")
            loop.break()
          }
        }
      }
      simSuccess()


    }





//  SpinalConfig(
//    targetDirectory = "../../fft/SFFT_tb/rtl"
////    targetDirectory = "../../fft/myfft_tb/rtl"
//  ).generateVerilog(
////    MyFFT(
////      length = 128,
////      cfg = HComplexConfig(8, 8),
////      use_pipeline = false
////    )
//    SFFT()
//  )

}
