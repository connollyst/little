package com.quane.little.language.util

import com.quane.little.language.Runtime
import com.quane.little.language.util.JSONTestUtilities._
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON serialization.
  *
  * @author Sean Connolly
  */
class TestJSONDeserialization extends FlatSpec with ShouldMatchers {

  /* Scopes */

  "json serializer" should "serialize runtime" in {
    val name = "runtime"
    val json = getJSON(name)
    val runtime = JSONSerializer.deserialize[Runtime](json)
    runtime.getClass should be(classOf[Runtime])
  }

}
