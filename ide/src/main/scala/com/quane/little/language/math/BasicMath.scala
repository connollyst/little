package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import org.eintr.loglady.Logging

sealed trait BasicMath
  extends Expression[Value]

class Addition(a: Expression[Value], b: Expression[Value])
  extends BasicMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asNumber
    val numberB = b.evaluate.asNumber
    log.info("Adding " + numberA + " to " + numberB)
    new Value(numberA + numberB)
  }

}