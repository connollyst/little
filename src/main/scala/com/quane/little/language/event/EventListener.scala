package com.quane.little.language.event

import com.quane.little.language.Expression
import com.quane.little.language.Function

class EventListener(val event: GlassEvent, val function: Function)
        extends Expression[Boolean] {

    def evaluate: Boolean = {
        function.evaluate
        true
    }

}