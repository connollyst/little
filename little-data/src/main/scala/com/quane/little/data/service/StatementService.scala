package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import scala.Some
import com.quane.little.language.data.Value

object StatementService {

  private var instance: Option[StatementService] = None

  def apply(): StatementService = {
    // Note: we provide this apply() just to be consistent with other services
    if (!instance.isDefined) {
      instance = Some(new StatementService)
    }
    instance.get
  }

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

/** A service for accessing statements of the little language.
  *
  * @author Sean Connolly
  */
class StatementService {

  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  def allStatement: Iterable[EvaluableCode] =
    StatementService.All map {
      id => StatementFactory.create(id)
    }

  def all: Iterable[PrimitiveRecord] =
    StatementService.All map {
      id => createRecord(new RecordId(id))
    }

  def findStatement(id: RecordId): EvaluableCode = StatementFactory.create(id.oid)

  def find(id: RecordId): PrimitiveRecord = createRecord(id)

  def createRecord(id: RecordId): PrimitiveRecord =
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
      case StatementService.Set => new SetStatement("", Value(""))
      case StatementService.Print => new PrintStatement(Value(""))
      case _ => throw new IllegalAccessException("No statement '" + id + "'")
    }
  }

}