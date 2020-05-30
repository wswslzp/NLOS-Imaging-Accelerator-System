import spinal.core._

object MyFFTMain extends App{
  import Core.MyFFT
  import Config.HComplexConfig
  SpinalConfig(
//    targetDirectory = "rtl"
    targetDirectory = "../fft/myfft_tb/rtl"
  ).generateVerilog(
    MyFFT(
      length = 128,
      cfg = HComplexConfig(8, 8)
//      use_pipeline = false
    )
  )
}
