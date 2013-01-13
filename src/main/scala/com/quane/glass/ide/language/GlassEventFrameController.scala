package com.quane.glass.ide.language

import org.eintr.loglady.Logging

import com.quane.glass.core.language.Function

class GlassEventFrameController(view: GlassEventFrame)
        extends Logging {

    def validate: Unit = {
        // TODO
        log.error("TODO: implement EventFrameController.validate")
    }

    def compile: Function = {
        val fun = new Function
        view.stepPanels.foreach(panel => {
            val expression = panel.compile(fun)
            fun.addStep(expression)
        })
        fun
    }

}