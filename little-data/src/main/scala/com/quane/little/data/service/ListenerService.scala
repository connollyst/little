package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.model.{ListenerId, Id, ListenerRecord}
import com.quane.little.data.repo.ListenerRepository
import com.quane.little.language.event.{Event, EventListener}
import com.quane.little.language.FunctionReference
import com.quane.little.language.data.Value
import scala.Some
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** A service for interacting with [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
trait ListenerService {

  def init(): Unit

  def findListener(id: ListenerId): EventListener

  def findListenersByUser(username: String): List[EventListener] =
    findByUser(username).map(_.listener)

  def findByUser(username: String): List[ListenerRecord]

  def update(id: ListenerId, listener: EventListener): ListenerRecord

  def insert(username: String, listener: EventListener): ListenerRecord

}

class MongoListenerService(implicit val bindingModule: BindingModule)
  extends ListenerService
  with Injectable {

  private val client = inject[MongoClient]
  private val userService = inject[UserService]

  override def init(): Unit = {
    // TODO this is all temporary!
    if (findListenersByUser("connollyst").nonEmpty) {
      return
    }
    val user = userService.fetch("connollyst")
    val repo = repository
    repo.insert(
      new ListenerRecord(
        user.id,
        new EventListener(Event.OnSpawn).addStep(
          new FunctionReference("move").addArg("speed", Value(5))
        )
      )
    )
    repo.insert(
      new ListenerRecord(
        user.id,
        new EventListener(Event.OnContact).addStep(
          new FunctionReference("turnRelative")
            .addArg("degrees", Value(260))
        )
      )
    )
    repo.insert(
      new ListenerRecord(
        user.id,
        new EventListener(Event.OnFoodConsumed).addStep(
          new FunctionReference("turn")
        )
      )
    )
  }

  override def findListener(id: ListenerId): EventListener =
    repository.find(id) match {
      case Some(record) => record.listener
      case None => throw new RuntimeException("No event listener for " + id)
    }

  override def findByUser(username: String): List[ListenerRecord] =
    repository.findByUser(userService.fetch(username))

  override def update(id: ListenerId, listener: EventListener): ListenerRecord = {
    val repo = repository
    repo.find(id) match {
      case Some(record) =>
        // TODO check if name is taken by another function
        record.listener = listener
        repo.update(record)
        record
      case None => throw new RuntimeException("No event listener for " + id)
    }
  }

  override def insert(username: String, listener: EventListener): ListenerRecord = {
    val user = userService.fetch(username)
    val record = new ListenerRecord(user.id, listener)
    repository.insert(record)
    record
  }

  private def repository: ListenerRepository = new ListenerRepository(collection)

  private def collection: MongoCollection = client("little")("listeners")

}
