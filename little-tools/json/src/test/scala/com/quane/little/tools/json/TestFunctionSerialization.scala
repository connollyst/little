package com.quane.little.tools.json

import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test JSON serialization of [[FunctionReference]] and [[FunctionDefinition]]
  * expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionSerialization extends FlatSpec with ShouldMatchers {

  /* Function References */

  "json serializer" should "serialize blank function reference" in {
    val name = "function_reference_blank"
    val fun = new FunctionReference(name)
    assertSerialization(getJSON(name), fun)
  }

  it should "serialize function reference with single value argument" in {
    val name = "function_reference_value_single"
    val fun = new FunctionReference(name)
    fun.addArg("x", Value("abc"))
    assertSerialization(getJSON(name), fun)
  }

  it should "serialize function reference with multiple value arguments" in {
    val name = "function_reference_value_multiple"
    val fun = new FunctionReference(name)
    fun.addArg("x", Value("abc"))
    fun.addArg("y", Value(123))
    fun.addArg("z", Value(true))
    assertSerialization(getJSON(name), fun)
  }

  it should "serialize function reference with function reference argument" in {
    val name = "function_reference_with_function_reference"
    val fun = new FunctionReference(name)
    fun.addArg("x", new FunctionReference("inner_function"))
    assertSerialization(getJSON(name), fun)
  }

  /* Function Definitions */

  it should "serialize blank function definition" in {
    val name = "function_definition_blank"
    val fun = new FunctionDefinition(name)
    val actual = new JSONSerializer().serialize(fun)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

}
