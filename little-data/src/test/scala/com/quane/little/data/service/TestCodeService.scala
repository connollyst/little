package com.quane.little.data.service

import com.escalatesoft.subcut.inject.{Injectable, NewBindingModule}
import com.mongodb.casbah.MongoClient
import com.quane.little.data.model.PrimitiveId
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

  val code = inject[CodeService]

  "CodeService" should {
    "find primitive Get" in {
      println(code)
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
    code.find(new PrimitiveId(id)) match {
      case Some(primitive) => primitive.getClass should be(expected)
      case None => fail("Expected " + expected + " for " + id + ", got None")
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