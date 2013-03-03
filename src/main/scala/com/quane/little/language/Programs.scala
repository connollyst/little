package com.quane.little.language

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Location
import com.quane.little.language.data.Number
import com.quane.little.language.math.RandomNumber
import com.quane.little.language.memory.Pointer
import com.quane.little.language.math.Addition
import com.quane.little.language.data.Direction
import com.quane.little.language.data.Location
import com.quane.little.game.entity.Mob

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

    def move(mob: Operator, speed: Expression[Number]): Function = {
        val fun = new Function(mob)
        fun.addStep(new SetSpeedStatement(fun, speed))
        fun
    }

    def stop(mob: Operator): Function = {
        val fun = new Function(mob)
        fun.addStep(new SetSpeedStatement(fun, new Number(0)))
        fun
    }

    def turn(mob: Operator, degrees: Expression[Direction]): Function = {
        val fun = new Function(mob)
        fun.addStep(new SetDirectionStatement(fun, degrees))
        fun
    }

    def turnRandom(mob: Operator): Function = {
        val min = new Number(1)
        val max = new Number(360)
        val randomDirection = new RandomNumber(min, max)

        // TODO add a step to get a random number when evaluated
        val randomFun = new Function(mob)
        
        val directionPointer = new Pointer(randomFun, Mob.VAR_DIRECTION, classOf[Direction])
        //        randomFun.addStep(new SetterStatement(directionPointer, randomFun))
        null
    }

    def turnRelative(mob: Operator, degrees: Int): Function = {
        val relativelyFun = new Function(mob)
        val dirPointer = new Pointer(relativelyFun, Mob.VAR_DIRECTION, classOf[Direction])
        val getCurrentDir = new GetterStatement(dirPointer)
        val dirChange = new Number(degrees);
        val getNewDirectionNumber = new Addition(getCurrentDir, dirChange)
        val getNewDirection = new CastNumberToDirection(getNewDirectionNumber)
        val setNewDirection = new SetterStatement(dirPointer, getNewDirection)
        relativelyFun.addStep(setNewDirection)
    }

    def voyage(mob: Operator): Function = {
        val fun = new Function(mob)
        // Step #1: Turn South if I'm facing North
        val myDirection = new Direction(mob.direction)
        val north = new Direction(270)
        val south = new Direction(90)
        val isNorth = new Evaluation(myDirection, Equals, north)
        val turnSouth = new Function(mob)
        val assignmentStep = new SetDirectionStatement(turnSouth, south)
        turnSouth.addStep(assignmentStep)
        fun.addStep(new Conditional(isNorth, turnSouth))
        // Step #2: Remember _Home_ is _Here_
        val homePointer = new Pointer(fun, "Home", classOf[Location])
        fun.addStep(new SetterStatement(homePointer, mob.location))
        // Step #3: Set speed to 10
        fun.addStep(new SetSpeedStatement(fun, new Number(10)))
        fun
    }
}