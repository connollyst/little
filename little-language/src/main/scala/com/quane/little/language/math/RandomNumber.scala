package com.quane.little.language.math

import com.quane.little.language.data.ValueType.ValueType
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.{EvaluableCode, Scope}

import scala.util.Random

/** A numeric value generated randomly within the specified range, inclusive.
  *
  * @param low the minimum possible value
  * @param high the maximum possible value
  */
class RandomNumber(low: Value, high: Value) extends EvaluableCode {

  override def returnType: ValueType = ValueType.Integer

  override def evaluate(scope: Scope): Value =
    Value(low.asInt + Random.nextInt(high.asInt - low.asInt + 1))

}

