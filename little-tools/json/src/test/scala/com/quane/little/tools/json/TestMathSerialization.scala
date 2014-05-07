package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.data.Value
import com.quane.little.language.math.{Division, Multiplication, Subtraction, Addition}

/** Test JSON serialization of [[com.quane.little.language.math.Math]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMathSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON serializer" should "serialize addition" in {
    val name = "math_addition"
    val v = new Addition(Value(1234), Value(45.67))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize subtraction" in {
    val name = "math_subtraction"
    val v = new Subtraction(Value(1234), Value(45.67))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize multiplication" in {
    val name = "math_multiplication"
    val v = new Multiplication(Value(1234), Value(45.67))
    assertSerialization(getJSON(name), v)
  }
  it should "serialize division" in {
    val name = "math_division"
    val v = new Division(Value(1234), Value(45.67))
    assertSerialization(getJSON(name), v)
  }


}
