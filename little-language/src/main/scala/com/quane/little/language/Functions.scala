package com.quane.little.language

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import com.quane.little.language.data.Value
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

  def blank: FunctionDefinition = new FunctionDefinition("")

  def move: FunctionDefinition = {
    val fun = new FunctionDefinition("move").addParam("speed")
    val speedArg = new Pointer(fun, "speed")
    val guySpeed = new Pointer(fun, Operable.SPEED)
    fun.addStep(new SetStatement(guySpeed, new GetStatement(speedArg)))
  }

  def stop: FunctionDefinition = {
    val fun = new FunctionDefinition("stop")
    val pointer = new Pointer(fun, Operable.SPEED)
    fun.addStep(new SetStatement(pointer, new Value(0)))
  }

  def turn: FunctionDefinition = {
    val fun = new FunctionDefinition("turn").addParam("direction")
    val directionArg = new Pointer(fun, "direction")
    val myDirection = new Pointer(fun, Operable.DIRECTION)
    fun.addStep(new SetStatement(myDirection, new GetStatement(directionArg)))
  }

  def turnRandom(mob: Operator): Block = {
    val min = new Value(1)
    val max = new Value(360)
    val randomFun = new Block(mob)
    randomFun.addStep(
      new SetStatement(
        new Pointer(randomFun, Operable.DIRECTION), new RandomNumber(min, max)
      )
    )
  }

  def turnRelative: FunctionDefinition = {
    val relativelyFun = new FunctionDefinition("turnRelative").addParam("degrees")
    val dirPointer = new Pointer(relativelyFun, Operable.DIRECTION)
    val getCurrentDir = new GetStatement(dirPointer)
    val dirChangeArg = new Pointer(relativelyFun, "degrees")
    val dirChange = new GetStatement(dirChangeArg)
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new SetStatement(dirPointer, getNewDirection)
    relativelyFun.addStep(setNewDirection)
  }

  def voyage: FunctionDefinition = {
    val fun = new FunctionDefinition("voyage")
    val myDirection = new GetStatement(fun, Operable.DIRECTION)
    val myX = new GetStatement(fun, Operable.X)
    val myY = new GetStatement(fun, Operable.Y)
    // Step #1: Turn South if I'm facing North
    val north = new Value(270)
    val south = new Value(90)
    val isNorth = new Evaluation(myDirection, Equals, north)
    val turnSouth = new FunctionReference(fun, "turn").addArg("direction", south)
    val turnSouthIfNorth = new Conditional(fun, isNorth).addStep(turnSouth)
    fun.addStep(turnSouthIfNorth)
    // Step #2: Remember _Home_ is _Here_
    val homeXPointer = new Pointer(fun, "HomeX")
    val homeYPointer = new Pointer(fun, "HomeY")
    fun.addStep(new SetStatement(homeXPointer, myX))
    fun.addStep(new SetStatement(homeYPointer, myY))
    // Step #3: Set speed to 10
    fun.addStep(new FunctionReference(fun, "move").addArg("speed", new Value(10)))
  }

  def printDirection: FunctionDefinition = {
    val fun = new FunctionDefinition("print dir")
    fun.addStep(new PrintStatement(new GetStatement(new Pointer(fun, Operable.DIRECTION))))
  }

  def pointToward(mob: Operator, x: Expression, y: Expression): Block = {
    val fun = new Block(mob)
    fun.addStep(new SetStatement(new Pointer(fun, Operable.DIRECTION), getAngleTo(mob, x, y)))
  }

  def getAngleTo(scope: Scope, x: Expression, y: Expression): Block = {
    val mobX = new GetStatement(new Pointer(scope, Operable.X))
    val mobY = new GetStatement(new Pointer(scope, Operable.Y))
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
    val saveAngleStep = new SetStatement(anglePointer, calculateAngleStep)

    // Check if the angle is too small
    val tooSmallChecker = new Evaluation(new GetStatement(anglePointer), LessThan, new Value(0))
    val cleanerFunction = new SetStatement(anglePointer, new Addition(new GetStatement(anglePointer), new Value(360)))
    val tooSmallCleaner = new Conditional(angleFunction, tooSmallChecker).addStep(cleanerFunction)

    // Build the function
    angleFunction.addStep(calculateAngleStep)
    angleFunction.addStep(saveAngleStep)
    angleFunction.addStep(tooSmallCleaner)
    angleFunction.addStep(new GetStatement(anglePointer))
    angleFunction
  }
}