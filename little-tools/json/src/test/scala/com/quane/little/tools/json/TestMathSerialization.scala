package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.data.Value
import com.quane.little.language.math._

/** Test JSON serialization of [[com.quane.little.language.math.Math]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMathSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON serializer" should "serialize addition" in {
    val name = "math_basic_addition"
    val v = new Addition(Value(1234), Value(56.78))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize subtraction" in {
    val name = "math_basic_subtraction"
    val v = new Subtraction(Value(1234), Value(56.78))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize multiplication" in {
    val name = "math_basic_multiplication"
    val v = new Multiplication(Value(1234), Value(56.78))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize division" in {
    val name = "math_basic_division"
    val v = new Division(Value(1234), Value(56.78))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize arctan" in {
    val name = "math_advanced_arctan"
    val v = new ArcTan(Value(1234))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize arctan2" in {
    val name = "math_advanced_arctan2"
    val v = new ArcTan2(Value(1234), Value(5678))
    assertSerialization(getJSON(name), v)
  }

}
