package com.quane.little.data

import com.quane.little.data.service.{UserService, FunctionService}
import com.quane.little.data.model.{FunctionRecord, RecordId}
import com.quane.little.language.FunctionDefinition
import com.quane.little.data.model.CodeCategory.CodeCategory
import scala.collection.mutable.ListBuffer
import com.escalatesoft.subcut.inject.{Injectable, BindingModule}

/** A mock implementation of the [[com.quane.little.data.service.FunctionService]]
  * trait to be injected into tests. It's behavior emulates that of the real
  * implementation, letting us focus on higher level logic in test.
  *
  * @author Sean Connolly
  */
class MockFunctionService(implicit val bindingModule: BindingModule)
  extends FunctionService
  with Injectable {

  private val userService = inject[UserService]
  private val functions = ListBuffer[FunctionRecord]()
  private var idSequence = 1

  /** Initialize the mock function service: clear all records.
    */
  override def init(): Unit = {
    functions.clear()
    idSequence = 1
  }

  private def nextId: RecordId = {
    idSequence += 1
    new RecordId(idSequence.toString)
  }

  override def findByUser(username: String): List[FunctionRecord] = {
    val userFunctions = ListBuffer[FunctionRecord]()
    functions foreach {
      function => {
        val owner = userService.fetch(function.ownerId)
        if (owner.username == username) {
          userFunctions += function
        }
      }
    }
    userFunctions.toList
  }

  override def update(id: RecordId, fun: FunctionDefinition): FunctionRecord = {
    getFunction(id) match {
      case Some(function) =>
        function.definition = fun
        function
      case None => throw new IllegalArgumentException("No function: " + id.oid)
    }
  }

  override def insert(username: String, category: CodeCategory, fun: FunctionDefinition): FunctionRecord = {
    val owner = userService.fetch(username)
    val record = new FunctionRecord(owner.id, category, fun)
    record.id = nextId
    functions += record
    record
  }

  override def isReservedSystemName(functionName: String): Boolean =
    getFunction(UserService.SYSTEM_USERNAME, functionName).isDefined

  override def findDefinition(id: RecordId): FunctionDefinition =
    getFunction(id) match {
      case Some(function) => function.definition
      case None => throw new IllegalArgumentException("No function: " + id.oid)
    }

  override def exists(username: String, functionName: String): Boolean =
    getFunction(username, functionName).isDefined

  private def getFunction(functionId: RecordId): Option[FunctionRecord] = {
    functions foreach {
      function => if (function.id == functionId) return Some(function)
    }
    None
  }

  private def getFunction(username: String, functionName: String): Option[FunctionRecord] = {
    functions foreach {
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
