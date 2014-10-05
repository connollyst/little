package com.quane.little.language.data

/** Defines the primitive type of the [[com.quane.little.language.data.Value]]
  * for better internal manipulation.
  *
  * @author Sean Connolly
  */
object ValueType extends Enumeration {
  type ValueType = Value
  val Any, Boolean, Integer, Double, String, Nada = Value
}