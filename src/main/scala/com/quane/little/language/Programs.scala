package com.quane.little.language

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.quane.little.language.data.Number
import com.quane.little.language.math.RandomNumber
import com.quane.little.language.memory.Pointer
import com.quane.little.language.math.Addition

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

    def turn(mob: Operator, degrees: Expression[Number]): Function = {
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
        
        val directionPointer = new Pointer(randomFun, Mob.VAR_DIRECTION, classOf[Number])
        //        randomFun.addStep(new SetStatement(directionPointer, randomFun))
        null
    }

    def turnRelative(mob: Operator, degrees: Int): Function = {
        val relativelyFun = new Function(mob)
        val dirPointer = new Pointer(relativelyFun, Mob.VAR_DIRECTION, classOf[Number])
        val getCurrentDir = new GetStatement(dirPointer)
        val dirChange = new Number(degrees);
        val getNewDirection = new Addition(getCurrentDir, dirChange)
        val setNewDirection = new SetStatement(dirPointer, getNewDirection)
        relativelyFun.addStep(setNewDirection)
    }

    def voyage(mob: Operator): Function = {
        val fun = new Function(mob)
        // Step #1: Turn South if I'm facing North
        val myDirection = mob.direction
        val north = new Number(270)
        val south = new Number(90)
        val isNorth = new Evaluation(myDirection, Equals, north)
        val turnSouth = new Function(mob)
        val assignmentStep = new SetDirectionStatement(turnSouth, south)
        turnSouth.addStep(assignmentStep)
        fun.addStep(new Conditional(isNorth, turnSouth))
        // Step #2: Remember _Home_ is _Here_
        val homeXPointer = new Pointer(fun, "HomeX", classOf[Number])
        val homeYPointer = new Pointer(fun, "HomeY", classOf[Number])
        fun.addStep(new SetStatement(homeXPointer, mob.x))
        fun.addStep(new SetStatement(homeYPointer, mob.y))
        // Step #3: Set speed to 10
        fun.addStep(new SetSpeedStatement(fun, new Number(10)))
        fun
    }
    
    def printDirection(mob: Operator): Function = {
        val fun = new Function(mob)
        fun.addStep(new PrintStatement(new GetStatement(new Pointer(fun, Mob.VAR_DIRECTION, classOf[Number]))))
        fun
    }
}