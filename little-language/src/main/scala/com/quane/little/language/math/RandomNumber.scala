package com.quane.little.language.math

import com.quane.little.language.data.Value
import scala.util.Random
import com.quane.little.language.Expression

/** A numeric value generated randomly within the specified range, inclusive.
  *
  * @param low the minimum possible value
  * @param high the maximum possible value
  */
class RandomNumber(low: Value, high: Value)
  extends Expression {

  override def evaluate: Value = new Value(low.asInt + Random.nextInt(high.asInt - low.asInt + 1))

}

