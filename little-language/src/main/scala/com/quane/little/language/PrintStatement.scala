package com.quane.little.language

import com.quane.little.tools.Logging
import com.google.common.base.Objects

/**
 *
 *
 * @author Sean Connolly
 */
class PrintStatement(val value: Expression)
  extends Statement
  with Logging {

  override def evaluate(scope: Scope): Unit = {
    val text = value.evaluate(scope)
    // TODO this should display a speech bubble over the guy
    error(text.asText)
  }

  override def equals(that: Any) = that match {
    case p: PrintStatement => value.equals(p.value)
    case _ => false
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("value", value)
      .toString

}
