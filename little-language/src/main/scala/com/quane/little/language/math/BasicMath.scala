package com.quane.little.language.math

import com.quane.little.language.{Scope, Expression}
import com.quane.little.language.data.Value

abstract class BasicMath(val l: Expression, val r: Expression) extends Math {

  override def evaluate(scope: Scope): Value = {
    val left = l.evaluate(scope)
    val right = r.evaluate(scope)
    evaluate(left, right)
  }

  def evaluate(left: Value, right: Value): Value

}

class Addition(l: Expression, r: Expression) extends BasicMath(l, r) {

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble + right.asDouble)

}

class Subtraction(l: Expression, r: Expression) extends BasicMath(l, r) {

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble - right.asDouble)

}

class Multiplication(l: Expression, r: Expression) extends BasicMath(l, r) {

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble * right.asDouble)

}

class Division(l: Expression, r: Expression) extends BasicMath(l, r) {

  override def evaluate(left: Value, right: Value): Value =
    Value(left.asDouble / right.asDouble)

}