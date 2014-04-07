package com.quane.little.data

import com.mongodb.util.JSON
import com.quane.little.data.model.UserRecord
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestUserRepository extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  "json serializer" should "persist user" in {
    val collection = mongoCollection("little_db", "test_users")
    println("Working with collection '" + collection.name + "': " + collection.size)
    collection foreach {
      record => println(":: " + record)
    }
    // Write a new user into the database
    val user = new UserRecord("sean")
    println("Writing user to MongoDB: " + user)
    new UserRepository().insert(collection, user)
    // Now let's print out the database
    val cursor = collection.find()
    cursor foreach {
      record =>
        val json = JSON.serialize(record)
        println(":: " + json)
        val u = new LittleJSON().deserialize[UserRecord](json)
        println("::>> " + u)
    }
  }

}
