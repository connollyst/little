package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.UserRepository
import com.quane.little.data.model.{RecordId, UserRecord}

object UserService {

  val SYSTEM_USERNAME = "little"

  private var instance: Option[UserService] = None

  def apply(): UserService = {
    if (!instance.isDefined) {
      instance = Some(new UserService(MongoClient()))
    }
    instance.get
  }

  def apply(client: MongoClient): UserService = {
    instance = Some(new UserService(client))
    instance.get
  }

}

class UserService(client: MongoClient) {

  /** Initialize the data source.
    */
  def init(): Unit = upsert(UserService.SYSTEM_USERNAME)

  def upsert(username: String): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => repo.insert(username, username, username)
    }
  }

  def fetch(username: String): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException(
        "No user record for username '" + username + "'."
      )
    }
  }

  def fetch(userId: RecordId): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(userId) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException(
        "No user record for userId '" + userId + "'."
      )
    }
  }

  def exists(username: String): Boolean = {
    val repo = new UserRepository(collection)
    repo.find(username).isDefined
  }

  private def collection: MongoCollection = client("little")("users")

}

