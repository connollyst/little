package com.quane.little.language

import com.quane.little.language.data.Value

/** A logical evaluation of the relationship between two arguments expressions.
  *
  * @param left the left operand
  * @param operator the evaluation operator
  * @param right the right operand
  * @tparam T the operand expression type
  */
class Evaluation[T <: Expression[_]](left: T,
                                     operator: EvaluationOperator,
                                     right: T)
  extends Expression[Value] {

  def evaluate: Value = {
    // TODO clean this up, gross!
    new Value(
      operator match {
        case Equals => left equals right
        case NotEquals => !(left equals right)
      }
    )
  }
}

sealed trait EvaluationOperator

object Equals extends EvaluationOperator

object NotEquals extends EvaluationOperator

// TODO GreaterThan, LessThan, Contains..