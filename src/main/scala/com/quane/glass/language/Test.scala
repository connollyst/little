package com.quane.glass.language

import com.quane.glass.core.language.data.Bool

class Test(
    left: Expression[Bool],
    operator: LogicalOperator,
    right: Expression[Bool])
        extends Expression[Boolean] {

    def evaluate: Boolean = isTrue

    def isTrue: Boolean = {
        operator match {
            case AND => {
                (left.evaluate.primitive && right.evaluate.primitive)
            }
            case OR => {
                (left.evaluate.primitive || right.evaluate.primitive)
            }
        }
    }

    def isFalse: Boolean = !isTrue

}

sealed trait LogicalOperator
object AND extends LogicalOperator
object OR extends LogicalOperator
// TODO
// XOR etc?