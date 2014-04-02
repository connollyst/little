package com.quane.little.language.math

import com.quane.little.language.Runtime
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestBasicMath extends FunSuite {

  test("test 1 + 1 = 2") {
    val result = new Addition(Value(1), Value(1)).evaluate(new Runtime)
    assert(result == 2)
  }

  test("test Pi + Pi = 2Pi") {
    val pi = Value(scala.math.Pi)
    val piPlusPi = new Addition(pi, pi).evaluate(new Runtime)
    val twoPi = new Multiplication(Value(2), pi).evaluate(new Runtime)
    assert(piPlusPi == twoPi, piPlusPi.asDouble + " == " + twoPi.asDouble)
  }

}
