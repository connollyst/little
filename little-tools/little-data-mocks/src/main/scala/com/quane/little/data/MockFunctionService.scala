package com.quane.little.data

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{FunctionRecord, RecordId}
import com.quane.little.data.service.{FunctionService, UserService}
import com.quane.little.language.FunctionDefinition

import scala.collection.mutable.ListBuffer

/** A mock [[com.quane.little.data.service.FunctionService]] to be injected into
  * tests.
  *
  * @author Sean Connolly
  */
class MockFunctionService(implicit val bindingModule: BindingModule)
  extends FunctionService
  with MockService[FunctionRecord]
  with Injectable {

  private val userService = inject[UserService]

  override def insert(username: String, category: CodeCategory, fun: FunctionDefinition): FunctionRecord = {
    val owner = userService.fetch(username)
    insert(new FunctionRecord(owner.id, category, fun))
  }

  override def update(id: RecordId, fun: FunctionDefinition): FunctionRecord = {
    get(id) match {
      case Some(function) =>
        function.definition = fun
        function
      case None => throw new IllegalArgumentException("No function: " + id.oid)
    }
  }

  override def findByUser(username: String): List[FunctionRecord] = {
    val userFunctions = ListBuffer[FunctionRecord]()
    records foreach {
      function => {
        val owner = userService.fetch(function.ownerId)
        if (owner.username == username) {
          userFunctions += function
        }
      }
    }
    userFunctions.toList
  }

  override def findDefinition(id: RecordId): FunctionDefinition =
    get(id) match {
      case Some(function) => function.definition
      case None => throw new IllegalArgumentException("No function: " + id.oid)
    }

  override def findDefinition(username: String, functionName: String): FunctionDefinition = {
    findByUser(username) foreach {
      function =>
        if (function.definition.name == functionName) {
          return function.definition
        }
    }
    throw new IllegalArgumentException("No function " + functionName + " for user " + username)
  }

  override def exists(username: String, functionName: String): Boolean =
    getFunction(username, functionName).isDefined

  override def isReservedSystemName(functionName: String): Boolean =
    getFunction(UserService.SYSTEM_USERNAME, functionName).isDefined

  private def getFunction(username: String, functionName: String): Option[FunctionRecord] = {
    records foreach {
      function => if (function.definition.name == functionName) {
        val owner = userService.fetch(function.ownerId)
        if (owner.username == username) {
          return Some(function)
        }
      }
    }
    None
  }

}
