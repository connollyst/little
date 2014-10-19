package com.quane.little.language.math

import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{NumberValue, ValueType}
import com.quane.little.language.{Code, Scope}

import scala.util.Random

/** A numeric value generated randomly within the specified range, inclusive.
  *
  * @param low the minimum possible value
  * @param high the maximum possible value
  */
class RandomNumber(low: NumberValue, high: NumberValue) extends Code {

  override def returnType: ValueType = ValueType.Number

  override def evaluate(scope: Scope): NumberValue = low + Random.nextInt((high - low + 1).asInt)

}

