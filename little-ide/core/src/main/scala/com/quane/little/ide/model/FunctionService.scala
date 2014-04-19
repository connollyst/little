package com.quane.little.ide.model

import com.mongodb.casbah.{MongoDB, MongoClient, MongoCollection}
import com.quane.little.data.FunctionRepository
import com.quane.little.data.model.{UserRecord, RecordId, FunctionRecord}
import com.quane.little.language.{FunctionReference, Functions, FunctionDefinition}

/** A service for interacting with [[com.quane.little.language.Functions]].
  *
  * @author Sean Connolly
  */
object FunctionService {

  val FunctionNames = List("blank", "move", "stop", "turn", "voyage", "print dir")

  def findReferencesByUser(username: String): List[FunctionReference] =
    findDefinitionsByUser(username).map(definition => definition.asReference)

  def findReferencesByUser(user: UserRecord): List[FunctionReference] =
    findDefinitionsByUser(user).map(definition => definition.asReference)

  def findDefinitionsByUser(username: String): List[FunctionDefinition] =
    findDefinitionsByUser(UserService.fetch(username))

  def findDefinitionsByUser(user: UserRecord): List[FunctionDefinition] = {
    println("Fetching all functions for " + user)
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionRepository(collection)
    repo.findByUser(user).map(function => function.definition)
  }

  def findReference(name: String): FunctionReference = findDefinition(name).asReference

  def findDefinition(name: String): FunctionDefinition = {
    name match {
      case "blank" => Functions.blank
      case "move" => Functions.move
      case "stop" => Functions.stop
      case "turn" => Functions.turn
      case "voyage" => Functions.voyage
      case "print dir" => Functions.printDirection
      case _ => throw new IllegalArgumentException("Unknown function: '" + name + "'")
    }
  }

  def upsert(username: String, id: Option[RecordId], fun: FunctionDefinition): FunctionRecord = {
    println("Saving " + fun + " for " + username)
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionRepository(collection)
    repo.find(id) match {
      case Some(record) => update(id.get, fun)
      case None => insert(username, fun)
    }
  }

  def update(id: RecordId, fun: FunctionDefinition): FunctionRecord = {
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionRepository(collection)
    repo.find(id) match {
      case Some(record) =>
        record.definition = fun
        repo.update(record)
        record
      case None => throw new RuntimeException("No FunctionDefinition for " + id)
    }
  }

  def insert(username: String, fun: FunctionDefinition): FunctionRecord = {
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionRepository(collection)
    val user = UserService.fetch(username)
    val record = new FunctionRecord(user.id, fun)
    repo.insert(record)
    record
  }

  private def mongoClient: MongoClient = MongoClient()

  private def mongoDB(db: String): MongoDB = mongoClient(db)

  private def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

}
