package com.quane.little.language.math

import com.quane.little.language.data.Value
import com.quane.little.language.{Scope, Expression}
import scala.math._

sealed trait AdvancedMath
  extends Math

class ArcTan(x: Expression)
  extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = x.evaluate(scope).asInt
    Value(atan(numberA))
  }

}

class ArcTan2(a: Expression, b: Expression)
  extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = a.evaluate(scope).asInt
    val numberB = b.evaluate(scope).asInt
    Value(atan2(numberA, numberB))
  }

}