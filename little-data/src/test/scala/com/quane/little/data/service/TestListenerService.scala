package com.quane.little.data.service

import org.scalatest.FlatSpec
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import com.mongodb.casbah.MongoClient
import com.quane.little.data.EmbeddedMongoDB

/** Test cases for the [[com.quane.little.data.service.ListenerService]]
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestListenerService extends FlatSpec with EmbeddedMongoDB with ShouldMatchers {

  val client = MongoClient(mongoHost, mongoPort)
  val users = UserService(client)
  val listeners = ListenerService(client)

  val userOne = "connollyst"

  override def beforeAll() {
    super.beforeAll()
    users.init()
    users.upsert(userOne)
  }

  "ListenerService" should "detect existing function" in {
    listeners.init()
  }

}
