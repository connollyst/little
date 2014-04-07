package com.quane.little.data

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import com.mongodb.casbah.MongoClient
import org.scalatest.{FlatSpec, BeforeAndAfter}

/** A ScalaTest [[FlatSpec]] which starts up and shuts down an embedded MongoDB
  * database before and after the test suite.
  *
  * @author Sean Connolly
  */
class FlatSpecMongoDB extends FlatSpec with MongoEmbedDatabase with BeforeAndAfter {

  val mongoHost = "127.0.0.1"
  val mongoPort = 12345
  val mongoVersion = "2.4.10"
  var mongoProps: MongodProps = null

  before {
    mongoProps = mongoStart()
  }

  after {
    mongoStop(mongoProps)
  }

  def mongoDBClient: MongoClient = MongoClient(mongoHost, mongoPort)

}
