package com.quane.little.language.math

import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestBasicMath extends FunSuite {

  test("test 1 + 1 = 2") {
    val result = new Addition(new Value(1), new Value(1)).evaluate
    assert(result == 2)
  }

  test("test Pi + Pi = 2Pi") {
    val pi = new Value(scala.math.Pi)
    val piPlusPi = new Addition(pi, pi).evaluate
    val twoPi = new Multiplication(new Value(2), pi).evaluate
    assert(piPlusPi == twoPi, piPlusPi.asDouble + " == " + twoPi.asDouble)
  }

}
