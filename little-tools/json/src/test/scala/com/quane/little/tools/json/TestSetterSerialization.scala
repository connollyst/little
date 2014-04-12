package com.quane.little.tools.json

import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.SetStatement
import com.quane.little.language.data.Value

/** Test JSON serialization of [[com.quane.little.language.SetStatement]] objects.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestSetterSerialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  "JSON serializer" should "serialize a set statement with a string value" in {
    val name = "setter_string"
    val setter = new SetStatement("MyVariable", "abc")
    val actual = new LittleJSON().serialize(setter)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }
  it should "serialize a set statement with an integer value" in {
    val name = "setter_integer"
    val setter = new SetStatement("MyVariable", 1234)
    val actual = new LittleJSON().serialize(setter)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }
  it should "serialize a set statement with a double value" in {
    val name = "setter_double"
    val setter = new SetStatement("MyVariable", 12.34)
    val actual = new LittleJSON().serialize(setter)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }
  it should "serialize a set statement with an boolean value (true)" in {
    val name = "setter_boolean_true"
    val setter = new SetStatement("MyVariable", true)
    val actual = new LittleJSON().serialize(setter)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }
  it should "serialize a set statement with an boolean value (false)" in {
    val name = "setter_boolean_false"
    val setter = new SetStatement("MyVariable", false)
    val actual = new LittleJSON().serialize(setter)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

}
