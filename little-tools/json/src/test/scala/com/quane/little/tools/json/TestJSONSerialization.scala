package com.quane.little.tools.json

import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test of JSON serialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestJSONSerialization
  extends FunSuite
  with MockitoSugar {

  /* Scopes */

  test("serialize runtime") {
    val name = "runtime"
    val runtime = new Runtime()
    val actual = new JSONSerializer().serialize(runtime)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

  /* Blocks */

  test("serialize block") {
    val name = "block"
    val block = new Block()
    assertSerialization(getJSON(name), block)
  }

  test("serialize block with values") {
    val name = "block_with_values"
    val block = new Block
    block.addStep(Value("abc"))
    block.addStep(Value(123))
    assertSerialization(getJSON(name), block)
  }

  /* Values */

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

  /* Functions */

  test("serialize function reference") {
    val name = "function_reference_blank"
    val fun = new FunctionReference(name)
    assertSerialization(getJSON(name), fun)
  }

  test("serialize function definition") {
    val name = "function_definition_blank"
    val fun = new FunctionDefinition(name)
    val actual = new JSONSerializer().serialize(fun)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

}
