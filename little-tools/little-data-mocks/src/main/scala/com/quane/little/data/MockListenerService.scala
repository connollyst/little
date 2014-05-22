package com.quane.little.data

import com.quane.little.data.service.{UserService, ListenerService}
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.model.{ListenerRecord, RecordId}
import scala.collection.mutable.ListBuffer
import com.quane.little.language.event.EventListener
import scala.Some

/** A mock [[com.quane.little.data.service.ListenerService]] to be injected into
  * tests.
  *
  * @author Sean Connolly
  */
class MockListenerService(implicit val bindingModule: BindingModule)
  extends ListenerService
  with MockService[ListenerRecord]
  with Injectable {

  private val userService = inject[UserService]

  override def insert(username: String, listener: EventListener): ListenerRecord = {
    val owner = userService.fetch(username)
    insert(new ListenerRecord(owner.id, listener))
  }

  override def update(id: RecordId, listener: EventListener): ListenerRecord = {
    get(id) match {
      case Some(function) =>
        function.listener = listener
        function
      case None => throw new IllegalArgumentException("No listener: " + id.oid)
    }
  }

  override def findByUser(username: String): List[ListenerRecord] = {
    val userListeners = ListBuffer[ListenerRecord]()
    records foreach {
      listener => {
        val owner = userService.fetch(listener.ownerId)
        if (owner.username == username) {
          userListeners += listener
        }
      }
    }
    userListeners.toList
  }

  override def findListener(id: RecordId): EventListener =
    get(id) match {
      case Some(function) => function.listener
      case None => throw new IllegalArgumentException("No listener: " + id.oid)
    }

}
