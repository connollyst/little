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

  def move: FunctionDefinition = {
    val fun = new FunctionDefinition("move")
    val speedArg = new Pointer(fun, "speed")
    val guySpeed = new Pointer(fun, Operable.SPEED)
    fun.addStep(new Set(guySpeed, new Get(speedArg)))
  }

  def stop: FunctionDefinition = {
    val fun = new FunctionDefinition("stop")
    val pointer = new Pointer(fun, Operable.SPEED)
    fun.addStep(new Set(pointer, new Value(0)))
  }

  def turn(mob: Operator, degrees: Expression): Block = {
    val fun = new Block(mob)
    val pointer = new Pointer(fun, Operable.DIRECTION)
    fun.addStep(new Set(pointer, degrees))
  }

  def turnRandom(mob: Operator): Block = {
    val min = new Value(1)
    val max = new Value(360)
    val randomFun = new Block(mob)
    randomFun.addStep(
      new Set(
        new Pointer(randomFun, Operable.DIRECTION), new RandomNumber(min, max)
      )
    )
  }

  def turnRelative(mob: Operator, degrees: Int): Block = {
    val relativelyFun = new Block(mob)
    val dirPointer = new Pointer(relativelyFun, Operable.DIRECTION)
    val getCurrentDir = new Get(dirPointer)
    val dirChange = new Value(degrees)
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new Set(dirPointer, getNewDirection)
    relativelyFun.addStep(setNewDirection)
  }

  def voyage(mob: Operator): Block = {
    val fun = new Block(mob)
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
    fun.addStep(new Set(homeXPointer, mob.x))
    fun.addStep(new Set(homeYPointer, mob.y))
    // Step #3: Set speed to 10
    fun.addStep(new FunctionReference(mob, "move").addArg("speed", new Value(10)))
  }

  def printDirection(mob: Operator): Block = {
    val fun = new Block(mob)
    fun.addStep(new Print(new Get(new Pointer(fun, Operable.DIRECTION))))
  }

  def pointToward(mob: Operator, x: Expression, y: Expression): Block = {
    val fun = new Block(mob)
    fun.addStep(new Set(new Pointer(fun, Operable.DIRECTION), getAngleTo(mob, x, y)))
  }

  def getAngleTo(scope: Scope, x: Expression, y: Expression): Block = {
    val mobX = new Get(new Pointer(scope, Operable.X))
    val mobY = new Get(new Pointer(scope, Operable.Y))
    getAngleBetween(scope, mobX, mobY, x, y)
  }

  def getAngleBetween(scope: Scope, fromX: Expression, fromY: Expression, toX: Expression, toY: Expression): Block = {
    val angleFunction = new Block(scope)
    val anglePointer = new Pointer(angleFunction, "angle")

    // Build the angle calculation
    val deltaX = new Subtraction(toX, fromX)
    val deltaY = new Subtraction(toY, fromY)
    val radians = new ArcTan2(deltaY, deltaX)
    val calculateAngleStep = new Division(new Multiplication(radians, new Value(180)), new Value(scala.math.Pi))

    // Save the angle to memory
    val saveAngleStep = new Set(anglePointer, calculateAngleStep)

    // Check if the angle is too small
    val tooSmallChecker = new Evaluation(new Get(anglePointer), LessThan, new Value(0))
    val cleanerFunction = new Block(angleFunction).addStep(
      new Set(anglePointer, new Addition(new Get(anglePointer), new Value(360)))
    )
    val tooSmallCleaner = new Conditional(tooSmallChecker, cleanerFunction)

    // Build the function
    angleFunction.addStep(calculateAngleStep)
    angleFunction.addStep(saveAngleStep)
    angleFunction.addStep(tooSmallCleaner)
    angleFunction.addStep(new Get(anglePointer))
    angleFunction
  }
}