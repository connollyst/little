package com.quane.little.tools.json

import com.quane.little.language._
import com.quane.little.language.data.{ValueType, Value}
import com.quane.little.language.math.Addition
import org.junit.runner.RunWith
import org.scalatest.{WordSpec, FlatSpec}
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test JSON serialization of [[FunctionReference]] and [[FunctionDefinition]]
  * expressions.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionSerialization
  extends WordSpec with ShouldMatchers with JSONTestUtilities {

  /* Function References */

  "JSON serializer" should {
    "serialize blank function reference" in {
      val name = "function_reference_blank"
      val fun = new FunctionReference(name)
      assertSerialization(getJSON(name), fun)
    }
    "serialize function reference with single value argument" in {
      val name = "function_reference_with_single_value_arg"
      val fun = new FunctionReference(name)
      fun.addArg("x", Value("abc"))
      assertSerialization(getJSON(name), fun)
    }
    "serialize function reference with multiple value arguments" in {
      val name = "function_reference_with_multiple_value_args"
      val fun = new FunctionReference(name)
      fun.addArg("x", Value("abc"))
      fun.addArg("y", Value(123))
      fun.addArg("z", Value(true))
      assertSerialization(getJSON(name), fun)
    }
    "serialize function reference with function reference argument" in {
      val name = "function_reference_with_function_reference_arg"
      val fun = new FunctionReference(name)
      fun.addArg("x", new FunctionReference("inner_function"))
      assertSerialization(getJSON(name), fun)
    }
  }
  /* Function Definitions */

  "JSON serializer" should {
    "serialize blank function definition" in {
      val name = "function_definition_blank"
      val fun = new FunctionDefinition(name)
      val actual = new LittleJSON().serialize(fun)
      val expected = getJSON(name)
      assertJSON(expected, actual)
    }
    "serialize 'hello world' function definition" in {
      val name = "function_definition_say_hello"
      val fun = new FunctionDefinition(name)
        .addStep(new Printer(Value("Hello World!")))
      val actual = new LittleJSON().serialize(fun)
      val expected = getJSON(name)
      assertJSON(expected, actual)
    }
    "serialize 'increment' function definition" in {
      val name = "function_definition_increment"
      val fun = new FunctionDefinition(name)
        .addParam("input", ValueType.Number)
        .addStep(new Addition(new Getter("input"), Value(1)))
      val actual = new LittleJSON().serialize(fun)
      val expected = getJSON(name)
      assertJSON(expected, actual)
    }
  }

}
