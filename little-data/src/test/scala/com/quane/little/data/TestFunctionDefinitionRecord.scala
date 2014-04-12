package com.quane.little.data

import com.quane.little.data.model.{FunctionDefinitionRecord, RecordID}
import com.quane.little.language.FunctionDefinition
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert

/** Test cases for the [[FunctionDefinitionRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionRecord extends FlatSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val functionRecordId = new RecordID("FunctionDefinitionRecordID")
  val userRecordId = new RecordID("UserRecordID")
  val functionDefinition = new FunctionDefinition("MyFunction")
  val function = new FunctionDefinitionRecord(userRecordId, functionDefinition)
  function.id = functionRecordId
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
    val actual = littleJSON.deserialize[FunctionDefinitionRecord](json)
    actual.id should be(functionRecordId)
  }
  it should "deserialize owner record id from JSON" in {
    val actual = littleJSON.deserialize[FunctionDefinitionRecord](json)
    actual.ownerId should be(userRecordId)
  }
  it should "deserialize function definition from JSON" in {
    val actual = littleJSON.deserialize[FunctionDefinitionRecord](json)
    actual.definition should be(functionDefinition)
  }
  it should "deserialize from JSON" in {
    val actual = littleJSON.deserialize[FunctionDefinitionRecord](json)
    actual should be(function)
  }

}
