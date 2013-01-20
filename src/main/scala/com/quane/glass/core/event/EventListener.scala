package com.quane.glass.core.event

import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function

class EventListener(val event: GlassEvent, val function: Function)
        extends Expression[Boolean] {

    def evaluate: Boolean = {
        function.evaluate
        true
    }

}