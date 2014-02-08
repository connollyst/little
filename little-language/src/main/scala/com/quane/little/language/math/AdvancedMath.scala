package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import org.eintr.loglady.Logging
import scala.math._

sealed trait AdvancedMath
  extends Math

class ArcTan(x: Expression)
  extends AdvancedMath
  with Logging {

  def evaluate: Value = {
    val numberA = x.evaluate.asInt
    log.info("atan(" + numberA + ")")
    new Value(atan(numberA))
  }

}

class ArcTan2(a: Expression, b: Expression)
  extends AdvancedMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asInt
    val numberB = b.evaluate.asInt
    log.info("atan2(" + numberA + ", " + numberB + ")")
    new Value(atan2(numberA, numberB))
  }

}