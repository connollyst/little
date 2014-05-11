package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import scala.Some
import com.quane.little.language.data.Value

object ExpressionService {

  private var instance: Option[ExpressionService] = None

  def apply(): ExpressionService = {
    // Note: we provide this apply() just to be consistent with other services
    if (!instance.isDefined) {
      instance = Some(new ExpressionService)
    }
    instance.get
  }

  val PrimitiveGet = "_little_get"
  val PrimitiveConditional = "_little_conditional"

  val Primitives = immutable.List[String](
    PrimitiveGet,
    PrimitiveConditional
  )

  val PrimitivesNames = immutable.Map[String, String](
    PrimitiveGet -> "get",
    PrimitiveConditional -> "if/else"
  )

}

/** A service for accessing primitives of the little language.
  *
  * @author Sean Connolly
  */
class ExpressionService {

  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  def allExpressions: Iterable[Expression] =
    ExpressionService.Primitives map {
      id => ExpressionFactory.create(id)
    }

  def all: Iterable[PrimitiveRecord] =
    ExpressionService.Primitives map {
      id => createRecord(new RecordId(id))
    }

  def findExpression(id: RecordId): Expression = ExpressionFactory.create(id.oid)

  def find(id: RecordId): PrimitiveRecord = createRecord(id)

  def createRecord(id: RecordId): PrimitiveRecord =
    new PrimitiveRecord(id, ExpressionService.PrimitivesNames(id.oid), ExpressionFactory.create(id.oid))
}

/** A factory for creating an [[com.quane.little.language.Expression]] for a
  * primitive's id.
  *
  * @author Sean Connolly
  */
object ExpressionFactory {

  def create(id: String): Expression = {
    id match {
      case ExpressionService.PrimitiveGet => new GetStatement("")
      case ExpressionService.PrimitiveConditional => new Conditional(new Evaluation(Value(1), Equals, Value(1)))
      case _ => throw new IllegalAccessException("No expression '" + id + "'")
    }
  }

}