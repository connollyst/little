package com.quane.little.language

import com.google.common.base.Objects
import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Nada, Value, ValueType}
import com.quane.little.tools.Logging

/** A statement which prints out text when evaluated.
  *
  * @author Sean Connolly
  */
class Printer(val value: EvaluableCode) extends EvaluableCode with Logging {

  /** Returns this statement's [[ValueType]]
    *
    * @return the return value type
    */
  override def returnType: ValueType = ValueType.Nothing

  /** Evaluate the print statement.
    *
    * @param scope the scope in which to evaluate
    */
  override def evaluate(scope: Scope): Value = {
    val text = value.evaluate(scope)
    // TODO this should display a speech bubble over the guy
    error(text.asText)
    new Nada
  }

  override def equals(that: Any) = that match {
    case p: Printer => value.equals(p.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("value", value)
      .toString

}
