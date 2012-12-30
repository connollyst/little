package com.quane.glass.core.event

import com.quane.glass.core.Guy
import com.quane.glass.core.language.Expression

class EventListener(val guy: Guy, val event: GlassEvent, val expression: Expression[_ <: Any]) {

    def fire(): Unit = {
        expression.evaluate
    }

}