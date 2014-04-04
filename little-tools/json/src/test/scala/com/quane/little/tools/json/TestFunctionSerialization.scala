package com.quane.little.tools.json

import com.quane.little.language._
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

  "json serializer" should "serialize function reference" in {
    val name = "function_reference_blank"
    val fun = new FunctionReference(name)
    assertSerialization(getJSON(name), fun)
  }

  it should "serialize function definition" in {
    val name = "function_definition_blank"
    val fun = new FunctionDefinition(name)
    val actual = new JSONSerializer().serialize(fun)
    val expected = getJSON(name)
    assertJSON(expected, actual)
  }

}
