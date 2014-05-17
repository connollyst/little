package com.quane.little.data.service

import com.mongodb.casbah.{MongoCollection, MongoClient}
import com.quane.little.data.model.{UserRecord, CodeSubcategory, RecordId, FunctionRecord}
import com.quane.little.language.{FunctionReference, FunctionDefinition}
import com.quane.little.data.repo.FunctionRepository
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}
import com.quane.little.data.model.CodeSubcategory.CodeSubcategory
import com.quane.little.language.util.Functions

/** A service for interacting with [[com.quane.little.data.model.FunctionRecord]].
  *
  * @author Sean Connolly
  */
trait FunctionService {

  def init(): Unit

  def exists(username: String, functionName: String): Boolean

  def findReferencesByUser(username: String): List[FunctionReference] =
    findDefinitionsByUser(username).map(_.asReference)

  def findDefinitionsByUser(username: String): List[FunctionDefinition] =
    findByUser(username).map(_.definition)

  def findByUser(username: String): List[FunctionRecord]

  def findReference(id: RecordId): FunctionReference =
    findDefinition(id).asReference

  def findDefinition(id: RecordId): FunctionDefinition

  def update(id: RecordId, fun: FunctionDefinition): FunctionRecord

  def insert(username: String, category: CodeSubcategory, fun: FunctionDefinition): FunctionRecord

  def isReservedSystemName(functionName: String): Boolean

}

class MongoFunctionService(implicit val bindingModule: BindingModule)
  extends FunctionService
  with Injectable {

  private val client = inject[MongoClient]
  private val userService = inject[UserService]

  /** Initialize the data source.
    */
  override def init(): Unit = {
    init(CodeSubcategory.Basic, Functions.blank)
    init(CodeSubcategory.Basic, Functions.printDirection)
    init(CodeSubcategory.Motion, Functions.move)
    init(CodeSubcategory.Motion, Functions.stop)
    init(CodeSubcategory.Motion, Functions.turn)
    init(CodeSubcategory.Motion, Functions.turnRelative)
    init(CodeSubcategory.Motion, Functions.voyage)
  }

  private def init(category: CodeSubcategory, function: FunctionDefinition) =
    if (!exists(UserService.SYSTEM_USERNAME, function.name)) {
      insert(UserService.SYSTEM_USERNAME, category, function)
    }

  override def exists(username: String, functionName: String): Boolean =
    exists(userService.fetch(username), functionName)

  private def exists(userId: RecordId, functionName: String): Boolean =
    exists(userService.fetch(userId), functionName)

  private def exists(user: UserRecord, functionName: String): Boolean =
    new FunctionRepository(collection).findByUser(user, functionName).isDefined

  override def findByUser(username: String): List[FunctionRecord] = {
    val repo = new FunctionRepository(collection)
    val systemFunctions = repo.findByUser(userService.fetch(UserService.SYSTEM_USERNAME))
    val userFunctions = repo.findByUser(userService.fetch(username))
    systemFunctions ::: userFunctions
  }

  override def findDefinition(id: RecordId): FunctionDefinition =
    new FunctionRepository(collection).find(id) match {
      case Some(record) => record.definition
      case None => throw new RuntimeException("No function definition for " + id)
    }

  override def update(id: RecordId, fun: FunctionDefinition): FunctionRecord = {
    val repo = new FunctionRepository(collection)
    repo.find(id) match {
      case Some(record) =>
        // TODO check if name is taken by another function
        record.definition = fun
        repo.update(record)
        record
      case None => throw new RuntimeException("No function definition for " + id)
    }
  }

  override def insert(username: String, category: CodeSubcategory, fun: FunctionDefinition): FunctionRecord = {
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
