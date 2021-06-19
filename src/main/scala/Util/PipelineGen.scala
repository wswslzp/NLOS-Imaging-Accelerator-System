package Util

import spinal.core._
import spinal.lib._
import fiber._

import scala.collection.mutable

//trait PipelineGen extends Generator {
//  private var pipeline_switches = mutable.Map[Int, Handle[Boolean]]()
//
//  private def setPipelineLevel(level: Int): Unit = {
//    if (!pipeline_switches.keys.exists(_ == level)) {
//      val sw = Handle[Boolean]
//      dependencies += sw
//      pipeline_switches += level -> sw
//    }
//  }
//
//  private def setLevel(level: Int, stat: Boolean): Unit = {
//    val sw = pipeline_switches.getOrElse(
//      level, {
//        val tmp = Handle[Boolean]
//        SpinalError(s"No level ${level}")
//        tmp
//      }
//    )
//    sw.load(stat)
//  }
//
//  def openLevel(level: Int): Unit = setLevel(level, true)
//  def closeLevel(level: Int): Unit = setLevel(level, false)
//
//  private def setAllLevel(stat: Boolean): Unit = {
//    pipeline_switches.keys.foreach {
//      pipeline_switches(_).load(stat)
//    }
//  }
//
//  def openAllLevel(): Unit = setAllLevel(true)
//  def closeAllLevel(): Unit = setAllLevel(false)
//
//  def stage[T <: Data](data_prestage: T, level: Int): T = {
//    setPipelineLevel(level)
//    val data_staged = cloneOf(data_prestage)
//    add task {
//      if(pipeline_switches(level).get) {
//        data_staged := RegNext(data_prestage)
//      } else {
//        data_staged := data_prestage
//      }
//    }
//    data_staged
//  }
//
//  def stage[T <: Data](dat_prestage: T, level_range: Range): T = {
//    val ret = level_range.map(_ -> cloneOf(dat_prestage)).toMap
//    val lvh = level_range.head
//    val idx_range = level_range.map(_ - lvh)
//    for (idx <- idx_range) {
//      val lv = idx + lvh
//      idx match {
//        case 0 => ret(lvh) := stage(dat_prestage, lv)
//        case _ => ret(lv) := stage(ret(lv - 1), lv)
//      }
//    }
//    ret(level_range.last)
//  }
//
//  def buildPipeline(): Unit = GeneratorCompiler(this)
//
//}
//
//object PipelineGenApp extends App {
//  case class Test() extends PipelineGen {
//    add task {
//      val ia = in UInt(8 bit)
//      val ob = out UInt(8 bit)
//      ob := stage(ia, 0)
//    }
//
////    ob := stage(ia, 0)
//
//    openAllLevel()
////    buildPipeline()
//  }
//
//  SpinalVerilog(Test().toComponent())
//}
