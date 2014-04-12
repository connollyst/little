package com.quane.little.data

import com.mongodb.casbah.MongoCollection
import com.mongodb.util.JSON
import com.quane.little.data.model.UserRecord
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the [[UserRepository]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestUserRepository extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  val username = "TestUsername"
  val firstname = "TestFirstname"
  val lastname = "TestLastname"
  val littleJSON = new LittleJSON()

  "UserRepository" should "assign a user record id on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    user.id should be(null)
    repo.insert(user)
    user.id should not be null
  }
  it should "maintain a user's username on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.username should be(username)
  }
  it should "maintain a user's first name on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.firstname should be(firstname)
  }
  it should "maintain a user's last name on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.lastname should be(lastname)
  }
  it should "insert user record" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val userIn = new UserRecord(username, firstname, lastname)
    repo.insert(userIn)
    val jsonOut = JSON.serialize(collection.findOne())
    val userOut = littleJSON.deserialize[UserRecord](jsonOut)
    userOut should be(userIn)
  }
  it should "fetch user record" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val userIn = new UserRecord(username, firstname, lastname)
    repo.insert(userIn)
    val userOut = repo.find(userIn.id)
    userOut should be(userIn)
  }

  private def mongoCollection: MongoCollection =
    mongoCollection("little_db", "test_users")

}
