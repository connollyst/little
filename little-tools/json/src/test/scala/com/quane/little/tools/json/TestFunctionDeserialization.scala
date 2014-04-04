package com.quane.little.tools.json

import com.quane.little.language.FunctionReference
import com.quane.little.language.data.Value
import com.quane.little.tools.json.JSONTestUtilities._
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON deserialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionDeserialization extends FlatSpec with ShouldMatchers {

  "json deserializer" should "deserialize blank function reference" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.getClass should be(classOf[FunctionReference])
  }
  it should "deserialize blank function reference name" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.name should be(name)
  }
  it should "deserialize blank function reference args" in {
    val name = "function_reference_blank"
    val fun = deserialize[FunctionReference](name)
    fun.args.isEmpty should be(true)
  }
  it should "deserialize function reference args of single value" in {
    val name = "function_reference_value_single"
    val fun = deserialize[FunctionReference](name)
    fun.args.size should be(1)
    assertArgEquals[Value](fun, "x", Value("abc"))
  }

  private def assertArgEquals[E](fun: FunctionReference, argName: String, argExpected: E): Unit = {
    fun.args.contains(argName) should be(true)
    fun.args(argName) should be(argExpected)
  }

  private def deserialize[T: Manifest](jsonName: String): T =
    new JSONDeserializer().deserialize[T](getJSON(jsonName))

}
