package com.quane.little.language.data

/**
 *
 *
 * @author Sean Connolly
 */
object ValueType extends Enumeration {
  type ValueType = Value
  val Boolean, Integer, Double, String, Nada = Value
}