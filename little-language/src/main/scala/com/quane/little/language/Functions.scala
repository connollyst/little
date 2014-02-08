package com.quane.little.language

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.quane.little.language.data.{Variable, Value}
import com.quane.little.language.math._
import com.quane.little.language.memory.Pointer

/** A set functions used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Functions {

  private val worker = Executors.newSingleThreadScheduledExecutor()

  def inTime(seconds: Int, fun: Expression) {
    val task = new Runnable() {
      def run() {
        fun.evaluate
      }
    }
    worker.schedule(task, seconds, TimeUnit.SECONDS)
  }

  def move(mob: Operator, speed: Expression): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.SPEED)
    fun.addStep(new SetStatement(pointer, speed))
  }

  def stop(mob: Operator): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.SPEED)
    fun.addStep(new SetStatement(pointer, new Value(0)))
  }

  def turn(mob: Operator, degrees: Expression): Function = {
    val fun = new Function(mob)
    val pointer = new Pointer(fun, Operable.DIRECTION)
    fun.addStep(new SetStatement(pointer, degrees))
  }

  def turnRandom(mob: Operator): Function = {
    val min = new Value(1)
    val max = new Value(360)
    val randomFun = new Function(mob)
    randomFun.addStep(
      new SetStatement(
        new Pointer(randomFun, Operable.DIRECTION), new RandomNumber(min, max)
      )
    )
  }

  def turnRelative(mob: Operator, degrees: Int): Function = {
    val relativelyFun = new Function(mob)
    val dirPointer = new Pointer(relativelyFun, Operable.DIRECTION)
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
    fun.addStep(new PrintStatement(new GetStatement(new Pointer(fun, Operable.DIRECTION))))
  }

  def pointToward(mob: Operator, x: Expression, y: Expression): Function = {
    val fun = new Function(mob)
    fun.addStep(new SetStatement(new Pointer(fun, Operable.DIRECTION), getAngleTo(mob, x, y)))
  }

  def getAngleTo(scope: Scope, x: Expression, y: Expression): Function = {
    val mobX = new GetStatement(new Pointer(scope, Operable.X))
    val mobY = new GetStatement(new Pointer(scope, Operable.Y))
    getAngleBetween(scope, mobX, mobY, x, y)
  }

  def getAngleBetween(scope: Scope, fromX: Expression, fromY: Expression, toX: Expression, toY: Expression): Function = {
    val angleFunction = new Function(scope)
    val anglePointer = new Pointer(angleFunction, "angle")

    // Build the angle calculation
    val deltaX = new Subtraction(toX, fromX)
    val deltaY = new Subtraction(toY, fromY)
    val radians = new ArcTan2(deltaY, deltaX)
    val calculateAngleStep = new Division(new Multiplication(radians, new Value(180)), new Value(scala.math.Pi))

    // Save the angle to memory
    val saveAngleStep = new SetStatement(anglePointer, calculateAngleStep)

    // Check if the angle is too small
    val tooSmallChecker = new Evaluation(new GetStatement(anglePointer), LessThan, new Value(0))
    val cleanerFunction = new Function(angleFunction).addStep(
      new SetStatement(anglePointer, new Addition(new GetStatement(anglePointer), new Value(360)))
    )
    val tooSmallCleaner = new Conditional(tooSmallChecker, cleanerFunction)

    // Build the function
    angleFunction.addStep(calculateAngleStep)
    angleFunction.addStep(saveAngleStep)
    angleFunction.addStep(tooSmallCleaner)
    angleFunction.lastStep = new GetStatement(anglePointer)
    angleFunction
  }
}