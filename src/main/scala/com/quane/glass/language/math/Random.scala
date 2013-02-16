package com.quane.glass.language.math

import com.quane.glass.language.Expression
import com.quane.glass.language.data.Number
import scala.util.Random

/** A {@link Number}, generated randomly within the specified range, inclusive.
  */
class RandomNumber(low: Number, high: Number)
    extends Number(low.int + Random.nextInt(high.int - low.int + 1))

class RandomNumberGenerator {

    /** Generates a random number within the specified range, inclusive.
      *
      * @param low the lower bound, inclusive
      * @param high the upper bound, inclusive
      */
    def generate(low: Number, high: Number): RandomNumber = new RandomNumber(low, high)

}