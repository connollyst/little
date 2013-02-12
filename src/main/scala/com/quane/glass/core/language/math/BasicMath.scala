package com.quane.glass.core.language.math

import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.data.Number

sealed trait BasicMath
    extends Expression[Number]

class Addition(a: Expression[Number], b: Expression[Number])
        extends BasicMath {

    def evaluate: Number = new Number(a.evaluate.int + b.evaluate.int);

}