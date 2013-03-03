package com.quane.little.language.math

import com.quane.little.language.Expression
import com.quane.little.language.data.Number
import org.eintr.loglady.Logging

sealed trait BasicMath
    extends Expression[Number]

class Addition(a: Expression[Number], b: Expression[Number])
        extends BasicMath
        with Logging {

    // TODO support dynamic casting so any Value can be passed in
    
    def evaluate: Number = {
        val numberA = a.evaluate.primitive
        val numberB = b.evaluate.primitive
        log.info("Adding " + numberA + " to " + numberB)
        new Number(numberA + numberB);
    }

}