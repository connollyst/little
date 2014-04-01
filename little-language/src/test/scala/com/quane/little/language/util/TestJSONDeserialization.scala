package com.quane.little.language.util

import com.quane.little.language.util.JSONTestUtilities._
import com.quane.little.language.{Block, Runtime}
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

/** Test of JSON serialization.
  *
  * @author Sean Connolly
  */
class TestJSONDeserialization extends FlatSpec with ShouldMatchers {

  /* Scopes */

  "json serializer" should "deserialize runtime" in {
    val name = "runtime"
    val json = getJSON(name)
    val runtime = JSONSerializer.deserialize[Runtime](json)
    runtime.getClass should be(classOf[Runtime])
  }

  it should "deserialize block" in {
    val name = "block"
    val json = getJSON(name)
    val block = JSONSerializer.deserialize[Block](json)
    block.getClass should be(classOf[Block])
  }

}
