package com.quane.little.language.util

import com.google.common.io.Files
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionDefinition, FunctionReference, Block, Runtime}
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

  test("serialize runtime") {
    val name = "runtime"
    val runtime = new Runtime()
    val json = JSONSerializer.serialize(runtime)
    assertJSON(name, json)
  }

  test("serialize block") {
    val name = "block"
    val block = new Block(new Runtime())
    val json = JSONSerializer.serialize(block)
    assertJSON(name, json)
  }

  test("serialize block with steps") {
    val name = "block_with_values"
    val block = new Block(new Runtime())
    block.addStep(new Value("abc"))
    block.addStep(new Value(123))
    val json = JSONSerializer.serialize(block)
    println(json)
    assertJSON(name, json)
  }

  test("serialize function reference") {
    val name = "function_reference_blank"
    val fun = new FunctionReference(new Runtime(), name)
    val json = JSONSerializer.serialize(fun)
    assertJSON(name, json)
  }

  test("serialize function definition") {
    val name = "function_definition_blank"
    val fun = new FunctionDefinition(name)
    val json = JSONSerializer.serialize(fun)
    assertJSON(name, json)
  }

  /** Assert that the `actual` JSON matches the expected JSON given by the name
    * of the serialized object.
    *
    * @param name the name of the json file
    * @param actual the actual json
    */
  private def assertJSON(name: String, actual: String) =
    JSONAssert.assertEquals(expectedJSON(name), actual, false)

  /** Get the expected JSON given the name of the serialized object.
    *
    * Assumes there is a corresponding .json file by the same name in the test
    * resources directory.
    *
    * @param name the name of the json file
    * @return the json
    */
  private def expectedJSON(name: String): String =
    getClass.getClassLoader.getResource("json/" + name + ".json") match {
      case url: URL =>
        Files.toString(new File(url.getFile), Charset.defaultCharset())
      case _ =>
        throw new FileNotFoundException(name)
    }

}
