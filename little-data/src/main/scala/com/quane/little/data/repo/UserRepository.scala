package com.quane.little.data.repo

import com.mongodb.casbah.Imports
import com.mongodb.casbah.Imports._
import com.quane.little.data.model.{UserId, UserRecord}
import com.quane.little.tools.Logging

/** Provides storage and retrieval access to the repository of [[com.quane.little.data.model.UserRecord]]
  * objects.
  *
  * @author Sean Connolly
  */
class UserRepository(collection: MongoCollection)
  extends MongoRepository[UserId, UserRecord](collection) with Logging {

  def find(username: String): Option[UserRecord] = {
    val query = MongoDBObject("username" -> username)
    val result = collection.findOne(query)
    deserialize(result)
  }

  def insert(username: String, firstname: String, lastname: String): UserRecord = {
    info("Registering new user: " + username)
    val user = new UserRecord(username, firstname, lastname)
    insert(user)
    user
  }

  protected def recordId(oid: Imports.ObjectId) = new UserId(oid)

}