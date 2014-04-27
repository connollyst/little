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

  /** Initialize the data source.
    */
  def init(): Unit = {
    UserService.init()
    init(Functions.blank)
    init(Functions.move)
    init(Functions.stop)
    init(Functions.turn)
    init(Functions.voyage)
    init(Functions.printDirection)
  }

  private def init(function: FunctionDefinition) =
    if (!exists(UserService.SYSTEM_USERNAME, function.name)) {
      insert(UserService.SYSTEM_USERNAME, function)
    }

  def exists(username: String, functionName: String): Boolean =
    exists(UserService.fetch(username), functionName)

  private def exists(user: UserRecord, functionName: String): Boolean =
    repo.findByUser(user, functionName).isDefined

  def findReferencesByUser(username: String): List[FunctionReference] =
    findDefinitionsByUser(username).map(_.asReference)

  def findDefinitionsByUser(username: String): List[FunctionDefinition] =
    findByUser(username).map(_.definition)

  def findByUser(username: String): List[FunctionRecord] = {
    val systemFunctions = repo.findByUser(UserService.fetch(UserService.SYSTEM_USERNAME))
    val userFunctions = repo.findByUser(UserService.fetch(username))
    systemFunctions ::: userFunctions
  }

  def findReference(id: RecordId): FunctionReference =
    findDefinition(id).asReference

  def findDefinition(id: RecordId): FunctionDefinition =
    repo.find(id) match {
      case Some(record) => record.definition
      case None => throw new RuntimeException("No function definition for " + id)
    }

  def upsert(username: String, id: Option[RecordId], fun: FunctionDefinition): FunctionRecord =
    repo.find(id) match {
      case Some(record) => update(id.get, fun)
      case None => insert(username, fun)
    }

  def update(id: RecordId, fun: FunctionDefinition): FunctionRecord =
    repo.find(id) match {
      case Some(record) =>
        record.definition = fun
        repo.update(record)
        record
      case None => throw new RuntimeException("No function definition for " + id)
    }

  def insert(username: String, fun: FunctionDefinition): FunctionRecord = {
    val user = UserService.fetch(username)
    if (exists(user, fun.name)) {
      throw new IllegalArgumentException("Function name taken '" + fun.name + "'")
    } else if (isReservedSystemName(fun.name)) {
      throw new IllegalArgumentException("Function name reserved '" + fun.name + "'")
    }
    val record = new FunctionRecord(user.id, fun)
    repo.insert(record)
    record
  }

  def isReservedSystemName(functionName: String): Boolean =
    exists(UserService.SYSTEM_USERNAME, functionName)

  private def mongoClient: MongoClient = MongoClient()

  private def mongoDB(db: String): MongoDB = mongoClient(db)

  private def mongoCollection(db: String, name: String): MongoCollection = mongoDB(db)(name)

  private def mongoCollection: MongoCollection = mongoCollection("little", "functions")

  private def repo: FunctionRepository = new FunctionRepository(mongoCollection)

}
