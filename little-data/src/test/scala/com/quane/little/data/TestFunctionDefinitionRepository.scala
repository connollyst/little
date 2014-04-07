package com.quane.little.data

import com.mongodb.util.JSON
import com.quane.little.data.model.FunctionDefinitionRecord
import com.quane.little.language.Functions
import com.quane.little.tools.json.LittleJSON
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionRepository extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  "json serializer" should "persist bson function" in {
    val collection = mongoCollection("little_db", "test_users")
    println("Working with collection '" + collection.name + "': " + collection.size)
    collection foreach {
      record => println(":: " + record)
    }
    // Write a new function into the database
    val function = new FunctionDefinitionRecord("1234", Functions.blank)
    println("Writing function to MongoDB: " + function)
    new FunctionDefinitionRepository().insert(collection, function)
    // Now let's print out the database
    val cursor = collection.find()
    cursor foreach {
      record =>
        val json = JSON.serialize(record)
        println(":: " + json)
        val fun = new LittleJSON().deserialize[FunctionDefinitionRecord](json)
        println("::>> " + fun)
    }
  }

}
