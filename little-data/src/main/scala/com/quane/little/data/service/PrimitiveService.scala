package com.quane.little.data.service

import com.quane.little.data.model.CodeCategory.CodeCategory
import com.quane.little.data.model.{CodeCategory, CodeRecord, RecordId}
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.language.math.{Division, Multiplication, Subtraction, Addition}

import scala.collection.immutable

object PrimitiveService {

  val Get = "_little_get"
  val Set = "_little_set"
  val Print = "_little_print"
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
    Set,
    Print,
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
    Set -> "set",
    Print -> "print",
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
    Set -> CodeCategory.Basic,
    Print -> CodeCategory.Basic,
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

trait PrimitiveService {

  def all: Iterable[Code]

  def allRecords: Iterable[CodeRecord]

  def exists(id: RecordId): Boolean

  def find(id: RecordId): Code

  def findRecord(id: RecordId): CodeRecord

  def createRecord(id: RecordId): CodeRecord

}

/** A service for accessing primitive expressions of the little language.
  *
  * @author Sean Connolly
  */
class BasicPrimitiveService extends PrimitiveService {

  // TODO we are sort of abusing the RecordId here, let's abstract out an 'id'

  override def all: Iterable[Code] =
    PrimitiveService.All map {
      id => PrimitiveFactory.create(id)
    }

  override def allRecords: Iterable[CodeRecord] =
    PrimitiveService.All map {
      id => createRecord(new RecordId(id))
    }

  override def exists(id: RecordId): Boolean = PrimitiveService.All.contains(id.oid)

  override def find(id: RecordId): Code = PrimitiveFactory.create(id.oid)

  override def findRecord(id: RecordId): CodeRecord = createRecord(id)

  override def createRecord(id: RecordId): CodeRecord =
    new CodeRecord(
      id,
      PrimitiveService.Categories(id.oid),
      PrimitiveService.Names(id.oid),
      PrimitiveFactory.create(id.oid)
    )

}

/** A factory for creating an [[com.quane.little.language.Code]] for a
  * primitive's id.
  *
  * @author Sean Connolly
  */
object PrimitiveFactory {

  def create(id: String): Code = {
    id match {
      case PrimitiveService.Get => new Getter("")
      case PrimitiveService.Set => new Setter("", Value(""))
      case PrimitiveService.Print => new Printer(Value(""))
      case PrimitiveService.Conditional => new Conditional(new Logic(Value(1), LogicOperation.Equals, Value(1)))
      // Math
      case PrimitiveService.Addition => new Addition(Value(1), Value(1))
      case PrimitiveService.Subtraction => new Subtraction(Value(1), Value(1))
      case PrimitiveService.Multiplication => new Multiplication(Value(1), Value(1))
      case PrimitiveService.Division => new Division(Value(1), Value(1))
      // Logic
      case PrimitiveService.Equals => new Logic(Value(1), LogicOperation.Equals, Value(1))
      case PrimitiveService.NotEquals => new Logic(Value(1), LogicOperation.NotEquals, Value(1))
      case PrimitiveService.LessThan => new Logic(Value(1), LogicOperation.LessThan, Value(1))
      case PrimitiveService.GreaterThan => new Logic(Value(1), LogicOperation.GreaterThan, Value(1))
      case PrimitiveService.And => new Logic(Value(1), LogicOperation.And, Value(1))
      case PrimitiveService.Or => new Logic(Value(1), LogicOperation.Or, Value(1))
      case _ => throw new IllegalAccessException("No expression '" + id + "'")
    }
  }

}