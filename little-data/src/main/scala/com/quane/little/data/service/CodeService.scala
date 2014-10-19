package com.quane.little.data.service

import com.escalatesoft.subcut.inject.{BindingModule, Injectable}
import com.quane.little.data.model.{PrimitiveId, CodeRecord, FunctionId, Id}
import com.quane.little.language._
import com.quane.little.language.exceptions.NotImplementedError

trait CodeService {

  def allForUser(username: String): Iterable[Code]

  def allRecordsForUser(username: String): Iterable[CodeRecord]

  def find(id: Id): Option[Code]

  def findRecord(id: Id): Option[CodeRecord]

  def createRecordForUser(username: String, id: Id): CodeRecord

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
      _.asCodeRecord
    }
  }

  override def find(id: Id): Option[Code] = {
    id match {
      case c: PrimitiveId => Some(primitiveService.find(c))
      case f: FunctionId => Some(functionService.findReference(f))
      case _ => throw new IllegalArgumentException(
        id.getClass + " is invalid, expected " + classOf[PrimitiveId] + " or " + classOf[FunctionId]
      )
    }
  }

  override def findRecord(id: Id): Option[CodeRecord] =
    throw new NotImplementedError("Not yet implemented.")

  override def createRecordForUser(username: String, id: Id): CodeRecord =
    throw new NotImplementedError("Not yet implemented.")

}