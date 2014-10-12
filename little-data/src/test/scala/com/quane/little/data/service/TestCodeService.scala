package com.quane.little.data.service

import com.escalatesoft.subcut.inject.{Injectable, NewBindingModule}
import com.mongodb.casbah.MongoClient
import com.quane.little.data.model.RecordId
import com.quane.little.data.{DataBindingModule, EmbeddedMongoDB}
import com.quane.little.language._
import org.junit.runner.RunWith
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the [[com.quane.little.data.service.PrimitiveService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestCodeService extends WordSpec with ShouldMatchers with EmbeddedMongoDB with Injectable {

  implicit val bindingModule = TestCodeServiceBindingModule

  val userOne = "userOne"
  val userTwo = "userTwo"
  val users = inject[UserService]
  val functions = inject[FunctionService]
  val code = inject[CodeService]

  override def beforeAll() {
    super.beforeAll()
    users.init()
    users.upsert(userOne)
    users.upsert(userTwo)
    // TODO if we always initialize, we can't test initialization..
    functions.init()
  }

  "CodeService" should {
    "find primitive Get" in {
      findPrimitive(PrimitiveService.Get, classOf[Getter])
    }
    "find primitive Set" in {
      findPrimitive(PrimitiveService.Set, classOf[Setter])
    }
    "find primitive Print" in {
      findPrimitive(PrimitiveService.Print, classOf[Printer])
    }
    "find primitive Conditional" in {
      findPrimitive(PrimitiveService.Conditional, classOf[Conditional])
    }
  }

  private def findPrimitive(id: String, expected: Class[_ <: Code]): Unit =
    code.find(new RecordId(id)) match {
      case Some(primitive) => primitive.getClass should be(expected)
      case None => fail("Expected " + expected + " for " + id)
    }

}


object TestCodeServiceBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  // Override the normal MongoDB client with that of the embedded database
  bind[MongoClient] toProvider {
    MongoClient(EmbeddedMongoDB.Host, EmbeddedMongoDB.Port)
  }

})