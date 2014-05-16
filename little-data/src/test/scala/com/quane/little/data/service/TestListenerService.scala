package com.quane.little.data.service

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.quane.little.data.{DataBindingModule, EmbeddedMongoDB}
import com.escalatesoft.subcut.inject.{NewBindingModule, Injectable}
import com.mongodb.casbah.MongoClient

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestListenerService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers with Injectable {

  implicit val bindingModule = TestListenerServiceBindingModule

  val users = inject[UserService]
  val listeners = inject[ListenerService]

  val userOne = "connollyst"

  override def beforeAll() {
    super.beforeAll()
    users.init()
    users.upsert(userOne)
  }

  "ListenerService" should "init" in {
    listeners.init()
  }

  it should "fetch listeners by user" in {
    listeners.init()
    listeners.findByUser(userOne)
  }

}

object TestListenerServiceBindingModule extends NewBindingModule(module => {

  import module._

  module <~ DataBindingModule

  // Override the normal MongoDB client with that of the embedded database
  bind[MongoClient] toProvider {
    MongoClient(EmbeddedMongoDB.Host, EmbeddedMongoDB.Port)
  }

})
