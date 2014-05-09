package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.model.ListenerRecord
import com.quane.little.data.repo.ListenerRepository
import com.quane.little.language.event.{LittleEvent, EventListener}
import com.quane.little.language.FunctionReference
import com.quane.little.language.data.Value

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
        new EventListener(
          LittleEvent.OnSpawn,
          new FunctionReference("move")
            .addArg("speed", Value(5))
        )
      )
    )
    repo.insert(
      new ListenerRecord(
        user.id,
        new EventListener(
          LittleEvent.OnContact,
          new FunctionReference("turnRelative")
            .addArg("degrees", Value(260))
        )
      )
    )
    repo.insert(
      new ListenerRecord(
        user.id,
        new EventListener(
          LittleEvent.OnFoodConsumed,
          new FunctionReference("turn")
        )
      )
    )
  }

  def findListenersByUser(username: String): List[EventListener] =
    findByUser(username).map(_.listener)

  def findByUser(username: String): List[ListenerRecord] =
    repository.findByUser(UserService().fetch(username))

  private def repository: ListenerRepository = new ListenerRepository(collection)

  private def collection: MongoCollection = client("little")("listeners")

}
