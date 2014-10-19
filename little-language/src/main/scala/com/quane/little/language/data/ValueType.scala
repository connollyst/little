package com.quane.little.language.data

/** Defines the primitive type of the [[com.quane.little.language.data.Value]]
  * for better internal manipulation.
  *
  * @author Sean Connolly
  */
object ValueType extends Enumeration {
  type ValueType = Value
  val Anything, Something, TrueFalse, Number, Text, Nothing = Value
}