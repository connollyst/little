package com.quane.little.ide.model

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.FunctionDefinition
import com.mongodb.casbah.MongoClient

/** Test cases for the [[FunctionService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  val client = MongoClient(mongoHost, mongoPort)
  val users = UserService(client)
  val functions = FunctionService(client)

  val userOne = "userOne"
  val userTwo = "userTwo"

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
    functions.insert(userOne, new FunctionDefinition(name))
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
    functions.insert(userTwo, new FunctionDefinition(name))
    val exists = functions.exists(userOne, name)
    exists should be(right = false)
  }

}
