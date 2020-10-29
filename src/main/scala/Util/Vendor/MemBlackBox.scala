package Util.Vendor

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.MemManager.MemConfig

class MemBlackBox(mem_cfg: MemConfig) extends BlackBox {
  setBlackBoxName(mem_cfg.name)
}
