package Util

import spinal.core._
import spinal.lib._

// TODO: do a universal function's LUT with linear interpolation ability.

// I => T, V => R

abstract class LutWithLP

//case class LutWithLP[I, V, T <: Data with Num[T], R <: Data with Num[R]]
//(
//  indx_table: Vector[I], // 1d lut
//  func_table: Vector[V],
//  indx_type:  HardType[T],
//  func_type:  HardType[R]
//) extends Component
//{
//  // To use the Num trait, HComplex should implement all the operator defined in it.
//
//}

