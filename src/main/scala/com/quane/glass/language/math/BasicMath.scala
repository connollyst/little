package com.quane.glass.language.math

import com.quane.glass.language.Expression
import com.quane.glass.language.data.Number
import com.quane.glass.language.data.NumericValue
import org.eintr.loglady.Logging

sealed trait BasicMath
    extends Expression[Number]

class Addition(a: Expression[NumericValue], b: Expression[NumericValue])
        extends BasicMath
        with Logging {

    def evaluate: Number = {
        val numberA = a.evaluate.primitive
        val numberB = b.evaluate.primitive
        log.info("Adding " + numberA + " to " + numberB)
        new Number(numberA + numberB);
    }

}