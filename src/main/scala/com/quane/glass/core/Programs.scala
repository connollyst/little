package com.quane.glass.core

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.Conditional
import com.quane.glass.core.language.Equals
import com.quane.glass.core.language.Evaluation
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.math.RandomNumber
import com.quane.glass.core.language.GetterStatement
import com.quane.glass.core.language.AssignmentStatement
import com.quane.glass.core.language.PrintStatement
import com.quane.glass.core.language.math.Addition

/** A set programs used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
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

    def move(guy: Guy, speed: Expression[Number]): Function = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(fun, Guy.VAR_SPEED, speed))
        fun
    }

    def stop(guy: Guy): Function = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(fun, Guy.VAR_SPEED, new Number(0)))
        fun
    }

    def turn(guy: Guy, degrees: Expression[Direction]): Function = {
        val fun = new Function(guy)
        fun.addStep(new AssignmentStatement(fun, Guy.VAR_DIRECTION, degrees))
        fun
    }

    def turnRandom(guy: Guy): Function = {
        val min = new Number(1)
        val max = new Number(360)
        // Generates one number up front:
        val degrees = new RandomNumber(min, max)
        turn(guy, new Direction(degrees))
        // val randomFun = new Function(guy) // TODO must return a Number
        // turn(guy, randomFun)
    }

    def turnRelative(guy: Guy): Function = {
        val relativelyFun = new Function(guy)
        val getCurrentSpeed = new GetterStatement(relativelyFun, Guy.VAR_DIRECTION)
        // TODO instead of a map of String->Variables, let's have a Memory
        // object with preallocated, typed, variables at compile time
//        val newSpeed = new Addition(getCurrentSpeed, new Number(60))
//        val setNewSpeed = new AssignmentStatement(relativelyFun, Guy.VAR_DIRECTION, newSpeed)
//        relativelyFun.addStep(setNewSpeed)
        relativelyFun
    }

    def voyage(guy: Guy): Function = {
        val fun = new Function(guy)
        // Step #1: Turn South if I'm facing North
        val myDirection = new Direction(guy.direction)
        val north = new Direction(270)
        val south = new Direction(90)
        val isNorth = new Evaluation(myDirection, Equals, north)
        val turnSouth = new Function(guy)
        val assignmentStep = new AssignmentStatement(turnSouth, Guy.VAR_DIRECTION, south)
        turnSouth.addStep(assignmentStep)
        fun.addStep(new Conditional(isNorth, turnSouth))
        // Step #2: Remember _Home_ is _Here_
        fun.addStep(new AssignmentStatement(fun, "Home", guy.location))
        // Step #3: Set speed to 10
        fun.addStep(new AssignmentStatement(fun, Guy.VAR_SPEED, new Number(10)))
        fun
    }
}