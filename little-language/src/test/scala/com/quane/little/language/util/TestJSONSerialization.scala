package com.quane.little.language.util

import com.google.common.io.Files
import com.quane.little.language._
import com.quane.little.language.data.Value
import java.io.{File, FileNotFoundException}
import java.net.URL
import java.nio.charset.Charset
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.skyscreamer.jsonassert.JSONAssert

/** Test of JSON serialization.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestJSONSerialization
  extends FunSuite {

  /* Scopes */

  test("serialize runtime") {
    val name = "runtime"
    val runtime = new Runtime()
    val actual = JSONSerializer.serialize(runtime)
    val expected = json(name)
    assertJSON(expected, actual)
  }

  /* Blocks */

  test("serialize block") {
    val name = "block"
    val block = new Block(new Runtime())
    assertSerialization(json(name), block)
  }

  test("serialize block with steps") {
    val name = "block_with_values"
    val block = new Block(new Runtime())
    block.addStep(Value("abc"))
    block.addStep(Value(123))
    assertSerialization(json(name), block)
  }

  /* Values */

  test("serialize value blank") {
    val name = "value_blank"
    val v = Value("")
    assertSerialization(json(name), v)
  }

  test("serialize value string") {
    val name = "value_string"
    val v = Value("abc")
    assertSerialization(json(name), v)
  }

  test("serialize value integer") {
    val name = "value_integer"
    val v = Value(123)
    assertSerialization(json(name), v)
  }

  test("serialize value double") {
    val name = "value_double"
    val v = Value(12.34d)
    assertSerialization(json(name), v)
  }

  test("serialize value boolean true") {
    val name = "value_boolean_true"
    val v = Value(true)
    assertSerialization(json(name), v)
  }

  test("serialize value boolean false") {
    val name = "value_boolean_false"
    val v = Value(false)
    assertSerialization(json(name), v)
  }

  /* Functions */

  test("serialize function reference") {
    val name = "function_reference_blank"
    val fun = new FunctionReference(new Runtime(), name)
    assertSerialization(json(name), fun)
  }

  test("serialize function definition") {
    val name = "function_definition_blank"
    val fun = new FunctionDefinition(name)
    val actual = JSONSerializer.serialize(fun)
    val expected = json(name)
    assertJSON(expected, actual)
  }

  /** Serialize the [[Expression]] and assert the JSON looks as expected.
    *
    * @param expected the expected json
    * @param e the expression to serialize
    * @tparam E the type of expression being serialized
    */
  private def assertSerialization[E <: Expression](expected: String, e: E) =
    assertJSON(expected, JSONSerializer.serialize(e))

  /** Assert that the `actual` JSON matches the expected JSON.
    *
    * @param expected the expected json
    * @param actual the actual json
    */
  private def assertJSON(expected: String, actual: String) = {
    println(actual)
    JSONAssert.assertEquals(expected, actual, false)
  }

  /** Get the expected JSON given the name of the serialized object.
    *
    * Assumes there is a corresponding .json file by the same name in the test
    * resources directory.
    *
    * @param name the name of the json file
    * @return the json
    */
  private def json(name: String): String = {
    val path = "json/" + name + ".json"
    getClass.getClassLoader.getResource(path) match {
      case url: URL =>
        Files.toString(new File(url.getFile), Charset.defaultCharset())
      case _ =>
        throw new FileNotFoundException(path)
    }
  }

}
