package FpgaTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import graphic.vga._
import spinal.lib.graphic.RgbConfig
import Fpga._
import Sim._

import java.io.File

object StreamToHDMITest extends App {
  val img = load_image("src/test/resource/fft_data/test.jpg", 256, 256)

  case class t_s2hdmi() extends Component {
    val io = new Bundle {
      val vga = master(Vga(RgbConfig(8, 8, 8)))
    }

    val s2hdmi = StreamToHDMI(vid_1280x720p60, 256, 256)
    io.vga.vSync := s2hdmi.io.vid.vs
    io.vga.hSync := s2hdmi.io.vid.hs
    io.vga.colorEn := s2hdmi.io.vid.de
    io.vga.color.r := s2hdmi.io.vid.data.r
    io.vga.color.g := s2hdmi.io.vid.data.g
    io.vga.color.b := s2hdmi.io.vid.data.b

    val inner_mem = Mem(Seq.tabulate(256*256){idx=>
      val r = idx / 256
      val c = idx % 256
      U(img(r, c).toInt, 8 bit)
    })

    val mem_waddr = Counter(256*256)
    s2hdmi.io.dat_in.valid.set()
    s2hdmi.io.dat_in.payload := inner_mem.readSync(mem_waddr.value.resized)
    when(s2hdmi.io.dat_in.fire){
      mem_waddr.increment()
    }

  }

  new File("rtl/t_s2hdmi").mkdir()
  SpinalConfig(
    targetDirectory = "rtl/t_s2hdmi"
  ).generateVerilog(t_s2hdmi())
}