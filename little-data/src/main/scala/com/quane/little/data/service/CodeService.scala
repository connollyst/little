package com.quane.little.data.service

import com.quane.little.data.model.{CodeRecord, RecordId}
import com.quane.little.language._
import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.language.exceptions.NotImplementedError

trait CodeService {

  def allForUser(username: String): Iterable[Code]

  def allRecordsForUser(username: String): Iterable[CodeRecord]

  def findForUser(username: String, id: RecordId): Option[Code]

  def findRecordForUser(username: String, id: RecordId): Option[CodeRecord]

  def createRecordForUser(username: String, id: RecordId): CodeRecord

}

/** A service for accessing code.
  *
  * @author Sean Connolly
  */
class BasicCodeService(implicit val bindingModule: BindingModule)
  extends CodeService with Injectable {

  val primitiveService = inject[PrimitiveService]
  val functionService = inject[FunctionService]

  override def allForUser(username: String): Iterable[Code] =
    allRecordsForUser(username).map {
      _.code
    }

  override def allRecordsForUser(username: String): Iterable[CodeRecord] = {
    primitiveService.allRecords ++ functionService.findByUser(username).map {
      fun => new CodeRecord(fun.id, fun.category, fun.definition.name, fun.definition.asReference)
    }
  }

  override def findForUser(username: String, id: RecordId): Option[Code] =
    if (primitiveService.exists(id)) {
      Some(primitiveService.find(id))
    } else if (functionService.exists(id)) {
      Some(functionService.findReference(id))
    } else {
      None
    }

  override def findRecordForUser(username: String, id: RecordId): Option[CodeRecord] =
    throw new NotImplementedError("Not yet implemented.")

  override def createRecordForUser(username: String, id: RecordId): CodeRecord =
    throw new NotImplementedError("Not yet implemented.")

}