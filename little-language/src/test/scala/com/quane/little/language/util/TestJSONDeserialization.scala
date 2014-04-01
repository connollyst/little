package com.quane.little.language.util

import com.quane.little.language.data._
import com.quane.little.language.util.JSONTestUtilities._
import com.quane.little.language.{Block, Runtime}
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON serialization.
  *
  * @author Sean Connolly
  */
class TestJSONDeserialization extends FlatSpec with ShouldMatchers {

  /* Scopes */

  "json serializer" should "deserialize runtime" in {
    val name = "runtime"
    val json = getJSON(name)
    val runtime = JSONSerializer.deserialize[Runtime](json)
    runtime.getClass should be(classOf[Runtime])
  }

  it should "deserialize block" in {
    val name = "block"
    val json = getJSON(name)
    val block = JSONSerializer.deserialize[Block](json)
    block.getClass should be(classOf[Block])
  }

  /* Values */
  
  it should "deserialize value blank" in {
    val name = "value_blank"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[Text])
    value.valueType should be(ValueType.String)
    value.primitive should be("")
  }

  it should "deserialize value string" in {
    val name = "value_string"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[Text])
    value.valueType should be(ValueType.String)
    value.primitive should be("abc")
  }

  it should "deserialize value integer" in {
    val name = "value_integer"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[Number])
    value.valueType should be(ValueType.Integer)
    value.primitive should be(123)
  }

  it should "deserialize value double" in {
    val name = "value_double"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[NumberDecimal])
    value.valueType should be(ValueType.Double)
    value.primitive should be(12.34)
  }

  it should "deserialize value boolean true" in {
    val name = "value_boolean_true"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[Bool])
    value.valueType should be(ValueType.Boolean)
    value.primitive should be(Boolean.box(true))
  }

  it should "deserialize value boolean false" in {
    val name = "value_boolean_false"
    val json = getJSON(name)
    val value = JSONSerializer.deserialize[Value](json)
    value.getClass should be(classOf[Bool])
    value.valueType should be(ValueType.Boolean)
    value.primitive should be(Boolean.box(false))
  }

}
