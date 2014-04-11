package com.quane.little.tools.json

import com.google.common.io.Files
import java.io.{FileNotFoundException, File}
import java.net.URL
import java.nio.charset.Charset
import org.skyscreamer.jsonassert.JSONAssert

/** Utilities for testing JSON serialization.
  *
  * @author Sean Connolly
  */
trait JSONTestUtilities {

  /** Serialize the [[com.quane.little.language.Expression]] and assert the JSON
    * looks as expected.
    *
    * @param expected the expected json
    * @param e the expression to serialize
    * @tparam E the type of expression being serialized
    */
  private[json] def assertSerialization[E](expected: String, e: E) =
    assertJSON(expected, new LittleJSON().serialize(e))

  /** Assert that the `actual` JSON matches the expected JSON.
    *
    * @param expected the expected json
    * @param actual the actual json
    */
  private[json] def assertJSON(expected: String, actual: String) = {
    println(actual)
    JSONAssert.assertEquals(expected, actual, true)
  }

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
