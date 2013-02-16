package com.quane.glass.language.event

import com.quane.glass.language.Expression
import com.quane.glass.language.Function

class EventListener(val event: GlassEvent, val function: Function)
        extends Expression[Boolean] {

    def evaluate: Boolean = {
        function.evaluate
        true
    }

}