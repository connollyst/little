package com.quane.little.data

import com.quane.little.data.model.{UserId, UserRecord}
import com.quane.little.data.service.UserService

/** A mock [[com.quane.little.data.service.UserService]] to be injected into
  * tests.
  *
  * @author Sean Connolly
  */
class MockUserService
  extends MockService[UserId, UserRecord](new IdFactory[UserId] {
    override def next = new UserId(increment)
  })
  with UserService {

  override def upsert(username: String): UserRecord =
    getUser(username) match {
      case Some(u) => u
      case None => insert(username)
    }

  private def insert(username: String): UserRecord =
    insert(new UserRecord(username, username + "First", username + "Last"))

  override def fetch(username: String): UserRecord =
    getUser(username) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + username)
    }

  override def fetch(userId: UserId): UserRecord =
    get(userId) match {
      case Some(user) => user
      case None => throw new IllegalArgumentException("No user: " + userId.id)
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
