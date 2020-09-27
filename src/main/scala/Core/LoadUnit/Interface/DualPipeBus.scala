package Core.LoadUnit.Interface

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._

case class PipeConfig(
                     dw: Int,
                     aw: Int
                     )

class Pipe(pipeConfig: PipeConfig) extends Bundle {
  val data = Bits(pipeConfig.dw bit)
  val addr = UInt(pipeConfig.aw bit)
}

class DualPipeBus(pipeConfig: PipeConfig) extends Bundle with IMasterSlave {
  val cmd = Stream(Fragment(new Pipe(pipeConfig)))
  val rsp = Stream(Fragment(new Pipe(pipeConfig)))

  override def asMaster(): Unit = {
    master(cmd)
    slave(rsp)
  }
}

