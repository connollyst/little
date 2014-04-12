package com.quane.little.tools.json

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test JSON serialization of [[Value]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestValueSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON serializer" should "serialize value blank" in {
    val name = "value_blank"
    val v = Value("")
    assertSerialization(getJSON(name), v)
  }
  it should "serialize value string" in {
    val name = "value_string"
    val v = Value("abc")
    assertSerialization(getJSON(name), v)
  }
  it should "serialize value integer" in {
    val name = "value_integer"
    val v = Value(1234)
    assertSerialization(getJSON(name), v)
  }
  it should "serialize value double" in {
    val name = "value_double"
    val v = Value(12.34d)
    assertSerialization(getJSON(name), v)
  }
  it should "serialize value boolean true" in {
    val name = "value_boolean_true"
    val v = Value(true)
    assertSerialization(getJSON(name), v)
  }
  it should "serialize value boolean false" in {
    val name = "value_boolean_false"
    val v = Value(false)
    assertSerialization(getJSON(name), v)
  }

}
