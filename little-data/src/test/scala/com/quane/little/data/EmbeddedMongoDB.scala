package com.quane.little.data

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import org.scalatest.BeforeAndAfterAll

/** A ScalaTest mixin which starts up and shuts down an embedded MongoDB
  * instance before and after the test suite.
  *
  * @author Sean Connolly
  */
trait EmbeddedMongoDB extends MongoEmbedDatabase with BeforeAndAfterAll {
  this: com.quane.little.data.EmbeddedMongoDB with org.scalatest.Suite =>

  val mongoVersion = "2.4.10"
  val mongoHost = EmbeddedMongoDB.Host
  var mongoPort = EmbeddedMongoDB.Port
  var mongoProps: MongodProps = null

  override def beforeAll() {
    println("Starting embedded MongoDB instance for " + getClass + " @ " + mongoPort)
    mongoProps = mongoStart(mongoPort)
  }

  override def afterAll() {
    mongoStop(mongoProps)
  }

  def mongoClient: MongoClient = MongoClient(mongoHost, mongoPort)

  def mongoDB(db: String): MongoDB = mongoClient(db)

  def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

}

object EmbeddedMongoDB {

  val Host = "127.0.0.1"
  // TODO resolve available port
  val Port = 13742

}