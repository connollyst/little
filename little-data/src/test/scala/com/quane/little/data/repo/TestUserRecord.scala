package com.quane.little.data.repo

import com.quane.little.data.model.{UserRecord, RecordId}
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.skyscreamer.jsonassert.JSONAssert

/** Test cases for the [[UserRecord]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestUserRecord extends FlatSpec with ShouldMatchers {

  val littleJSON = new LittleJSON()
  val user = new UserRecord("User Name", "First Name", "Last Name")
  user.id = new RecordId("abcd1234")
  val json = // TODO move to JSON file
    """{
      |"_id" : { "$oid" : "abcd1234"},
      | "username"  : "User Name",
      | "firstname" : "First Name",
      | "lastname"  : "Last Name"
      | }"""
      .stripMargin

  "UserRecord" should "serialize to JSON" in {
    JSONAssert.assertEquals(json, littleJSON.serialize(user), true)
  }

  it should "deserialize from JSON" in {
    littleJSON.deserialize[UserRecord](json) should be(user)
  }

}
