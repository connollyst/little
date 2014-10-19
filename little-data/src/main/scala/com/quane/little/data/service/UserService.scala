package com.quane.little.data.service

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.mongodb.casbah.{MongoClient, MongoCollection}
import com.quane.little.data.model.{UserId, UserRecord}
import com.quane.little.data.repo.UserRepository

object UserService {

  val SYSTEM_USERNAME = "little"

}

trait UserService {

  def init(): Unit

  def upsert(username: String): UserRecord

  def fetch(username: String): UserRecord

  def fetch(userId: UserId): UserRecord

  def exists(username: String): Boolean

}

class MongoUserService(implicit val bindingModule: BindingModule)
  extends UserService
  with Injectable {

  private val client = inject[MongoClient]

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

  override def fetch(userId: UserId): UserRecord = {
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

