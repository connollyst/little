package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Value
import org.eintr.loglady.Logging

sealed trait BasicMath
  extends Math

class Addition(a: Expression, b: Expression)
  extends BasicMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    log.info("Adding " + numberB + " to " + numberA)
    new Value(numberA + numberB)
  }

}

class Subtraction(a: Expression, b: Expression)
  extends BasicMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    log.info("Subtracting " + numberB + " from " + numberA)
    new Value(numberA - numberB)
  }

}

class Multiplication(a: Expression, b: Expression)
  extends BasicMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    log.info("Multiplying " + numberA + " by " + numberB)
    new Value(numberA * numberB)
  }

}

class Division(a: Expression, b: Expression)
  extends BasicMath
  with Logging {

  def evaluate: Value = {
    val numberA = a.evaluate.asDouble
    val numberB = b.evaluate.asDouble
    log.info("Dividing " + numberA + " by " + numberB)
    new Value(numberA / numberB)
  }

}