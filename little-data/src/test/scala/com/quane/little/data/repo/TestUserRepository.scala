package com.quane.little.data.repo

import com.mongodb.casbah.MongoCollection
import com.quane.little.data.model.UserRecord
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.EmbeddedMongoDB

/** Test cases for the [[com.quane.little.data.repo.UserRepository]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestUserRepository extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  val username = "TestUsername"
  val firstname = "TestFirstname"
  val lastname = "TestLastname"
  val littleJSON = new LittleJSON()

  "UserRepository" should "assign a record id on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    user.id should be(null)
    repo.insert(user)
    user.id should not be null
  }
  it should "maintain username on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.username should be(username)
  }
  it should "maintain first name on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.firstname should be(firstname)
  }
  it should "maintain last name on insert" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val user = new UserRecord(username, firstname, lastname)
    repo.insert(user)
    user.lastname should be(lastname)
  }
  it should "fetch user record" in {
    val collection = mongoCollection
    val repo = new UserRepository(collection)
    val userIn = new UserRecord(username, firstname, lastname)
    repo.insert(userIn)
    val userOut = repo.find(userIn.id).get
    userOut should be(userIn)
  }

  private def mongoCollection: MongoCollection =
    mongoCollection("little_db", "test_users")

}
