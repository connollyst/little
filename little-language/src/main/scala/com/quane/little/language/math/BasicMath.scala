package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Value

sealed trait BasicMath
  extends Math

class Addition(a: Expression, b: Expression)
  extends BasicMath {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    Value(numberA + numberB)
  }

}

class Subtraction(a: Expression, b: Expression)
  extends BasicMath {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    Value(numberA - numberB)
  }

}

class Multiplication(a: Expression, b: Expression)
  extends BasicMath {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    Value(numberA * numberB)
  }

}

class Division(a: Expression, b: Expression)
  extends BasicMath {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    Value(numberA / numberB)
  }

}