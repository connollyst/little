package com.quane.little.data

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import com.quane.little.data.service.{UserService, FunctionService}
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.model.CodeCategory
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
    "assign id on insert" in {
      val record = functionService.insert(
        testUsernameA,
        CodeCategory.Misc,
        new FunctionDefinition("test_function")
      )
      record.id should not be null
    }
  }

}
