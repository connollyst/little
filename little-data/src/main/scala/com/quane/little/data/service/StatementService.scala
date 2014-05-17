package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import com.quane.little.language.data.Value

object StatementService {

  val Set = "_little_set"
  val Print = "_little_print"

  val All = immutable.List[String](
    Set,
    Print
  )

  val Names = immutable.Map[String, String](
    Set -> "set",
    Print -> "print"
  )

}

trait StatementService {

  def allStatement: Iterable[EvaluableCode]

  def all: Iterable[PrimitiveRecord]

  def findStatement(id: RecordId): EvaluableCode

  def find(id: RecordId): PrimitiveRecord

  def createRecord(id: RecordId): PrimitiveRecord

}

/** A service for accessing primitive statements of the little language.
  *
  * @author Sean Connolly
  */
class BasicStatementService extends StatementService {

  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  override def allStatement: Iterable[EvaluableCode] =
    StatementService.All map {
      id => StatementFactory.create(id)
    }

  override def all: Iterable[PrimitiveRecord] =
    StatementService.All map {
      id => createRecord(new RecordId(id))
    }

  override def findStatement(id: RecordId): EvaluableCode = StatementFactory.create(id.oid)

  override def find(id: RecordId): PrimitiveRecord = createRecord(id)

  override def createRecord(id: RecordId): PrimitiveRecord =
    new PrimitiveRecord(id, StatementService.Names(id.oid), StatementFactory.create(id.oid))

}

/** A factory for creating an [[com.quane.little.language.Expression]] for a
  * primitive's id.
  *
  * @author Sean Connolly
  */
object StatementFactory {

  def create(id: String): EvaluableCode = {
    id match {
      case StatementService.Set => new Setter("", Value(""))
      case StatementService.Print => new Printer(Value(""))
      case _ => throw new IllegalAccessException("No statement '" + id + "'")
    }
  }

}