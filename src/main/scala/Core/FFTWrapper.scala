package Core

import spinal.core._

case class FFTWrapper(width:Int, point:Int) extends BlackBox {
  val io = new Bundle {
    val clk = in Bool()
    val reset = in Bool()
    val next = in Bool()
    val next_out = out Bool()
    val data_in = List.fill(point)(in UInt(width bit))
    val data_out = List.fill(point)(out UInt(width bit))
  }

  noIoPrefix()
  mapCurrentClockDomain(
    clock = io.clk,
    reset = io.reset
  )

  private def renameIO() : Unit = {
    io flattenForeach {bt=>
      if(bt.getName().contains("data_in")) bt.setName(
        bt.getName().replace("data_in_", "i")
      )

      if(bt.getName().contains("data_out")) bt.setName(
        bt.getName().replace("data_out_", "o")
      )
    }
  }

  private def renameModule(): Unit = {
    val intw = 8
    val fracw = 8
    val point_log = log2Up(point)
    val name = f"fftn${point_log}fx${intw}${fracw}"
    setDefinitionName(name)
  }

  addPrePopTask(renameModule)
  addPrePopTask(renameIO)
  addRTLPath("../rtl/fftn7fx88.v")
}
