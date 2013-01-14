package com.quane.glass.core.event

import com.quane.glass.core.language.Function

class EventListener(val event: GlassEvent, val function: Function) {

    def fire(): Unit = {
        function.evaluate
    }

}