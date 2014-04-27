package com.quane.little.ide.model

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.MongoClient

/** Test cases for the [[UserService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestUserService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  val client = MongoClient(mongoHost, mongoPort)
  val users = UserService(client)

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

}
