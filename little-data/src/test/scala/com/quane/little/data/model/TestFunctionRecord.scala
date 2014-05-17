package com.quane.little.data.model

import com.quane.little.language.FunctionDefinition
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert
import java.io.{FileNotFoundException, File}
import com.quane.little.language.util.Functions
import java.nio.charset.Charset
import java.net.URL
import com.google.common.io.Files

/** Test cases for the [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionRecord extends WordSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val userId = new RecordId("UserRecordID")
  val category = CodeSubcategory.Misc
  val definitionBlank = new FunctionDefinition("BlankFunction")
  val functionBlankId = new RecordId("BlankFunctionDefinitionRecordID")
  val functionBlank = new FunctionRecord(userId, category, definitionBlank)
  functionBlank.id = functionBlankId
  val definitionComplex = Functions.turnRelative
  val functionComplexId = new RecordId("ComplexFunctionDefinitionRecordID")
  val functionComplex = new FunctionRecord(userId, category, definitionComplex)
  functionComplex.id = functionComplexId

  "FunctionDefinitionRecord" should {
    "serialize blank function to JSON" in {
      val expected = getJSON("function_blank")
      val actual = littleJSON.serialize(functionBlank)
      JSONAssert.assertEquals(expected, actual, true)
    }
    "deserialize record id from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual.id should be(functionBlankId)
    }
    "deserialize owner record id from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual.ownerId should be(userId)
    }
    "deserialize code category from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual.category should be(category)
    }
    "deserialize function definition from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual.definition should be(definitionBlank)
    }
    "deserialize from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual should be(functionBlank)
    }
    "serialize complex function to JSON" in {
      val expected = getJSON("function_turnRelative")
      val actual = littleJSON.serialize(functionComplex)
      println(actual)
      JSONAssert.assertEquals(expected, actual, true)
    }
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
