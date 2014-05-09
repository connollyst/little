package com.quane.little.data.service

import com.mongodb.casbah.{MongoClient, MongoCollection}
import com.quane.little.data.model.{UserRecord, RecordId, FunctionRecord}
import com.quane.little.language.{FunctionReference, Functions, FunctionDefinition}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.CodeCategory
import com.quane.little.data.repo.FunctionRepository

/** A service for interacting with [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
object FunctionService {

  private var instance: Option[FunctionService] = None

  def apply(): FunctionService = {
    if (!instance.isDefined) {
      instance = Some(new FunctionService(MongoClient()))
    }
    instance.get
  }

  def apply(client: MongoClient): FunctionService = {
    instance = Some(new FunctionService(client))
    instance.get
  }

}

class FunctionService(client: MongoClient) {

  /** Initialize the data source.
    */
  def init(): Unit = {
    init(CodeCategory.Basic, Functions.blank)
    init(CodeCategory.Basic, Functions.printDirection)
    init(CodeCategory.Motion, Functions.move)
    init(CodeCategory.Motion, Functions.stop)
    init(CodeCategory.Motion, Functions.turn)
    init(CodeCategory.Motion, Functions.turnRelative)
    init(CodeCategory.Motion, Functions.voyage)
  }

  private def init(category: CodeCategory, function: FunctionDefinition) =
    if (!exists(UserService.SYSTEM_USERNAME, function.name)) {
      insert(UserService.SYSTEM_USERNAME, category, function)
    }

  def exists(username: String, functionName: String): Boolean =
    exists(UserService().fetch(username), functionName)

  private def exists(userId: RecordId, functionName: String): Boolean =
    exists(UserService().fetch(userId), functionName)

  private def exists(user: UserRecord, functionName: String): Boolean =
    new FunctionRepository(collection).findByUser(user, functionName).isDefined

  def findReferencesByUser(username: String): List[FunctionReference] =
    findDefinitionsByUser(username).map(_.asReference)

  def findDefinitionsByUser(username: String): List[FunctionDefinition] =
    findByUser(username).map(_.definition)

  def findByUser(username: String): List[FunctionRecord] = {
    val repo = new FunctionRepository(collection)
    val systemFunctions = repo.findByUser(UserService().fetch(UserService.SYSTEM_USERNAME))
    val userFunctions = repo.findByUser(UserService().fetch(username))
    systemFunctions ::: userFunctions
  }

  def findReference(id: RecordId): FunctionReference =
    findDefinition(id).asReference

  def findDefinition(id: RecordId): FunctionDefinition =
    new FunctionRepository(collection).find(id) match {
      case Some(record) => record.definition
      case None => throw new RuntimeException("No function definition for " + id)
    }

  def update(id: RecordId, fun: FunctionDefinition): FunctionRecord = {
    val repo = new FunctionRepository(collection)
    repo.find(id) match {
      case Some(record) =>
        if (exists(record.ownerId, fun.name)) {
          throw new IllegalArgumentException("Another function with the name '" + fun.name + "' already exists.")
        }
        record.definition = fun
        repo.update(record)
        record
      case None => throw new RuntimeException("No function definition for " + id)
    }
  }

  def insert(username: String, category: CodeCategory, fun: FunctionDefinition): FunctionRecord = {
    val user = UserService().fetch(username)
    if (exists(user, fun.name)) {
      throw new IllegalArgumentException("Function name taken '" + fun.name + "'")
    } else if (isReservedSystemName(fun.name)) {
      throw new IllegalArgumentException("Function name reserved '" + fun.name + "'")
    }
    val record = new FunctionRecord(user.id, category, fun)
    new FunctionRepository(collection).insert(record)
    record
  }

  def isReservedSystemName(functionName: String): Boolean =
    exists(UserService.SYSTEM_USERNAME, functionName)

  private def collection: MongoCollection = client("little")("functions")

}
