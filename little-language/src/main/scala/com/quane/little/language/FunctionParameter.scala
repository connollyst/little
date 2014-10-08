package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType

/** Defines a function parameter to be specified at evaluation time.
  *
  * @param name the name of the parameter
  * @see [[com.quane.little.language.FunctionDefinition]]
  */
class FunctionParameter(val name: String, val valueType: ValueType) {

  def canEqual(other: Any): Boolean = other.isInstanceOf[FunctionParameter]

  override def equals(other: Any): Boolean = other match {
    case that: FunctionParameter =>
      (that canEqual this) &&
        name == that.name &&
        valueType == that.valueType
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, valueType)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("name", name)
      .add("valueType", valueType)
      .toString

}
