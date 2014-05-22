package com.quane.little.data

import com.quane.little.data.service.UserService
import com.quane.little.data.model.{RecordId, UserRecord}
import scala.collection.mutable.ListBuffer

/** A mock implementation of [[com.quane.little.data.service.UserService]] for
  * testing higher level functionality.
  *
  * @author Sean Connolly
  */
class MockUserService extends UserService {

  private val users = ListBuffer[UserRecord]()
  private var idSequence = 1

  override def init(): Unit = {
    users.clear()
    idSequence = 1
  }

  private def nextId: RecordId = {
    idSequence += 1
    new RecordId(idSequence.toString)
  }

  override def fetch(username: String): UserRecord =
    getUser(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + username)
    }

  override def fetch(userId: RecordId): UserRecord =
    getUser(userId) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + userId.oid)
    }

  override def upsert(username: String): UserRecord =
    getUser(username) match {
      case Some(u) => u
      case None => insert(username)
    }

  override def exists(username: String): Boolean =
    getUser(username).isDefined

  private def getUser(username: String): Option[UserRecord] = {
    users foreach {
      user => if (user.username == username) return Some(user)
    }
    None
  }

  private def getUser(userId: RecordId): Option[UserRecord] = {
    users foreach {
      user => if (user.id == userId) return Some(user)
    }
    None
  }

  private def insert(username: String): UserRecord = {
    val record = new UserRecord(username, username + "First", username + "Last")
    record.id = nextId
    users += record
    record
  }

}
