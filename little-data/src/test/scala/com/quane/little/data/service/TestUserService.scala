package com.quane.little.data.service

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.{EmbeddedDatabaseBindingModule, EmbeddedMongoDB}
import com.escalatesoft.subcut.inject.Injectable

/** Test cases for the [[com.quane.little.data.service.UserService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestUserService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers with Injectable {

  implicit val bindingModule = EmbeddedDatabaseBindingModule

  val users = inject[UserService]

  "UserService" should "insert new user" in {
    val name = "UserOne"
    val user = users.upsert(name)
    user.id should not be null
  }
  it should "update existing user" in {
    val name = "UserOne"
    val user1 = users.upsert(name)
    val user2 = users.upsert(name)
    user1.id should be(user2.id)
  }
  it should "find existing user" in {
    val name = "UserOne"
    users.upsert(name)
    val user = users.fetch(name)
    user should not be null
  }
  it should "not find system user before initialization" in {
    val thrown = intercept[IllegalArgumentException] {
      users.fetch(UserService.SYSTEM_USERNAME)
    }
    thrown.getMessage should be("No user record for username '" + UserService.SYSTEM_USERNAME + "'.")
  }
  it should "find system user after initialization" in {
    users.init()
    val user = users.fetch(UserService.SYSTEM_USERNAME)
    user should not be null
  }

}
