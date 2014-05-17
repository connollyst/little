package com.quane.little.tools.json

import com.quane.little.language.data.Value
import com.quane.little.language.{Printer, Expression, FunctionDefinition, FunctionReference}
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionDeserialization
  extends FlatSpec with ShouldMatchers with JSONTestUtilities {

  /* Function References */

  "a function reference" should "deserialize to correct class" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.getClass should be(classOf[FunctionReference])
  }
  it should "deserialize with correct name" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.name should be(name)
  }
  it should "deserialize even with empty args" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.args.isEmpty should be(right = true)
  }
  it should "deserialize with a single arg of type value" in {
    val name = "function_reference_with_single_value_arg"
    val fun = deserialize[FunctionReference](name)
    fun.args.size should be(1)
    assertArgEquals[Value](fun, "x", Value("abc"))
  }
  it should "deserialize with multiple args of type value" in {
    val name = "function_reference_with_multiple_value_args"
    val fun = deserialize[FunctionReference](name)
    fun.args.size should be(3)
    assertArgEquals[Value](fun, "x", Value("abc"))
    assertArgEquals[Value](fun, "y", Value(123))
    assertArgEquals[Value](fun, "z", Value(true))
  }
  it should "deserialize with a function reference arg" in {
    val name = "function_reference_with_function_reference_arg"
    val fun = deserialize[FunctionReference](name)
    fun.args.size should be(1)
    assertArgEquals(fun, "x", new FunctionReference("inner_function"))
  }

  /* Function Definitions */

  "a function definition" should "deserialize to the correct class" in {
    val name = "function_definition_blank"
    val fun = deserialize[FunctionDefinition](name)
    fun.getClass should be(classOf[FunctionDefinition])
  }
  it should "deserialize with a print statement step" in {
    val name = "function_definition_say_hello"
    val fun = deserialize[FunctionDefinition](name)
    fun should be(
      new FunctionDefinition("sayHello")
        .addStep(new Printer(Value("Hello World!"))
        )
    )
  }

  private def assertArgEquals[E <: Expression](fun: FunctionReference, argName: String, argExpected: E): Unit = {
    fun.args.contains(argName) should be(right = true)
    fun.args(argName) should be(argExpected)
  }

}
