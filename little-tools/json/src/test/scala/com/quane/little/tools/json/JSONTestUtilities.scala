package com.quane.little.tools.json

import java.io.{File, FileNotFoundException}
import java.net.URL
import java.nio.charset.Charset

import com.google.common.io.Files
import org.skyscreamer.jsonassert.JSONAssert

/** Utilities for testing JSON serialization.
  *
  * @author Sean Connolly
  */
trait JSONTestUtilities {

  private[json] def deserialize[T: Manifest](jsonName: String): T =
    new LittleJSON().deserialize[T](getJSON(jsonName))

  /** Serialize the [[com.quane.little.language.Code]] and assert the JSON
    * looks as expected.
    *
    * @param expected the expected json
    * @param c the code to serialize
    * @tparam C the type of code being serialized
    */
  private[json] def assertSerialization[C](expected: String, c: C) =
    assertJSON(expected, new LittleJSON().serialize(c))

  /** Assert that the `actual` JSON matches the expected JSON.
    *
    * @param expected the expected json
    * @param actual the actual json
    */
  private[json] def assertJSON(expected: String, actual: String) =
    JSONAssert.assertEquals(expected, actual, true)

  /** Get the expected JSON given the name of the serialized object.
    *
    * Assumes there is a corresponding .json file by the same name in the test
    * resources directory.
    *
    * @param name the name of the json file
    * @return the json
    */
  private[json] def getJSON(name: String): String = {
    val path = "json/" + name + ".json"
    getClass.getClassLoader.getResource(path) match {
      case url: URL =>
        Files.toString(new File(url.getFile), Charset.defaultCharset())
      case _ =>
        throw new FileNotFoundException(path)
    }
  }

}
