package Fpga.IP

import spinal.core._
import spinal.lib._

case class POR(ip_name: String = "por") extends BlackBox {
  setDefinitionName(ip_name)
  val ninit_done = out Bool()
}
