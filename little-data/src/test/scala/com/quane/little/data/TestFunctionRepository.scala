package com.quane.little.data

import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import com.quane.little.data.model.FunctionORM
import com.quane.little.language.Functions
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestFunctionRepository extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  "json serializer" should "persist bson function" in {
    val collection = mongoCollection("little_db", "test_users")
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
