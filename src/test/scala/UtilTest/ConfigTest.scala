package UtilTest

import spinal.core._
import spinal.core.sim._
import spinal.lib._

object ConfigTest extends App{
  import Config.RsdKernelConfig._
  println(s"kernel_config: ${rsd_cfg.getKernelConfig}")
  println(s"uin_config: ${rsd_cfg.getUinConfig}")
  println(s"fuin_config: ${rsd_cfg.getFUinConfig}")
  println(s"mac_config: ${rsd_cfg.getMACDatConfig}")
  println(s"result_config: ${rsd_cfg.getResultConfig}")
}
