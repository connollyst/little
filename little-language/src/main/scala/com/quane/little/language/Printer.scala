package com.quane.little.language

import com.quane.little.tools.Logging
import com.google.common.base.Objects

/** A [[com.quane.little.language.Statement]] which prints out text when evaluated.
  *
  * @author Sean Connolly
  */
class Printer(val value: Expression)
  extends Statement
  with Logging {

  /** Evaluate the print statement.
    *
    * @param scope the scope in which to evaluate
    */
  override def evaluate(scope: Scope): Unit = {
    val text = value.evaluate(scope)
    // TODO this should display a speech bubble over the guy
    error(text.asText)
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
