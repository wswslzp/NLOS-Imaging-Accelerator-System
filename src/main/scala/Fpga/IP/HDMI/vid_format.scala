package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

trait vid_format {
  val clk_pixel: Int
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

object vid_720x480p60 extends vid_format {
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