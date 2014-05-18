package com.quane.little.data.service

import com.quane.little.data.model.{CodeCategory, PrimitiveRecord, RecordId}
import com.quane.little.language._
import scala.collection.immutable
import com.quane.little.language.data.Value
import com.quane.little.language.math.{Multiplication, Division, Subtraction, Addition}
import com.quane.little.data.model.CodeCategory.CodeCategory

object ExpressionService {

  val Get = "_little_get"
  val Conditional = "_little_conditional"
  // Math
  val Addition = "_little_math_addition"
  val Subtraction = "_little_math_subtraction"
  val Multiplication = "_little_math_multiplication"
  val Division = "_little_math_division"
  // Logic
  val Equals = "_little_logic_eq"
  val NotEquals = "_little_logic_ne"
  val LessThan = "_little_logic_lt"
  val GreaterThan = "_little_logic_gt"
  val And = "_little_logic_and"
  val Or = "_little_logic_or"

  val All = immutable.List[String](
    Get,
    Conditional,
    // Math
    Addition,
    Subtraction,
    Multiplication,
    Division,
    // Logic
    Equals,
    NotEquals,
    LessThan,
    GreaterThan,
    And,
    Or
  )
  val Names = immutable.Map[String, String](
    Get -> "get",
    Conditional -> "if/else",
    // Math
    Addition -> "plus",
    Subtraction -> "minus",
    Multiplication -> "multiplied by",
    Division -> "divided by",
    // Logic
    Equals -> "equals",
    NotEquals -> "not equals",
    LessThan -> "less than",
    GreaterThan -> "greater than",
    And -> "and",
    Or -> "or"
  )
  val Categories = immutable.Map[String, CodeCategory](
    Get -> CodeCategory.Basic,
    Conditional -> CodeCategory.Basic,
    // Math
    Addition -> CodeCategory.Math,
    Subtraction -> CodeCategory.Math,
    Multiplication -> CodeCategory.Math,
    Division -> CodeCategory.Math,
    // Logic
    Equals -> CodeCategory.Math,
    NotEquals -> CodeCategory.Math,
    LessThan -> CodeCategory.Math,
    GreaterThan -> CodeCategory.Math,
    And -> CodeCategory.Math,
    Or -> CodeCategory.Math
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
    new PrimitiveRecord(
      id,
      ExpressionService.Categories(id.oid),
      ExpressionService.Names(id.oid),
      ExpressionFactory.create(id.oid)
    )

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
      case ExpressionService.Conditional => new Conditional(new Logic(Value(1), LogicOperation.Equals, Value(1)))
      // Math
      case ExpressionService.Addition => new Addition(Value(1), Value(1))
      case ExpressionService.Subtraction => new Subtraction(Value(1), Value(1))
      case ExpressionService.Multiplication => new Multiplication(Value(1), Value(1))
      case ExpressionService.Division => new Division(Value(1), Value(1))
      // Logic
      case ExpressionService.Equals => new Logic(Value(1), LogicOperation.Equals, Value(1))
      case ExpressionService.NotEquals => new Logic(Value(1), LogicOperation.NotEquals, Value(1))
      case ExpressionService.LessThan => new Logic(Value(1), LogicOperation.LessThan, Value(1))
      case ExpressionService.GreaterThan => new Logic(Value(1), LogicOperation.GreaterThan, Value(1))
      case ExpressionService.And => new Logic(Value(1), LogicOperation.And, Value(1))
      case ExpressionService.Or => new Logic(Value(1), LogicOperation.Or, Value(1))
      case _ => throw new IllegalAccessException("No expression '" + id + "'")
    }
  }

}