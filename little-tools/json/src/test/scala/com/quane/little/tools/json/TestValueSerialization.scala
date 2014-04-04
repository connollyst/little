package com.quane.little.tools.json

import com.quane.little.language.data.Value
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test JSON serialization of [[Value]] expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestValueSerialization
  extends FunSuite
  with MockitoSugar {

  test("serialize value blank") {
    val name = "value_blank"
    val v = Value("")
    assertSerialization(getJSON(name), v)
  }

  test("serialize value string") {
    val name = "value_string"
    val v = Value("abc")
    assertSerialization(getJSON(name), v)
  }

  test("serialize value integer") {
    val name = "value_integer"
    val v = Value(123)
    assertSerialization(getJSON(name), v)
  }

  test("serialize value double") {
    val name = "value_double"
    val v = Value(12.34d)
    assertSerialization(getJSON(name), v)
  }

  test("serialize value boolean true") {
    val name = "value_boolean_true"
    val v = Value(true)
    assertSerialization(getJSON(name), v)
  }

  test("serialize value boolean false") {
    val name = "value_boolean_false"
    val v = Value(false)
    assertSerialization(getJSON(name), v)
  }

}
