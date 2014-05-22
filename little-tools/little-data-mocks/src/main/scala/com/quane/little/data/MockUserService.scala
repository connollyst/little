package com.quane.little.data

import com.quane.little.data.service.UserService
import com.quane.little.data.model.{RecordId, UserRecord}

/** A mock [[com.quane.little.data.service.UserService]] to be injected into
  * tests.
  *
  * @author Sean Connolly
  */
class MockUserService
  extends UserService
  with MockService[UserRecord] {

  override def upsert(username: String): UserRecord =
    getUser(username) match {
      case Some(u) => u
      case None => insert(username)
    }

  private def insert(username: String): UserRecord = {
    val record = new UserRecord(username, username + "First", username + "Last")
    record.id = nextId
    records += record
    record
  }

  override def fetch(username: String): UserRecord =
    getUser(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + username)
    }

  override def fetch(userId: RecordId): UserRecord =
    get(userId) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + userId.oid)
    }

  override def exists(username: String): Boolean =
    getUser(username).isDefined

  private def getUser(username: String): Option[UserRecord] = {
    records foreach {
      user => if (user.username == username) return Some(user)
    }
    None
  }


}
