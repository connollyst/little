package com.quane.little.data

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import org.scalatest.BeforeAndAfter

/** A ScalaTest mixin which starts up and shuts down an embedded MongoDB
  * instance before and after the test suite.
  *
  * @author Sean Connolly
  */
trait EmbeddedMongoDB extends MongoEmbedDatabase with BeforeAndAfter {
  this: com.quane.little.data.EmbeddedMongoDB with org.scalatest.Suite =>

  val mongoVersion = "2.4.10"
  val mongoHost = "127.0.0.1"
  // TODO resolve available port
  val mongoPort = 12345
  var mongoProps: MongodProps = null

  before {
    mongoProps = mongoStart()
  }

  after {
    mongoStop(mongoProps)
  }

  def mongoClient: MongoClient = MongoClient(mongoHost, mongoPort)

  def mongoDB(db: String): MongoDB = mongoClient(db)

  def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)
}
