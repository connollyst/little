package com.quane.glass.core

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.AssignmentStatement

object Programs {

    private val worker = Executors.newSingleThreadScheduledExecutor()

    def inTime(seconds: Int, fun: Expression[Any]) = {
        val task = new Runnable() {
            def run() = {
                fun.evaluate
            }
        };
        worker.schedule(task, seconds, TimeUnit.SECONDS);
    }

    def move(guy: Guy, speed: String): Expression[Any] = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(Guy.VAR_SPEED, speed, fun))
        fun
    }

    def stop(guy: Guy): Expression[Any] = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(Guy.VAR_SPEED, "0", fun))
        fun
    }

    def turn(guy: Guy, degrees: String): Expression[Any] = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(Guy.VAR_DIRECTION, degrees, fun))
        fun
    }

}