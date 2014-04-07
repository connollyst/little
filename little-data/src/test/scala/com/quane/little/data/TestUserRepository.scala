package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import com.quane.little.data.model.{FunctionORM, User}
import com.quane.little.language.Functions
import com.quane.little.tools.json.LittleJSON
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class TestUserRepository extends FlatSpec with ShouldMatchers {

  "json serializer" should "persist user" in {
    val mongoClient = MongoClient()
    println("Connected to MongoDB @ " + mongoClient.address)
    val mongoDB = mongoClient("little_db")
    println("Connected to MongoDB '" + mongoDB.name + "'")
    val collection = mongoDB("test_users2")
    println("Working with collection '" + collection.name + "': " + collection.size)
    collection foreach {
      record => println(":: " + record)
    }
    // Write a new user into the database
    val user = new User("sean")
    println("Writing user to MongoDB: " + user)
    val json = new LittleJSON().serialize(user)
    println(json)
    JSON.parse(json) match {
      case dbObject: DBObject =>
        val writeResult = collection += dbObject
        println("Wrote user to MongoDB: " + user + ": " + writeResult)
      case _ => throw new RuntimeException("=*(")
    }
    // Now let's print out the database
    val cursor = collection.find()
    cursor foreach {
      record =>
        val json = JSON.serialize(record)
        println(":: " + json)
        val u = new LittleJSON().deserialize[User](json)
        println("::>> " + u)
    }
  }

  it should "persist bson function" in {
    val mongoClient = MongoClient()
    println("Connected to MongoDB @ " + mongoClient.address)
    val mongoDB = mongoClient("little_db")
    println("Connected to MongoDB '" + mongoDB.name + "'")
    val collection = mongoDB("test_functionz3")
    println("Working with collection '" + collection.name + "': " + collection.size)
    collection foreach {
      record => println(":: " + record)
    }
    // Write a new function into the database
    val function = new FunctionORM(Functions.blank)
    println("Writing function to MongoDB: " + function)
    val json = new LittleJSON().serialize(function)
    println(json)
    JSON.parse(json) match {
      case dbObject: DBObject =>
        val writeResult = collection += dbObject
        println("Wrote function to MongoDB: " + function + ": " + writeResult)
      case _ => throw new RuntimeException("=*(")
    }
    // Now let's print out the database
    val cursor = collection.find()
    cursor foreach {
      record =>
        val json = JSON.serialize(record)
        println(":: " + json)
        val fun = new LittleJSON().deserialize[FunctionORM](json)
        println("::>> " + fun)
    }
  }

}
