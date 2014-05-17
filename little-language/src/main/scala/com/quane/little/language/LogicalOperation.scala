package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects
import com.quane.little.language.EvaluationOperator.EvaluationOperator
import com.sun.tools.corba.se.idl.constExpr.GreaterThan

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operator the evaluation operator
  * @param right the right operand
  */
class LogicalOperation(val left: Expression,
                       val operator: EvaluationOperator,
                       val right: Expression)
  extends Expression {

  override def evaluate(scope: Scope): Value = {
    val l = left.evaluate(scope)
    val r = right.evaluate(scope)
    Value(
      operator match {
        case EvaluationOperator.Equals => l equals r
        case EvaluationOperator.NotEquals => !(l equals r)
        case EvaluationOperator.LessThan => (l compare r) < 0
        case EvaluationOperator.GreaterThan => (l compare r) > 0
        case EvaluationOperator.And => l.asBool && r.asBool
        case EvaluationOperator.Or => l.asBool || r.asBool
      }
    )
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[LogicalOperation]

  override def equals(other: Any): Boolean = other match {
    case that: LogicalOperation =>
      (that canEqual this) &&
        left == that.left &&
        operator == that.operator &&
        right == that.right
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(left, operator, right)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString: String =
    Objects.toStringHelper(getClass)
      .add("left", left)
      .add("operator", operator)
      .add("right", right)
      .toString

}

object EvaluationOperator extends Enumeration {
  type EvaluationOperator = Value
  val Equals, NotEquals, LessThan, GreaterThan, And, Or = Value
}
