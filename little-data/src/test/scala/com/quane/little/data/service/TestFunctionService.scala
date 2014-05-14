package com.quane.little.data.service

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.language.FunctionDefinition
import com.quane.little.data.model.FunctionCategory
import com.quane.little.data.{EmbeddedDatabaseBindingModule, EmbeddedMongoDB}
import com.escalatesoft.subcut.inject.Injectable

/** Test cases for the [[com.quane.little.data.service.FunctionService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers with Injectable {

  implicit val bindingModule = EmbeddedDatabaseBindingModule

  val users = inject[UserService]
  val functions = inject[FunctionService]

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
