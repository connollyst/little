package com.quane.little.data

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import com.quane.little.data.service.{UserService, FunctionService}
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.model.{FunctionId, Id, CodeCategory}
import com.quane.little.language.FunctionDefinition
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/** Test cases for the mock service.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMockFunctionService extends WordSpec with ShouldMatchers with BeforeAndAfterEach with Injectable {

  implicit val bindingModule = MockDataBindingModule
  private val functionService = inject[FunctionService]
  private val userService = inject[UserService]
  private val testUsernameA = "UserA"
  private val testUsernameB = "UserB"

  /**
   * Before each test, the services are wiped clean. The user service is
   * reinitialized with two mock users.
   */
  override def beforeEach() {
    functionService.init()
    userService.init()
    userService.upsert(testUsernameA)
    userService.upsert(testUsernameB)
  }

  "MockDataBindingModule" should {
    "inject MockFunctionService" in {
      functionService.getClass should be(classOf[MockFunctionService])
    }
  }

  "MockFunctionService" should {
    "assign id on insert" in {
      val function = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("MyFunction")
      )
      function.id should not be null
    }
    "assign unique id on insert" in {
      val functionA1 = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA1")
      )
      val functionA2 = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA2")
      )
      functionA1.id should not be functionA2.id
    }
    "maintain id on update" in {
      val originalFunction = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("OriginalFunction")
      )
      val updatedFunction = functionService.update(
        originalFunction.id, CodeCategory.Misc, new FunctionDefinition("UpdatedFunction")
      )
      originalFunction.id should be(updatedFunction.id)
    }
    "error when updating with unknown id" in {
      val badId = "SomeFakeId"
      val error = intercept[IllegalArgumentException] {
        functionService.update(
          new FunctionId(badId), CodeCategory.Misc, new FunctionDefinition("UpdatedFunction")
        )
      }
      error.getMessage should be("No function: " + badId)
    }
    "remove all records on init" in {
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA1")
      )
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA2")
      )
      functionService.insert(
        testUsernameB, CodeCategory.Misc, new FunctionDefinition("FunctionB1")
      )
      functionService.insert(
        testUsernameB, CodeCategory.Misc, new FunctionDefinition("FunctionB2")
      )
      functionService.init()
      functionService.findByUser(testUsernameA).size should be(0)
      functionService.findByUser(testUsernameB).size should be(0)
    }
    "find all records for each user" in {
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA1")
      )
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("FunctionA2")
      )
      functionService.insert(
        testUsernameB, CodeCategory.Misc, new FunctionDefinition("FunctionB1")
      )
      functionService.insert(
        testUsernameB, CodeCategory.Misc, new FunctionDefinition("FunctionB2")
      )
      functionService.findByUser(testUsernameA).size should be(2)
      functionService.findByUser(testUsernameB).size should be(2)
    }
    "report existing definition exists" in {
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("MyFunction")
      )
      val exists = functionService.exists(testUsernameA, "MyFunction")
      exists should be(right = true)
    }
    "report non existing definition doesn't exist" in {
      functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("MyFunction")
      )
      val exists = functionService.exists(testUsernameA, "AnotherFunction")
      exists should be(right = false)
    }
    "find definition by id" in {
      val function = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("MyFunction")
      )
      val definition = functionService.findDefinition(function.id)
      function.definition should be(definition)
    }
    "find reference by id" in {
      val function = functionService.insert(
        testUsernameA, CodeCategory.Misc, new FunctionDefinition("MyFunction")
      )
      val reference = functionService.findReference(function.id)
      function.definition.asReference should be(reference)
    }
    "not find definition by unknown id" in {
      val badId = "SomeFakeId"
      val error = intercept[IllegalArgumentException] {
        functionService.findDefinition(new FunctionId(badId))
      }
      error.getMessage should be("No function: " + badId)
    }
    "not find reference by unknown id" in {
      val badId = "SomeFakeId"
      val error = intercept[IllegalArgumentException] {
        functionService.findReference(new FunctionId(badId))
      }
      error.getMessage should be("No function: " + badId)
    }
  }

}
