package com.quane.little.ide.model

import com.mongodb.casbah.{MongoCollection, MongoDB, MongoClient}
import com.quane.little.data.FunctionDefinitionRepository
import com.quane.little.data.model.{RecordID, FunctionDefinitionRecord}
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, Functions, FunctionDefinition}

/** A service for interacting with [[com.quane.little.language.Functions]].
  *
  * @author Sean Connolly
  */
object FunctionService {

  val FunctionNames = List("blank", "move", "stop", "turn", "voyage", "print dir")

  def upsert(username: String, id: Option[RecordID], fun: FunctionDefinition): FunctionDefinitionRecord = {
    println("Saving " + fun + " for " + username)
    new FunctionDefinitionRecord(username, fun)
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionDefinitionRepository(collection)
    repo.find(id) match {
      case Some(record) => update(id.get, fun)
      case None => insert(username, fun)
    }
  }

  def update(id: RecordID, fun: FunctionDefinition): FunctionDefinitionRecord = {
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionDefinitionRepository(collection)
    repo.find(id) match {
      case Some(record) => println("Updating " + id)
      case None => throw new RuntimeException("No FunctionDefinition for " + id)
    }
    repo.find(id).get
  }

  def insert(username: String, fun: FunctionDefinition): FunctionDefinitionRecord = {
    val collection = mongoCollection("little", "functions")
    val repo = new FunctionDefinitionRepository(collection)
    val record = new FunctionDefinitionRecord(username, fun)
    repo.insert(record)
    record
  }

  def fetchReference(name: String): FunctionReference = {
    val definition = fetchDefinition(name)
    val reference = new FunctionReference(definition.name)
    definition.params foreach {
      param =>
      // TODO parameters should have default values
        reference.addArg(param.name, Value(""))
    }
    reference
  }

  def fetchDefinition(name: String): FunctionDefinition = {
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

  private def mongoClient: MongoClient = MongoClient()

  private def mongoDB(db: String): MongoDB = mongoClient(db)

  private def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

}
