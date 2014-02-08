package com.quane.little.language

import com.quane.little.language.data.Value
import com.sun.tools.corba.se.idl.constExpr.GreaterThan

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

  def evaluate: Value = {
    val l = left.evaluate
    val r = right.evaluate
    new Value(
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