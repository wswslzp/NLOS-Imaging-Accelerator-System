package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

case class t1_vid_gen(cfg: hdmi_config, vid_fm: vid_format) extends Component {
  val io = new Bundle {
    val de = out Bits(cfg.pixel_per_clock bit)
    val hsync = out Bits(cfg.pixel_per_clock bit)
    val vsync = out Bits(cfg.pixel_per_clock bit)
    val data = out Bits(cfg.pixel_per_clock * 48 bit)
  }

  val img_rom = Mem(Seq.tabulate(vid_fm.h_act * vid_fm.v_act){idx=>
    val x = idx / vid_fm.h_act
    val y = idx % vid_fm.h_act
    if(y < vid_fm.h_act/3){
      U(BigInt(255) << 40, 48 bit)
    } else if (vid_fm.h_act/3 <= y && y < ( vid_fm.h_act*2/3 )) {
      U(BigInt(255) << 24, 48 bit)
    } else {
      U(BigInt(255) << 8, 48 bit)
    }
  })

  val h_cnt = CounterFreeRun(vid_fm.getHTotal)
  val v_cnt = Counter(vid_fm.getVTotal)
  when(h_cnt.willOverflow){
    v_cnt.increment()
  }

  // front --- sync --- back --- act
  val hs_r = RegInit(False)
  val vs_r = RegInit(False)
  hs_r := ( vid_fm.h_front <= h_cnt.value ) && (h_cnt.value < (vid_fm.h_front + vid_fm.h_sync))
  vs_r := ( vid_fm.v_front <= v_cnt.value ) && (v_cnt.value < (vid_fm.v_front + vid_fm.v_sync))
  if(cfg.tx_polarity == HIGH){
    io.hsync.setAllTo(hs_r)
    io.vsync.setAllTo(vs_r)
  } else {
    io.hsync.setAllTo(!hs_r)
    io.vsync.setAllTo(!vs_r)
  }

  val de_r = RegInit(False)
  val de = (vid_fm.getHBlank <= h_cnt.value) && (h_cnt.value < vid_fm.getHTotal) && (vid_fm.getVBlank <= v_cnt.value) && (v_cnt.value < vid_fm.getVTotal)
  de_r := de
  io.de.setAllTo(de_r)

  var cur_x = v_cnt.value - vid_fm.getHBlank
  var cur_y = h_cnt.value - vid_fm.getVBlank
  val img_addr = cur_y + cur_x * vid_fm.h_act

  for(p <- 0 until cfg.pixel_per_clock) {
    io.data((p+1)*48-1 downto p*48) := img_rom.readSync(img_addr.resized).asBits
  }

}
