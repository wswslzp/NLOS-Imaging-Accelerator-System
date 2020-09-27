package Util.MemManager

import spinal.core._
import spinal.lib._
import Core._
import Util._
import Config._
import Util.Vendor.MemBlackBox

trait Vendor {
  def build(mw: Ram1rw): MemBlackBox
  def build(mw: Ram1r1w): MemBlackBox
  def build(mw: Ram2rw): MemBlackBox
  def build(mw: Rom): MemBlackBox
}

case object Huali extends Vendor {
  override def build(mw: Ram1rw): MemBlackBox = new Util.Vendor.Huali.mbb1rw(mw).Build()
  override def build(mw: Ram1r1w): MemBlackBox = new Util.Vendor.Huali.mbb1r1w(mw).Build()
  override def build(mw: Ram2rw): MemBlackBox = new Util.Vendor.Huali.mbb2rw(mw).Build()
  override def build(mw: Rom): MemBlackBox = new Util.Vendor.Huali.mbbrom(mw).Build()
}