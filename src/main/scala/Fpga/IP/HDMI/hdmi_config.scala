package Fpga.IP.HDMI
import spinal.core._
import spinal.lib._

case class hdmi_config(
                        pixel_per_clock: Int = 2, // now is 2
                        bit_per_pixel: Int = 8, // could be 8, 10, 12, 16
                        tx_polarity: Polarity = LOW // negative sync ?
                      ) {
  def getPhyRate: Int = pixel_per_clock * bit_per_pixel * 10 / 8

  def getTxOS: Int = {
    if ((350 <= getPhyRate) && (getPhyRate < 500)) 1
    else if ((300 <= getPhyRate) && (getPhyRate < 350)) 2
    else if ((250 <= getPhyRate) && (getPhyRate < 300)) 3
    else if ((500 <= getPhyRate) && (getPhyRate < 1000)) 3
    else 0
  }
}
