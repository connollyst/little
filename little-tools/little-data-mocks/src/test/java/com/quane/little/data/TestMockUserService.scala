package com.quane.little.data

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import com.quane.little.data.service.UserService
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the mock [[com.quane.little.data.service.UserService]].
  *
  * @author Sean Connolly
  */
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
      val record = userService.upsert("UserA")
      record.id should not be null
    }
  }

}
