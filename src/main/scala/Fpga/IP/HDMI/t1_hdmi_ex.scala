package Fpga.IP.HDMI
import spinal.core._
import spinal.core.internals.Operator
import spinal.lib._

case class t1_hdmi_ex(pixels: Int) extends BlackBox {
  val io = new Bundle {
    val clk = new Bundle {
      val clk = in Bool()
    }

    val cpu_reset = new Bundle {
      val reset = in Bool() // reset = 1
    }

    val iopll_0_locked_export = out Bool()
    val iopll_0_refclk_clk = in Bool()

    val vid_clk_clk = out Bool()
    val hdmi_0 = new Bundle {
      val tx_clk_clk = in Bool()
      val mode_mode = in Bool()
      val clocked_video_vid = new Bundle {
        val de = in Bits(pixels bit)
        val hsync = in Bits(pixels bit)
        val vsync = in Bits(pixels bit)
        val data = in Bits(pixels * 48 bit)
      }

      val phy_interface = new Bundle {
        val os = in Bits(2 bit)
        val out_r = out Bits(20 bit)
        val out_g = out Bits(20 bit)
        val out_b = out Bits(20 bit)
        val out_c = out Bits(20 bit)
      }

      val in_lock = new Bundle {
        val in_lock = in Bool()
      }

      val tx_hpd = new Bundle {
        val tx_hpd = in Bool()
        val tx_hpd_req = out Bool()
      }

      val scrambler_enable = new Bundle {
        val scrambler_enable = in Bool()
      }

      val tmds_bit_clock_ratio = new Bundle {
        val tmds_bit_clock_ratio = in Bool()
      }

      val ctrl = new Bundle {
        val ctrl = in Bits(12 bit)
      }
    }

    val xcvr = new Bundle {
      val fpll_s10_htile_0 = new Bundle {
        val pll_cal_busy = new Bundle {
          val pll_cal_busy = out Bool()
        }
        val tx_bonding_clocks_clk = out Bits(6 bit)
        val pll_locked_pll_locked = out Bool()
        val pll_refclk0_clk = in Bool()
      }

      val native_s10_htile_0 = new Bundle {
        val tx = new Bundle {
          val cal_busy_tx_cal_busy = in Bits(4 bit)
          val bonding_clocks_clk = in Bits(24 bit)
          val serial_data_tx_serial_data = out Bits(4 bit)
          val coreclkin_clk = in Bits(4 bit)
          val clkout_clk = out Bits(4 bit)
          val parallel_data_tx_parallel_data = in Bits(80 bit)
          val polinv_tx_polinv = in Bits(4 bit)
        }
        val unused_tx_parallel_data_unused_tx_parallel_data = in Bits(240 bit)
      }

      val reset_control_s10_0 = new Bundle {
        val tx_ready_tx_ready = out Bits(4 bit)
        val pll_locked_pll_locked = in Bool()
        val pll_select_pll_select = in Bool()
        val tx_cal_busy_tx_cal_busy = in Bits(4 bit)
      }
    }

  }

  noIoPrefix()
  mapCurrentClockDomain(
    clock = io.clk.clk, reset = io.cpu_reset.reset
  )
}
