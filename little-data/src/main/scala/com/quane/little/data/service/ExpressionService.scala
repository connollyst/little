package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import com.quane.little.language.data.Value

object ExpressionService {

  private var instance: Option[ExpressionService] = None

  def apply(): ExpressionService = {
    // Note: we provide this apply() just to be consistent with other services
    if (!instance.isDefined) {
      instance = Some(new MongoExpressionService)
    }
    instance.get
  }

  val Get = "_little_get"
  val Conditional = "_little_conditional"

  val All = immutable.List[String](
    Get,
    Conditional
  )

  val Names = immutable.Map[String, String](
    Get -> "get",
    Conditional -> "if/else"
  )

}

trait ExpressionService {

  def allExpressions: Iterable[Expression]

  def all: Iterable[PrimitiveRecord]

  def findExpression(id: RecordId): Expression

  def find(id: RecordId): PrimitiveRecord

  def createRecord(id: RecordId): PrimitiveRecord

}

/** A service for accessing primitive expressions of the little language.
  *
  * @author Sean Connolly
  */
class MongoExpressionService extends ExpressionService {

  // TODO erm.. this isn't related to MongoDB at all..?
  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  override def allExpressions: Iterable[Expression] =
    ExpressionService.All map {
      id => ExpressionFactory.create(id)
    }

  override def all: Iterable[PrimitiveRecord] =
    ExpressionService.All map {
      id => createRecord(new RecordId(id))
    }

  override def findExpression(id: RecordId): Expression = ExpressionFactory.create(id.oid)

  override def find(id: RecordId): PrimitiveRecord = createRecord(id)

  override def createRecord(id: RecordId): PrimitiveRecord =
    new PrimitiveRecord(id, ExpressionService.Names(id.oid), ExpressionFactory.create(id.oid))

}

/** A factory for creating an [[com.quane.little.language.Expression]] for a
  * primitive's id.
  *
  * @author Sean Connolly
  */
object ExpressionFactory {

  def create(id: String): Expression = {
    id match {
      case ExpressionService.Get => new GetStatement("")
      case ExpressionService.Conditional => new Conditional(new Evaluation(Value(1), Equals, Value(1)))
      case _ => throw new IllegalAccessException("No expression '" + id + "'")
    }
  }

}