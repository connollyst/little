package com.quane.glass.core

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.quane.glass.core.language.Conditional
import com.quane.glass.core.language.Equals
import com.quane.glass.core.language.Evaluation
import com.quane.glass.core.language.Expression
import com.quane.glass.core.language.Function
import com.quane.glass.core.language.SetDirectionStatement
import com.quane.glass.core.language.SetSpeedStatement
import com.quane.glass.core.language.GetterStatement
import com.quane.glass.core.language.SetterStatement
import com.quane.glass.core.language.data.Direction
import com.quane.glass.core.language.data.Location
import com.quane.glass.core.language.data.Number
import com.quane.glass.core.language.math.RandomNumber
import com.quane.glass.core.language.memory.Pointer
import com.quane.glass.core.language.math.Addition
import com.quane.glass.core.language.CastDirectionToNumber
import com.quane.glass.core.language.CastNumberToDirection

/** A set programs used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Programs {

    private val worker = Executors.newSingleThreadScheduledExecutor()

    def inTime(seconds: Int, fun: Expression[Any]) {
        val task = new Runnable() {
            def run() {
                fun.evaluate
            }
        };
        worker.schedule(task, seconds, TimeUnit.SECONDS);
    }

    def move(guy: Guy, speed: Expression[Number]): Function = {
        val fun = new Function(guy)
        fun.addStep(new SetSpeedStatement(fun, speed))
        fun
    }

    def stop(guy: Guy): Function = {
        val fun = new Function(guy)
        fun.addStep(new SetSpeedStatement(fun, new Number(0)))
        fun
    }

    def turn(guy: Guy, degrees: Expression[Direction]): Function = {
        val fun = new Function(guy)
        fun.addStep(new SetDirectionStatement(fun, degrees))
        fun
    }

    def turnRandom(guy: Guy): Function = {
        val min = new Number(1)
        val max = new Number(360)
        val randomDirection = new RandomNumber(min, max)

        // TODO add a step to get a random number when evaluated
        val randomFun = new Function(guy)
        val directionPointer = new Pointer(randomFun, Guy.VAR_DIRECTION, classOf[Direction])
        //        randomFun.addStep(new SetterStatement(directionPointer, randomDirection))
        null
    }

    def turnRelative(guy: Guy, degrees: Int): Function = {
        val relativelyFun = new Function(guy)
        val dirPointer = new Pointer(relativelyFun, Guy.VAR_DIRECTION, classOf[Direction])
        val getCurrentDir = new GetterStatement(dirPointer)
        val getNewDirectionNumber = new Addition(getCurrentDir, new Number(degrees))
        val getNewDirection = new CastNumberToDirection(getNewDirectionNumber)
        val setNewDirection = new SetterStatement(dirPointer, getNewDirection)
        relativelyFun.addStep(setNewDirection)
    }

    def voyage(guy: Guy): Function = {
        val fun = new Function(guy)
        // Step #1: Turn South if I'm facing North
        val myDirection = new Direction(guy.direction)
        val north = new Direction(270)
        val south = new Direction(90)
        val isNorth = new Evaluation(myDirection, Equals, north)
        val turnSouth = new Function(guy)
        val assignmentStep = new SetDirectionStatement(turnSouth, south)
        turnSouth.addStep(assignmentStep)
        fun.addStep(new Conditional(isNorth, turnSouth))
        // Step #2: Remember _Home_ is _Here_
        val homePointer = new Pointer(fun, "Home", classOf[Location])
        fun.addStep(new SetterStatement(homePointer, guy.location))
        // Step #3: Set speed to 10
        fun.addStep(new SetSpeedStatement(fun, new Number(10)))
        fun
    }
}