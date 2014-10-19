package com.quane.little.data

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import com.quane.little.data.service.UserService
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.data.model.{UserId, Id}

/** Test cases for the mock [[com.quane.little.data.service.UserService]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestMockUserService extends WordSpec with ShouldMatchers with BeforeAndAfterEach with Injectable {

  implicit val bindingModule = MockDataBindingModule
  private val userService = inject[UserService]

  /**
   * Before each test, the service is wiped clean.
   */
  override def beforeEach() {
    userService.init()
  }

  "MockDataBindingModule" should {
    "inject MockUserService" in {
      userService.getClass should be(classOf[MockUserService])
    }
  }

  "MockUserService" should {
    "remove all records on init" in {
      userService.upsert("UserA")
      userService.init()
      userService.exists("UserA") should be(right = false)
    }
    "assign id on insert" in {
      val user = userService.upsert("UserA")
      user.id should not be null
    }
    "assign unique on insert" in {
      val user1 = userService.upsert("UserA")
      val user2 = userService.upsert("UserB")
      user1.id should not be user2.id
    }
    "maintain same id on update" in {
      val user1 = userService.upsert("UserA")
      val user2 = userService.upsert("UserA")
      user1.id should be(user2.id)
    }
    "fetch user for existing user id" in {
      val user1 = userService.upsert("UserA")
      val user2 = userService.fetch(user1.id)
      user2.id should be(user1.id)
    }
    "fetch user for existing username" in {
      val user1 = userService.upsert("UserA")
      val user2 = userService.fetch("UserA")
      user2.id should be(user1.id)
    }
    "throw error when fetching user for non-existing user id" in {
      val id = "A Non-Existing Id"
      val error = intercept[IllegalArgumentException] {
        userService.fetch(new UserId(id))
      }
      error.getMessage should be("No user: " + id)
    }
    "throw error when fetching user for non-existing username" in {
      val name = "A Non-Existing Username"
      val error = intercept[IllegalArgumentException] {
        userService.fetch(name)
      }
      error.getMessage should be("No user: " + name)
    }
  }

}
