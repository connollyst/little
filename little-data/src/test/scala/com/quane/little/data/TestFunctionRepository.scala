package com.quane.little.data

import com.quane.little.data.model.{CodeCategory, RecordId, FunctionRecord}
import com.quane.little.language.{Functions, PrintStatement, FunctionDefinition}
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import com.quane.little.language.data.Value
import com.quane.little.data.repo.{UserRepository, FunctionRepository}

@RunWith(classOf[JUnitRunner])
class TestFunctionRepository
  extends FlatSpec with EmbeddedMongoDB with ShouldMatchers with BeforeAndAfterAll {

  val littleJSON = new LittleJSON()
  var ownerId: RecordId = _
  val definition = new FunctionDefinition("MyFunction")

  /** Before the tests start, prepare a [[com.quane.little.data.model.UserRecord]]
    * to test with.
    *
    * Also, start up the [[com.quane.little.data.EmbeddedMongoDB]].
    */
  override def beforeAll() {
    super.beforeAll()
    ownerId = userRepository.insert("Username", "FirstName", "LastName").id
  }

  "FunctionDefinitionRepository" should "assign a function id on insert" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    function.id should be(null)
    repo.insert(function)
    function.id should not be null
  }
  it should "maintain an owner's id on insert" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(function)
    function.ownerId should be(ownerId)
  }
  it should "maintain a function's definition on insert" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(function)
    function.definition should be(definition)
  }
  it should "update with known id (id doesn't change)" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(function)
    val recordId = function.id
    repo.update(function)
    function.id should be(recordId)
    // TODO change all fields but id and assert everything persisted w/ fetch
  }
  it should "update function with known id (definition changes)" in {
    val originalDefinition = new FunctionDefinition("Original Function")
    val updatedDefinition = new FunctionDefinition("Updated Function")
    originalDefinition.addParam("Param 1")
    originalDefinition.addParam("Param 2")
    updatedDefinition.addParam("Param A")
    updatedDefinition.addParam("Param B")
    originalDefinition.addStep(new PrintStatement(Value("Hello Original World!")))
    updatedDefinition.addStep(new PrintStatement(Value("Hello Updated World!")))
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, originalDefinition)
    repo.insert(function)
    val recordId = function.id
    function.definition = updatedDefinition
    repo.update(function)
    function.id should be(recordId)
    function.ownerId should be(ownerId)
    function.definition should be(updatedDefinition)
  }
  it should "error out on insert with known id" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(function)
    val recordId = function.id
    val thrown = intercept[IllegalArgumentException] {
      repo.insert(function)
    }
    thrown.getMessage should be(
      "Cannot insert FunctionRecord with known id: " + recordId.oid
    )
  }
  it should "error out on update without id" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    function.id = null
    val thrown = intercept[IllegalArgumentException] {
      repo.update(function)
    }
    thrown.getMessage should be(
      "Cannot update FunctionRecord with no id."
    )
  }
  it should "error out on insert with unknown id" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    function.id = new RecordId("UnknownId1234")
    val thrown = intercept[IllegalArgumentException] {
      repo.insert(function)
    }
    thrown.getMessage should be(
      "Cannot insert FunctionRecord with unknown id: UnknownId1234"
    )
  }
  it should "error out on update with unknown id" in {
    val repo = functionRepository
    val function = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    function.id = new RecordId("UnknownId1234")
    val thrown = intercept[IllegalArgumentException] {
      repo.update(function)
    }
    thrown.getMessage should be(
      "Cannot update FunctionRecord with unknown id: UnknownId1234"
    )
  }
  it should "insert simple function" in {
    val repo = functionRepository
    val functionIn = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(functionIn)
    functionIn.id should not be null
  }
  it should "insert and find simple function" in {
    val repo = functionRepository
    val functionIn = new FunctionRecord(ownerId, CodeCategory.Misc, definition)
    repo.insert(functionIn)
    val functionOut = repo.find(functionIn.id).get
    functionOut should be(functionIn)
  }
  it should "insert complex function" in {
    val repo = functionRepository
    val functionIn = new FunctionRecord(ownerId, CodeCategory.Misc, Functions.voyage)
    repo.insert(functionIn)
    functionIn.id should not be null
  }
  it should "insert and find complex function" in {
    val repo = functionRepository
    val functionIn = new FunctionRecord(ownerId, CodeCategory.Misc, Functions.voyage)
    repo.insert(functionIn)
    val functionOut = repo.find(functionIn.id).get
    functionOut should be(functionIn)
  }

  private def userRepository: UserRepository =
    new UserRepository(mongoCollection("little_db", "test_users"))

  private def functionRepository: FunctionRepository =
    new FunctionRepository(mongoCollection("little_db", "test_functions"))

}
