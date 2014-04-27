package com.quane.little.ide.model

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import org.scalatest.BeforeAndAfterAll

/** A ScalaTest mixin which starts up and shuts down an embedded MongoDB
  * instance before and after the test suite.
  *
  * @author Sean Connolly
  */
trait EmbeddedMongoDB extends MongoEmbedDatabase with BeforeAndAfterAll {
  this: com.quane.little.ide.model.EmbeddedMongoDB with org.scalatest.Suite =>

  val mongoVersion = "2.4.10"
  val mongoHost = "127.0.0.1"
  // TODO resolve available port
  val mongoPort = 12345
  var mongoProps: MongodProps = null

  override def beforeAll() {
    mongoProps = mongoStart()
  }

  override def afterAll() {
    mongoStop(mongoProps)
  }

  def mongoClient: MongoClient = MongoClient(mongoHost, mongoPort)

  def mongoDB(db: String): MongoDB = mongoClient(db)

  def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

}
