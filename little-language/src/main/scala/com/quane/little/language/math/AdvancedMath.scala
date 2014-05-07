package com.quane.little.language.math

import com.quane.little.language.data.Value
import com.quane.little.language.{Scope, Expression}
import scala.math._

sealed trait AdvancedMath extends Math

class ArcTan(val x: Expression) extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = x.evaluate(scope).asInt
    Value(atan(numberA))
  }

}

class ArcTan2(val l: Expression, val r: Expression) extends AdvancedMath {

  override def evaluate(scope: Scope): Value = {
    val left = l.evaluate(scope).asInt
    val right = r.evaluate(scope).asInt
    Value(atan2(left, right))
  }

}