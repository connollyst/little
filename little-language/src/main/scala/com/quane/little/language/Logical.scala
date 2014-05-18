package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects
import com.quane.little.language.LogicalOperation.LogicalOperation

/** An enumeration of all operations supported by the
  * [[com.quane.little.language.Logical]] expression.
  *
  * @author Sean Connolly
  */
object LogicalOperation extends Enumeration {
  type LogicalOperation = Value
  val Equals, NotEquals, LessThan, GreaterThan, And, Or = Value
}

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operation the evaluation operator
  * @param right the right operand
  *
  * @author Sean Connolly
  */
class Logical(val left: Expression,
              val operation: LogicalOperation,
              val right: Expression)
  extends Expression {

  override def evaluate(scope: Scope): Value = {
    val l = left.evaluate(scope)
    // TODO only evaluate the right if necessary
    val r = right.evaluate(scope)
    Value(
      operation match {
        case LogicalOperation.Equals => l equals r
        case LogicalOperation.NotEquals => !(l equals r)
        case LogicalOperation.LessThan => (l compare r) < 0
        case LogicalOperation.GreaterThan => (l compare r) > 0
        case LogicalOperation.And => l.asBool && r.asBool
        case LogicalOperation.Or => l.asBool || r.asBool
      }
    )
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Logical]

  override def equals(other: Any): Boolean = other match {
    case that: Logical =>
      (that canEqual this) &&
        left == that.left &&
        operation == that.operation &&
        right == that.right
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(left, operation, right)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("left", left)
      .add("operation", operation)
      .add("right", right)
      .toString

}
