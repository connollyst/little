package com.quane.little.ide.model

import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import com.quane.little.data.UserRepository
import com.quane.little.data.model.UserRecord

/**
 *
 *
 * @author Sean Connolly
 */
object UserService {

  def upsert(username: String): UserRecord = {
    val collection = mongoCollection("little", "users")
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => repo.insert(username, username, username)
    }
  }

  def fetch(username: String): UserRecord = {
    val collection = mongoCollection("little", "users")
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException(
        "No user record for username '" + username + "'."
      )
    }
  }

  private def mongoClient: MongoClient = MongoClient()

  private def mongoDB(db: String): MongoDB = mongoClient(db)

  private def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

}
