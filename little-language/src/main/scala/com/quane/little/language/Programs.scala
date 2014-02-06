package com.quane.little.language

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.quane.little.language.data.Value
import com.quane.little.language.math.Addition
import com.quane.little.language.math.RandomNumber
import com.quane.little.language.memory.Pointer

/** A set programs used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Programs {

  private val worker = Executors.newSingleThreadScheduledExecutor()

  def inTime(seconds: Int, fun: Expression[_]) {
    val task = new Runnable() {
      def run() {
        fun.evaluate
      }
    }
    worker.schedule(task, seconds, TimeUnit.SECONDS)
  }

  def move(mob: Operator, speed: Expression[Value]): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.VAR_SPEED)
    fun.addStep(new SetStatement(pointer, speed))
  }

  def stop(mob: Operator): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.VAR_SPEED)
    fun.addStep(new SetStatement(pointer, new Value(0)))
  }

  def turn(mob: Operator, degrees: Expression[Value]): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.VAR_DIRECTION)
    fun.addStep(new SetStatement(pointer, degrees))
  }

  def turnRandom(mob: Operator): Function = {
    val min = new Value(1)
    val max = new Value(360)
    val randomFun = new Function(mob)
    randomFun.addStep(
      new SetStatement(
        new Pointer(randomFun, Operable.VAR_DIRECTION), new RandomNumber(min, max)
      )
    )
  }

  def turnRelative(mob: Operator, degrees: Int): Function = {
    val relativelyFun = new Function(mob)
    val dirPointer = new Pointer(relativelyFun, Operable.VAR_DIRECTION)
    val getCurrentDir = new GetStatement(dirPointer)
    val dirChange = new Value(degrees)
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new SetStatement(dirPointer, getNewDirection)
    relativelyFun.addStep(setNewDirection)
  }

  def voyage(mob: Operator): Function = {
    val fun = new Function(mob)
    // Step #1: Turn South if I'm facing North
    val myDirection = mob.direction
    val north = new Value(270)
    val south = new Value(90)
    val isNorth = new Evaluation(myDirection, Equals, north)
    val turnSouth = turn(mob, south)
    fun.addStep(new Conditional(isNorth, turnSouth))
    // Step #2: Remember _Home_ is _Here_
    val homeXPointer = new Pointer(fun, "HomeX")
    val homeYPointer = new Pointer(fun, "HomeY")
    fun.addStep(new SetStatement(homeXPointer, mob.x))
    fun.addStep(new SetStatement(homeYPointer, mob.y))
    // Step #3: Set speed to 10
    fun.addStep(move(mob, new Value(10)))
  }

  def printDirection(mob: Operator): Function = {
    val fun = new Function(mob)
    fun.addStep(new PrintStatement(new GetStatement(new Pointer(fun, Operable.VAR_DIRECTION))))
  }

}