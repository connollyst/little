package com.quane.little.language.math

import com.quane.little.language.data.{Value, Number}
import scala.util.Random

/** A {@link Number}, generated randomly within the specified range, inclusive.
  */
class RandomNumber(low: Value, high: Value)
  extends Number(low.asNumber + Random.nextInt(high.asNumber - low.asNumber + 1))

class RandomNumberGenerator {

  /** Generates a random number within the specified range, inclusive.
    *
    * @param low the lower bound, inclusive
    * @param high the upper bound, inclusive
    */
  def generate(low: Value, high: Value): RandomNumber = new RandomNumber(low, high)

}