package com.quane.little.data

import com.quane.little.data.model.{RecordID, FunctionDefinitionRecord}
import com.quane.little.language.FunctionDefinition
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionRepository
  extends FlatSpec with EmbeddedMongoDB with ShouldMatchers with BeforeAndAfterAll {

  val littleJSON = new LittleJSON()
  var ownerId: RecordID = _
  val definition = new FunctionDefinition("MyFunction")

  /** Before the tests start, prepare a [[com.quane.little.data.model.UserRecord]]
    * to test with.
    *
    * Also, start up the [[com.quane.little.data.EmbeddedMongoDB]].
    */
  override def beforeAll() {
    mongoProps = mongoStart()
    ownerId = userRepository.insert("Username", "FirstName", "LastName").id
  }

  "FunctionDefinitionRepository" should "assign a record id on insert" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    record.id should be(null)
    repo.insert(record)
    record.id should not be null
  }
  it should "maintain an owner's id on insert" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    repo.insert(record)
    record.ownerId should be(ownerId)
  }
  it should "maintain a function's definition on insert" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    repo.insert(record)
    record.definition should be(definition)
  }
  it should "update with known id" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    repo.insert(record)
    val recordId = record.id
    repo.insert(record)
    record.id should be(recordId)
    // TODO change all fields but id and assert everything persisted w/ fetch
  }
  it should "error out on insert with known id" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    repo.insert(record)
    val recordId = record.id
    val thrown = intercept[IllegalArgumentException] {
      repo.insert(record)
    }
    thrown.getMessage should be(
      "Cannot insert FunctionRecord with known id: " + recordId.oid
    )
  }
  it should "error out on update without id" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    record.id = null
    val thrown = intercept[IllegalArgumentException] {
      repo.update(record)
    }
    thrown.getMessage should be(
      "Cannot update FunctionRecord with no id."
    )
  }
  it should "error out on insert with unknown id" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    record.id = new RecordID("UnknownId1234")
    val thrown = intercept[IllegalArgumentException] {
      repo.insert(record)
    }
    thrown.getMessage should be(
      "Cannot insert FunctionRecord with unknown id: UnknownId1234"
    )
  }
  it should "error out on update with unknown id" in {
    val repo = functionRepository
    val record = new FunctionDefinitionRecord(ownerId, definition)
    record.id = new RecordID("UnknownId1234")
    val thrown = intercept[IllegalArgumentException] {
      repo.update(record)
    }
    thrown.getMessage should be(
      "Cannot update FunctionRecord with unknown id: UnknownId1234"
    )
  }
  it should "fetch function record" in {
    val repo = functionRepository
    val recordIn = new FunctionDefinitionRecord(ownerId, definition)
    repo.insert(recordIn)
    val recordOut = repo.find(recordIn.id).get
    recordOut should be(recordIn)
  }

  private def userRepository: UserRepository =
    new UserRepository(mongoCollection("little_db", "test_users"))

  private def functionRepository: FunctionDefinitionRepository =
    new FunctionDefinitionRepository(mongoCollection("little_db", "test_functions"))

}
