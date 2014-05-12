package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.model.{RecordId, ListenerRecord}
import com.quane.little.data.repo.ListenerRepository
import com.quane.little.language.event.{Event, EventListener}
import com.quane.little.language.FunctionReference
import com.quane.little.language.data.Value
import scala.Some

/** A service for interacting with [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
object ListenerService {

  private var instance: Option[ListenerService] = None

  def apply(): ListenerService = {
    if (!instance.isDefined) {
      instance = Some(new ListenerService(MongoClient()))
    }
    instance.get
  }

  def apply(client: MongoClient): ListenerService = {
    instance = Some(new ListenerService(client))
    instance.get
  }

}

class ListenerService(client: MongoClient) {

  def init(): Unit = {
    // TODO this is all temporary!
    if (findListenersByUser("connollyst").nonEmpty) {
      return
    }
    val user = UserService().fetch("connollyst")
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

  def findListener(id: RecordId): EventListener =
    repository.find(id) match {
      case Some(record) => record.listener
      case None => throw new RuntimeException("No event listener for " + id)
    }

  def findListenersByUser(username: String): List[EventListener] =
    findByUser(username).map(_.listener)

  def findByUser(username: String): List[ListenerRecord] =
    repository.findByUser(UserService().fetch(username))

  def update(id: RecordId, listener: EventListener): ListenerRecord = {
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

  def insert(username: String, listener: EventListener): ListenerRecord = {
    val user = UserService().fetch(username)
    val record = new ListenerRecord(user.id, listener)
    repository.insert(record)
    record
  }

  private def repository: ListenerRepository = new ListenerRepository(collection)

  private def collection: MongoCollection = client("little")("listeners")

}
