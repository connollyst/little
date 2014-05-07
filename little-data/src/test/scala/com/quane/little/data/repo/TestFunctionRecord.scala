package com.quane.little.data.repo

import com.quane.little.data.model.{CodeCategory, FunctionRecord, RecordId}
import com.quane.little.language.FunctionDefinition
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert
import java.net.URL
import com.google.common.io.Files
import java.io.{FileNotFoundException, File}
import java.nio.charset.Charset

/** Test cases for the [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionRecord extends FlatSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val functionId = new RecordId("FunctionDefinitionRecordID")
  val userId = new RecordId("UserRecordID")
  val category = CodeCategory.Misc
  val definition = new FunctionDefinition("MyFunction")
  val function = new FunctionRecord(userId, category, definition)
  function.id = functionId

  "FunctionDefinitionRecord" should "serialize to JSON" in {
    val expected = getJSON("function_blank")
    val actual = littleJSON.serialize(function)
    JSONAssert.assertEquals(expected, actual, true)
  }
  it should "deserialize record id from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
    actual.id should be(functionId)
  }
  it should "deserialize owner record id from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
    actual.ownerId should be(userId)
  }
  it should "deserialize code category from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
    actual.category should be(category)
  }
  it should "deserialize function definition from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
    actual.definition should be(definition)
  }
  it should "deserialize from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
    actual should be(function)
  }

  private[data] def getJSON(name: String): String = {
    val path = "json/" + name + ".json"
    getClass.getClassLoader.getResource(path) match {
      case url: URL =>
        Files.toString(new File(url.getFile), Charset.defaultCharset())
      case _ =>
        throw new FileNotFoundException(path)
    }
  }

}
