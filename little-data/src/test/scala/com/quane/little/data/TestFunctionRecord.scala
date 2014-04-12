package com.quane.little.data

import com.quane.little.data.model.{FunctionRecord, RecordId}
import com.quane.little.language.FunctionDefinition
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert

/** Test cases for the [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionRecord extends FlatSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val functionId = new RecordId("FunctionDefinitionRecordID")
  val userId = new RecordId("UserRecordID")
  val definition = new FunctionDefinition("MyFunction")
  val function = new FunctionRecord(userId, definition)
  function.id = functionId
  val json = // TODO move to JSON file
    """{
      |"_id":{"$oid":"FunctionDefinitionRecordID"},
      |"ownerId":{"$oid":"UserRecordID"},
      |"definition":{
      |     "@class":"com.quane.little.language.FunctionDefinition",
      |     "name":"MyFunction",
      |     "params":[],
      |     "steps":[]
      |}
      |}"""
      .stripMargin

  "FunctionDefinitionRecord" should "serialize to JSON" in {
    JSONAssert.assertEquals(json, littleJSON.serialize(function), true)
  }
  it should "deserialize record id from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](json)
    actual.id should be(functionId)
  }
  it should "deserialize owner record id from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](json)
    actual.ownerId should be(userId)
  }
  it should "deserialize function definition from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](json)
    actual.definition should be(definition)
  }
  it should "deserialize from JSON" in {
    val actual = littleJSON.deserialize[FunctionRecord](json)
    actual should be(function)
  }

}
