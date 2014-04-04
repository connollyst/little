package com.quane.little.tools.json

import com.quane.little.language.{Block, Runtime}
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
class TestBlockDeserialization extends FlatSpec with ShouldMatchers {

  "json deserializer" should "deserialize runtime" in {
    val runtime = deserialize[Runtime]("runtime")
    runtime.getClass should be(classOf[Runtime])
  }

  it should "deserialize runtime's runtime as itself" in {
    val runtime = deserialize[Runtime]("runtime")
    runtime.runtime should be(runtime)
  }

  it should "deserialize block" in {
    val name = "block"
    val block = deserialize[Block](name)
    block.getClass should be(classOf[Block])
  }

  it should "deserialize block with values" in {
    val name = "block_with_values"
    val block = deserialize[Block](name)
    block.getClass should be(classOf[Block])
    block.length should be(2)
  }

  private def deserialize[T: Manifest](jsonName: String): T =
    new JSONDeserializer().deserialize[T](getJSON(jsonName))

}
