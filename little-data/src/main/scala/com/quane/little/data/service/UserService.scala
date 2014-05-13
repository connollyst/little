package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.model.{RecordId, UserRecord}
import com.quane.little.data.repo.UserRepository

object UserService {

  val SYSTEM_USERNAME = "little"

  private var instance: Option[UserService] = None

  def apply(): UserService = {
    if (!instance.isDefined) {
      instance = Some(new MongoUserService(MongoClient()))
    }
    instance.get
  }

  def apply(client: MongoClient): UserService = {
    instance = Some(new MongoUserService(client))
    instance.get
  }

}

trait UserService {

  def init(): Unit

  def upsert(username: String): UserRecord

  def fetch(username: String): UserRecord

  def fetch(userId: RecordId): UserRecord

  def exists(username: String): Boolean

}

class MongoUserService(client: MongoClient) extends UserService {

  /** Initialize the data source.
    */
  override def init(): Unit = upsert(UserService.SYSTEM_USERNAME)

  override def upsert(username: String): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => repo.insert(username, username, username)
    }
  }

  override def fetch(username: String): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException(
        "No user record for username '" + username + "'."
      )
    }
  }

  override def fetch(userId: RecordId): UserRecord = {
    val repo = new UserRepository(collection)
    repo.find(userId) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException(
        "No user record for userId '" + userId + "'."
      )
    }
  }

  override def exists(username: String): Boolean = {
    val repo = new UserRepository(collection)
    repo.find(username).isDefined
  }

  private def collection: MongoCollection = client("little")("users")

}

