package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.SetStatement
import com.quane.little.language.data.{Text, Bool, NumberSimple, NumberDecimal}

/** Test JSON serialization of [[com.quane.little.language.SetStatement]] objects.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestSetterDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON deserializer" should "deserialize a setter with a string value" in {
    val name = "setter_string"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new Text("abc"))
  }
  it should "deserialize a setter with an integer value" in {
    val name = "setter_integer"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new NumberSimple(1234))
  }
  it should "deserialize a setter with an double value" in {
    val name = "setter_double"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new NumberDecimal(12.34))
  }
  it should "deserialize a setter with an boolean value (true)" in {
    val name = "setter_boolean_true"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new Bool(true))
  }
  it should "deserialize a setter with an boolean value (false)" in {
    val name = "setter_boolean_false"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new Bool(false))
  }
  it should "deserialize a setter with a function reference" in {
    val name = "setter_function_reference"
    val setter = deserialize[SetStatement](name)
    setter.name should be("MyVariable")
    setter.value should be(new Bool(false))
  }

}
