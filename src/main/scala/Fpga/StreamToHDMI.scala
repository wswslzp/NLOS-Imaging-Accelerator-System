package Fpga

import spinal.core._
import spinal.lib._
import spinal.lib.graphic._
import Util.HotMap

case class HdmiConfig(
                        pixel_per_clock: Int = 2, // now is 2
                        bit_per_pixel: Int = 8, // could be 8, 10, 12, 16
                        tx_polarity: Polarity = LOW, // negative sync ?
                        para_data_order: Boolean = true // false: CBGR, true: RGBC
                      ) {
  def getPhyRate(clk_pixel: Double): Int = ( clk_pixel * bit_per_pixel * 10 / 8 ).toInt // 27*8*10/8 = 20

  def getTxOS(clk_pixel: Double): Int = {
    if ((350 <= getPhyRate(clk_pixel)) && (getPhyRate(clk_pixel) < 500)) 1
    else if ((300 <= getPhyRate(clk_pixel)) && (getPhyRate(clk_pixel) < 350)) 2
    else if ((250 <= getPhyRate(clk_pixel)) && (getPhyRate(clk_pixel) < 300)) 3
    else if ((500 <= getPhyRate(clk_pixel)) && (getPhyRate(clk_pixel) < 1000)) 3
    else 0
  }
}

trait VideoFormat {
  val clk_pixel: Double
  val v_front: Int
  val v_sync: Int
  val v_act: Int
  val v_back: Int
  val v_pol: Polarity
  val h_front: Int
  val h_sync: Int
  val h_act: Int
  val h_back: Int
  val h_pol: Polarity

  def getVTotal: Int = v_front + v_sync + v_back + v_act
  def getVBlank: Int = v_front + v_sync + v_back
  def getHTotal: Int = h_front + h_sync + h_back + h_act
  def getHBlank: Int = h_front + h_sync + h_back
}

object vid_720x480p60 extends VideoFormat {
  override val clk_pixel = 27
  override val v_front = 9
  override val v_sync = 6
  override val v_back = 45 - v_sync - v_front
  override val v_act = 480
  override val v_pol = LOW
  override val h_front = 16
  override val h_sync = 62
  override val h_back = 138 - h_sync - h_front
  override val h_act = 720
  override val h_pol = LOW
}

object vid_1920x1080p60 extends VideoFormat {
  override val clk_pixel = 148.5
  override val v_front = 4
  override val v_sync = 5
  override val v_back = 45 - v_sync - v_front
  override val v_act = 1080
  override val v_pol = HIGH
  override val h_front = 88
  override val h_sync = 44
  override val h_back = 280 - h_sync - h_front
  override val h_act = 1920
  override val h_pol = HIGH
}

object vid_1280x720p60 extends VideoFormat {
  override val clk_pixel = 74.25
  override val v_front = 5
  override val v_sync = 5
  override val v_back = 20
  override val v_act = 720
  override val v_pol = HIGH
  override val h_front = 110
  override val h_sync = 40
  override val h_back = 220
  override val h_act = 1280
  override val h_pol = HIGH
}

case class HdmiVideoBus() extends Bundle with IMasterSlave {
  val de = Bool()
  val data = Rgb(8, 8, 8)
  val vs = Bool()
  val hs = Bool()

  override def asMaster(): Unit = {
    out(de, data, hs, vs)
  }
}

case class StreamToHDMI(vid_fm: VideoFormat, img_rows: Int, img_cols: Int) extends Component {
  val io = new Bundle {
    val dat_in = slave Stream UInt(8 bit)
    val vid = master (HdmiVideoBus())
  }
  val video_mem_read_clk = ClockDomain.external("vid")

  // declare a video memory
  val video_mem = Mem(UInt(8 bit), BigInt(vid_fm.v_act * vid_fm.h_act))

  // write logic
  val dat_in = Reg(cloneOf(io.dat_in.payload))
  when(io.dat_in.valid){
    dat_in := io.dat_in.payload
  }
  val img_row_cnt = Counter(img_rows)
  val img_col_cnt = Counter(img_cols)
  when(io.dat_in.valid){
    img_col_cnt.increment()
  }
  when(img_col_cnt.willOverflow){
    img_row_cnt.increment()
  }
  val img_row_val = img_row_cnt.value + (vid_fm.v_act/2 - img_rows/2)
  val img_col_val = img_col_cnt.value + (vid_fm.h_act/2 - img_cols/2)
  val video_mem_waddr = RegNext( img_row_val * vid_fm.h_act + img_col_val )
  video_mem.write(video_mem_waddr.resized, dat_in)

  // read logic under `video_mem_read_clk` clock domain
  val vid_read = new ClockingArea(video_mem_read_clk){
    val h_cnt = CounterFreeRun(vid_fm.getHTotal)
    val v_cnt = Counter(vid_fm.getVTotal)
    when(h_cnt.willOverflow) {
      v_cnt.increment()
    }

    // front --- sync --- back --- act
    val hs_r = RegInit(False)
    val vs_r = RegInit(False)
    hs_r := ( vid_fm.h_front <= h_cnt.value ) && (h_cnt.value < (vid_fm.h_front + vid_fm.h_sync)) // High when in sync period
    vs_r := ( vid_fm.v_front <= v_cnt.value ) && (v_cnt.value < (vid_fm.v_front + vid_fm.v_sync))
    if(vid_fm.h_pol == HIGH) {
      io.vid.hs := hs_r
    } else {
      io.vid.hs := !hs_r
    }
    if(vid_fm.v_pol == HIGH) {
      io.vid.vs := vs_r
    } else {
      io.vid.vs := !vs_r
    }

    val de_r = RegInit(False)
    val de = (vid_fm.getHBlank <= h_cnt.value) && (h_cnt.value < vid_fm.getHTotal) && (vid_fm.getVBlank <= v_cnt.value) && (v_cnt.value < vid_fm.getVTotal)
    de_r := de
    io.vid.de := de_r
    io.dat_in.ready := True

    val cur_x = v_cnt.value - vid_fm.getVBlank
    val cur_y = h_cnt.value - vid_fm.getHBlank
    val video_mem_raddr = cur_x * vid_fm.h_act + cur_y
    val video_mem_rdata = video_mem.readSync(video_mem_raddr.resized, enable = de)
    val hot_map = HotMap()
    hot_map.io.gray := video_mem_rdata
    io.vid.data.r := hot_map.io.red
    io.vid.data.g := hot_map.io.green
    io.vid.data.b := hot_map.io.blue
  }

}
