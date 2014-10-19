package com.quane.little.data.service

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.mongodb.casbah.{MongoClient, MongoCollection}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model._
import com.quane.little.data.repo.FunctionRepository
import com.quane.little.language.util.Functions
import com.quane.little.language.{FunctionDefinition, FunctionReference}

/** A service for interacting with [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
trait FunctionService {

  def init(): Unit

  def exists(id: FunctionId): Boolean

  def exists(username: String, functionName: String): Boolean

  def findReferencesByUser(username: String): Iterable[FunctionReference] =
    findDefinitionsByUser(username).map(_.asReference)

  def findDefinitionsByUser(username: String): Iterable[FunctionDefinition] =
    findByUser(username).map(_.definition)

  def findByUser(username: String): Iterable[FunctionRecord]

  def findReference(id: FunctionId): FunctionReference =
    findDefinition(id).asReference

  def findDefinition(id: FunctionId): FunctionDefinition

  def findDefinition(username: String, functionName: String): FunctionDefinition

  def update(id: FunctionId, category: CodeCategory, fun: FunctionDefinition): FunctionRecord

  def insert(username: String, category: CodeCategory, fun: FunctionDefinition): FunctionRecord

  def isReservedSystemName(functionName: String): Boolean

}

class MongoFunctionService(implicit val bindingModule: BindingModule)
  extends FunctionService with Injectable {

  private val client = inject[MongoClient]
  private val userService = inject[UserService]

  /** Initialize the data source.
    */
  override def init(): Unit = {
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

  override def exists(username: String, functionName: String): Boolean =
    exists(userService.fetch(username), functionName)

  private def exists(userId: UserId, functionName: String): Boolean =
    exists(userService.fetch(userId), functionName)

  override def exists(id: FunctionId): Boolean =
    new FunctionRepository(collection).find(id).isDefined

  private def exists(user: UserRecord, functionName: String): Boolean =
    new FunctionRepository(collection).findByUser(user, functionName).isDefined

  override def findByUser(username: String): List[FunctionRecord] = {
    val repo = new FunctionRepository(collection)
    val systemFunctions = repo.findByUser(userService.fetch(UserService.SYSTEM_USERNAME))
    val userFunctions = repo.findByUser(userService.fetch(username))
    systemFunctions ::: userFunctions
  }

  override def findDefinition(id: FunctionId): FunctionDefinition =
    new FunctionRepository(collection).find(id) match {
      case Some(record) => record.definition
      case None => throw new RuntimeException("No function definition for " + id)
    }

  override def findDefinition(username: String, functionName: String): FunctionDefinition = {
    findByUser(username) foreach {
      function => if (function.definition.name == functionName) return function.definition
    }
    throw new RuntimeException("No function definition '" + functionName + "' for user '" + username + "'")
  }

  override def update(id: FunctionId, category: CodeCategory, fun: FunctionDefinition): FunctionRecord = {
    val repo = new FunctionRepository(collection)
    repo.find(id) match {
      case Some(record) =>
        // TODO check if name is taken by another function
        record.definition = fun
        record.category = category
        repo.update(record)
        record
      case None => throw new RuntimeException("No function definition for " + id)
    }
  }

  override def insert(username: String, category: CodeCategory, fun: FunctionDefinition): FunctionRecord = {
    val user = userService.fetch(username)
    if (exists(user, fun.name)) {
      throw new IllegalArgumentException("Function name taken '" + fun.name + "'")
    } else if (isReservedSystemName(fun.name)) {
      throw new IllegalArgumentException("Function name reserved '" + fun.name + "'")
    }
    val record = new FunctionRecord(user.id, category, fun)
    new FunctionRepository(collection).insert(record)
    record
  }

  override def isReservedSystemName(functionName: String): Boolean =
    exists(UserService.SYSTEM_USERNAME, functionName)

  private def collection: MongoCollection = client("little")("functions")

}
