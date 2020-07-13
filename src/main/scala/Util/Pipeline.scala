package Util

import spinal.core._

import scala.collection.mutable
import spinal.lib.generator._

trait Pipeline {
  private var pipeline_switches = mutable.Map[Int, Boolean]()

  // TODO: This method has some problems
  //       When user call stage(), this method set the pipeline level dynamically,
  //       but the openAllStage() method could not actually control the stage.
  //       The Generator Framework may help.
  def setPipelineLevel(l: Int): Unit = { pipeline_switches += l -> true }
  def deleteStage(lv: Int): Unit = { pipeline_switches(lv) = false }
  def openAllStage(): Unit = pipeline_switches.keys.foreach { key =>
    pipeline_switches(key) = true
  }
  def closeAllStage(): Unit = pipeline_switches.keys.foreach { key =>
    pipeline_switches(key) = false
  }
  def getStagesCount: Int = pipeline_switches.size

  def stage[T <: Data](dat_prestage: T, level: Int): T = {
    // stage the input data, record the current pipeline level
    // with a switch to change stage existence
    //TODO: be able to find the previous node is in the correct level
    // switch control whether the stage exists.
    setPipelineLevel(level)
    val dat_staged = if (pipeline_switches(level)) {
      RegNext(dat_prestage)
    } else {
      dat_prestage
    }
    dat_staged
  }

  // continually add some stages on one node.
  def stage[T <: Data](dat_prestage: T, level_range: Range): T = {
    val ret = level_range.map(_ -> cloneOf(dat_prestage)).toMap
    val lvh = level_range.head
    val idx_range = level_range.map(_ - lvh)
    for (idx <- idx_range) {
      val lv = idx + lvh
      idx match {
        case 0 => ret(lvh) := stage(dat_prestage, lv)
        case _ => ret(lv) := stage(ret(lv - 1), lv)
      }
    }
    ret(level_range.last)
  }
}
