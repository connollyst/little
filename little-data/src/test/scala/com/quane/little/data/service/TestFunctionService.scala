package com.quane.little.data.service

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.FunctionDefinition
import com.quane.little.data.model.FunctionCategory
import com.quane.little.data.{DataBindingModule, EmbeddedMongoDB}
import com.mongodb.casbah.MongoClient
import com.escalatesoft.subcut.inject.{Injectable, NewBindingModule}

/** Test cases for the [[com.quane.little.data.service.FunctionService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionService extends FlatSpec with ShouldMatchers with EmbeddedMongoDB with Injectable {

  implicit val bindingModule = TestFunctionServiceBindingModule

  val userOne = "userOne"
  val userTwo = "userTwo"
  val users = inject[UserService]
  val functions = inject[FunctionService]

  override def beforeAll() {
    super.beforeAll()
    users.init()
    users.upsert(userOne)
    users.upsert(userTwo)
    // TODO if we always initialize, we can't test initialization..
    functions.init()
  }

  "FunctionService" should "detect existing function" in {
    val name = "MyFunction"
    functions.insert(userOne, FunctionCategory.Misc, new FunctionDefinition(name))
    val exists = functions.exists(userOne, name)
    exists should be(right = true)
  }
  it should "not detect non-existing function" in {
    val name = "UnknownFunction"
    val exists = functions.exists(userOne, name)
    exists should be(right = false)
  }
  it should "not detect other user's existing function" in {
    val name = "HisFunction"
    functions.insert(userTwo, FunctionCategory.Misc, new FunctionDefinition(name))
    val exists = functions.exists(userOne, name)
    exists should be(right = false)
  }

}

object TestFunctionServiceBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  // Override the normal MongoDB client with that of the embedded database
  bind[MongoClient] toProvider {
    MongoClient(EmbeddedMongoDB.Host, EmbeddedMongoDB.Port)
  }

})