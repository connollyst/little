package com.quane.glass.language

import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.data.Value

trait Cast[V <: Value]
    extends Expression[V]

class CastDirectionToNumber(direction: Expression[Direction])
        extends Cast[Number] {

    def evaluate: Number = new Number(direction.evaluate.degrees)

}

class CastNumberToDirection(number: Expression[Number])
        extends Cast[Direction] {

    def evaluate: Direction = new Direction(number.evaluate)

}