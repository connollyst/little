package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.data.Value
import com.quane.little.language.math._

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMathDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "json deserializer" should "deserialize addition" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_basic_addition")
    value should be(new Addition(Value(1234), Value(56.78)))
  }
  it should "deserialize subtraction" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_basic_subtraction")
    value should be(new Subtraction(Value(1234), Value(56.78)))
  }
  it should "deserialize multiplication" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_basic_multiplication")
    value should be(new Multiplication(Value(1234), Value(56.78)))
  }
  it should "deserialize division" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_basic_division")
    value should be(new Division(Value(1234), Value(56.78)))
  }
  it should "deserialize arctan" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_advanced_arctan")
    value should be(new ArcTan(Value(1234)))
  }
  it should "deserialize arctan2" in {
    val value = deserialize[com.quane.little.language.math.Math]("math_advanced_arctan2")
    value should be(new ArcTan2(Value(1234), Value(5678)))
  }
}
