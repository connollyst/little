package com.quane.little.data

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{ListenerId, ListenerRecord}
import com.quane.little.data.service.{ListenerService, UserService}
import com.quane.little.language.event.EventListener

import scala.collection.mutable.ListBuffer

/** A mock [[com.quane.little.data.service.ListenerService]] to be injected into
  * tests.
  *
  * @author Sean Connolly
  */
class MockListenerService(implicit val bindingModule: BindingModule)
  extends MockService[ListenerId, ListenerRecord](new IdFactory[ListenerId] {
    override def next = new ListenerId(increment)
  })
  with ListenerService
  with Injectable {

  private val userService = inject[UserService]

  override def insert(username: String, listener: EventListener): ListenerRecord = {
    val owner = userService.fetch(username)
    insert(new ListenerRecord(owner.id, listener))
  }

  override def update(id: ListenerId, listener: EventListener): ListenerRecord = {
    get(id) match {
      case Some(function) =>
        function.listener = listener
        function
      case None => throw new IllegalArgumentException("No listener: " + id.id)
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

  override def findListener(id: ListenerId): EventListener =
    get(id) match {
      case Some(function) => function.listener
      case None => throw new IllegalArgumentException("No listener: " + id.id)
    }

}
