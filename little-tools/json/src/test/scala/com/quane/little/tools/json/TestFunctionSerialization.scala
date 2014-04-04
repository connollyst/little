package com.quane.little.tools.json

import com.quane.little.language._
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

/** Test JSON serialization of [[FunctionReference]] and [[FunctionDefinition]]
  * expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionSerialization
  extends FunSuite
  with MockitoSugar {

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
