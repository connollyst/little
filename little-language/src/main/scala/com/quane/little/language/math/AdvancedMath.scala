package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import scala.math._

sealed trait AdvancedMath
  extends Math

class ArcTan(x: Expression)
  extends AdvancedMath {

  def evaluate: Value = {
    val numberA = x.evaluate.asInt
    new Value(atan(numberA))
  }

}

class ArcTan2(a: Expression, b: Expression)
  extends AdvancedMath {

  def evaluate: Value = {
    val numberA = a.evaluate.asInt
    val numberB = b.evaluate.asInt
    new Value(atan2(numberA, numberB))
  }

}