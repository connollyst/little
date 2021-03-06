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
  val userId = new UserId("UserRecordID")
  val category = CodeCategory.Misc
  // A blank function definition
  val blankFunction = new FunctionDefinition("BlankFunction")
  val blankFunctionId = new FunctionId("BlankFunctionRecordID")
  val blankRecord = new FunctionRecord(userId, category, blankFunction)
  blankRecord.id = blankFunctionId
  // A more complex function definition
  val complexFunction = Functions.turnRelative
  val complexFunctionId = new FunctionId("ComplexFunctionRecordID")
  val complexRecord = new FunctionRecord(userId, category, complexFunction)
  complexRecord.id = complexFunctionId

  classOf[FunctionRecord].getSimpleName should {
    "serialize blank function to JSON" in {
      val expected = getJSON("function_blank")
      val actual = littleJSON.serialize(blankRecord)
      JSONAssert.assertEquals(expected, actual, true)
    }
    "deserialize record id from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual.id should be(blankFunctionId)
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
      actual.definition should be(blankFunction)
    }
    "deserialize from JSON" in {
      val actual = littleJSON.deserialize[FunctionRecord](getJSON("function_blank"))
      actual should be(blankRecord)
    }
    "serialize complex function to JSON" in {
      val expected = getJSON("function_complex")
      val actual = littleJSON.serialize(complexRecord)
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
