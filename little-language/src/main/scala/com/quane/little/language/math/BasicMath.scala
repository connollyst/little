package com.quane.little.language.math

import com.quane.little.language.data.Value
import com.quane.little.language.{Scope, Expression}

sealed trait BasicMath
  extends Math

class Addition(a: Expression, b: Expression)
  extends BasicMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = a.evaluate(scope).asDouble
    val numberB = b.evaluate(scope).asDouble
    Value(numberA + numberB)
  }

}

class Subtraction(a: Expression, b: Expression)
  extends BasicMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = a.evaluate(scope).asDouble
    val numberB = b.evaluate(scope).asDouble
    Value(numberA - numberB)
  }

}

class Multiplication(a: Expression, b: Expression)
  extends BasicMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = a.evaluate(scope).asDouble
    val numberB = b.evaluate(scope).asDouble
    Value(numberA * numberB)
  }

}

class Division(a: Expression, b: Expression)
  extends BasicMath {

  override def evaluate(scope: Scope): Value = {
    val numberA = a.evaluate(scope).asDouble
    val numberB = b.evaluate(scope).asDouble
    Value(numberA / numberB)
  }

}