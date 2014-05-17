package com.quane.little.data.service

import com.quane.little.data.model.{PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import com.quane.little.language.data.Value
import com.quane.little.language.math.{Multiplication, Division, Subtraction, Addition}

object ExpressionService {

  val Get = "_little_get"
  val Conditional = "_little_conditional"
  // Math Expressions
  val Addition = "_little_math_addition"
  val Subtraction = "_little_math_subtraction"
  val Multiplication = "_little_math_multiplication"
  val Division = "_little_math_division"

  val All = immutable.List[String](
    Get,
    Conditional,
    Addition,
    Subtraction,
    Multiplication,
    Division
  )

  val Names = immutable.Map[String, String](
    Get -> "get",
    Conditional -> "if/else",
    Addition -> "+",
    Subtraction -> "-",
    Multiplication -> "*",
    Division -> "/"
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
class BasicExpressionService extends ExpressionService {

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
      case ExpressionService.Get => new Getter("")
      case ExpressionService.Conditional => new Conditional(new Evaluation(Value(1), Equals, Value(1)))
      case ExpressionService.Addition => new Addition(Value(1), Value(1))
      case ExpressionService.Subtraction => new Subtraction(Value(1), Value(1))
      case ExpressionService.Multiplication => new Multiplication(Value(1), Value(1))
      case ExpressionService.Division => new Division(Value(1), Value(1))
      case _ => throw new IllegalAccessException("No expression '" + id + "'")
    }
  }

}