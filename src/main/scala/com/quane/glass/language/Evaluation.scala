package com.quane.glass.language

import com.quane.glass.language.data.Bool

class Evaluation[T](
    left: Expression[T],
    operator: EvaluationOperator,
    right: Expression[T])
        extends Expression[Bool] {

    def evaluate: Bool = {
        // TODO clean this up, gross!
        val retVal = operator match {
            case Equals => {
                left equals right
            }
            case NotEquals => {
                !(left equals right)
            }
        }
        new Bool(retVal)
    }
}

sealed trait EvaluationOperator
object Equals extends EvaluationOperator
object NotEquals extends EvaluationOperator
// TODO
// GreaterThan
// LessThan
// Contains??