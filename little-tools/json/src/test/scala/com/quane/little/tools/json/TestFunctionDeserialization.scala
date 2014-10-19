package com.quane.little.tools.json

import com.quane.little.language.data.{ValueType, Value}
import com.quane.little.language.math.Addition
import com.quane.little.language._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionDeserialization
  extends WordSpec with ShouldMatchers with JSONTestUtilities {

  /* Function References */

  "JSON Deserialized" should {
    "deserialize to correct class" in {
      val name = "function_reference_blank"
      val fun = deserialize[FunctionReference](name)
      fun.getClass should be(classOf[FunctionReference])
    }
    "deserialize with correct name" in {
      val name = "function_reference_blank"
      val fun = deserialize[FunctionReference](name)
      fun.name should be(name)
    }
    "deserialize even with empty args" in {
      val name = "function_reference_blank"
      val fun = deserialize[FunctionReference](name)
      fun.args.isEmpty should be(right = true)
    }
    "deserialize with a single arg of type value" in {
      val name = "function_reference_with_single_value_arg"
      val fun = deserialize[FunctionReference](name)
      fun.args.size should be(1)
      assertArgEquals[Value](fun, "x", Value("abc"))
    }
    "deserialize with multiple args of type value" in {
      val name = "function_reference_with_multiple_value_args"
      val fun = deserialize[FunctionReference](name)
      fun.args.size should be(3)
      assertArgEquals[Value](fun, "x", Value("abc"))
      assertArgEquals[Value](fun, "y", Value(123))
      assertArgEquals[Value](fun, "z", Value(true))
    }
    "deserialize with a function reference arg" in {
      val name = "function_reference_with_function_reference_arg"
      val fun = deserialize[FunctionReference](name)
      fun.args.size should be(1)
      assertArgEquals(fun, "x", new FunctionReference("inner_function"))
    }
  }


  /* Function Definitions */

  "JSON Deserialized" should {
    "deserialize function definitions to the correct class" in {
      val name = "function_definition_blank"
      val fun = deserialize[FunctionDefinition](name)
      fun.getClass should be(classOf[FunctionDefinition])
    }
    "deserialize blank function definition" in {
      val name = "function_definition_blank"
      val fun = deserialize[FunctionDefinition](name)
      fun should be(new FunctionDefinition(name))
    }
    "deserialize 'hello world' function definition" in {
      val name = "function_definition_say_hello"
      val fun = deserialize[FunctionDefinition](name)
      fun should be(
        new FunctionDefinition(name)
          .addStep(new Printer(Value("Hello World!"))
          )
      )
    }
    "deserialize 'increment' function definition" in {
      val name = "function_definition_increment"
      val fun = deserialize[FunctionDefinition](name)
      fun should be(
        new FunctionDefinition(name)
          .addParam("input", ValueType.Number)
          .addStep(new Addition(new Getter("input"), Value(1)))
      )
    }
  }

  private def assertArgEquals[E <: Code](fun: FunctionReference, argName: String, argExpected: E): Unit = {
    fun.args.contains(argName) should be(right = true)
    fun.args(argName) should be(argExpected)
  }

}
