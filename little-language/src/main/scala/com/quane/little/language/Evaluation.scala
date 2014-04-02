package com.quane.little.language

import com.quane.little.language.data.Value

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operator the evaluation operator
  * @param right the right operand
  */
class Evaluation(left: Expression,
                 operator: EvaluationOperator,
                 right: Expression)
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
}

sealed trait EvaluationOperator

object Equals extends EvaluationOperator

object NotEquals extends EvaluationOperator

object LessThan extends EvaluationOperator

object GreaterThan extends EvaluationOperator

// TODO Contains..