package com.quane.little.language

import com.quane.little.language.data.Value
import com.google.common.base.Objects

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operator the evaluation operator
  * @param right the right operand
  */
class Evaluation(val left: Expression,
                 val operator: EvaluationOperator,
                 val right: Expression)
  extends Expression {

  override def evaluate(scope: Scope): Value = {
    val l = left.evaluate(scope)
    val r = right.evaluate(scope)
    Value(
      operator match {
        case Equals => l equals r
        case NotEquals => !(l equals r)
        case LessThan => (l compare r) < 0
        case GreaterThan => (l compare r) > 0
      }
    )
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Evaluation]

  override def equals(other: Any): Boolean = other match {
    case that: Evaluation =>
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

sealed trait EvaluationOperator

object Equals extends EvaluationOperator

object NotEquals extends EvaluationOperator

object LessThan extends EvaluationOperator

object GreaterThan extends EvaluationOperator

// TODO Contains..