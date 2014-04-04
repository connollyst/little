package com.quane.little.tools.json

import com.quane.little.language.data._
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestValueDeserialization extends FlatSpec with ShouldMatchers {

  "json deserializer" should "deserialize value blank" in {
    val value = deserialize[Value]("value_blank")
    value.getClass should be(classOf[Text])
    value.valueType should be(ValueType.String)
    value.primitive should be("")
  }

  it should "deserialize value string" in {
    val value = deserialize[Value]("value_string")
    value.getClass should be(classOf[Text])
    value.valueType should be(ValueType.String)
    value.primitive should be("abc")
  }

  it should "deserialize value integer" in {
    val value = deserialize[Value]("value_integer")
    value.getClass should be(classOf[Number])
    value.valueType should be(ValueType.Integer)
    value.primitive should be(123)
  }

  it should "deserialize value double" in {
    val value = deserialize[Value]("value_double")
    value.getClass should be(classOf[NumberDecimal])
    value.valueType should be(ValueType.Double)
    value.primitive should be(12.34)
  }

  it should "deserialize value boolean true" in {
    val value = deserialize[Value]("value_boolean_true")
    value.getClass should be(classOf[Bool])
    value.valueType should be(ValueType.Boolean)
    value.primitive should be(Boolean.box(true))
  }

  it should "deserialize value boolean false" in {
    val value = deserialize[Value]("value_boolean_false")
    value.getClass should be(classOf[Bool])
    value.valueType should be(ValueType.Boolean)
    value.primitive should be(Boolean.box(false))
  }

  private def deserialize[T: Manifest](jsonName: String): T =
    new JSONDeserializer().deserialize[T](getJSON(jsonName))

}
