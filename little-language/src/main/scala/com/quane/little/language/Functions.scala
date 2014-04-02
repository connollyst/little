package com.quane.little.language

import com.quane.little.language.data.Value
import com.quane.little.language.math._


/** A set functions used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Functions {

  def blank: FunctionDefinition = new FunctionDefinition("")

  def move: FunctionDefinition = {
    val fun = new FunctionDefinition("move").addParam("speed")
    val speedArg = new Pointer("speed")
    val guySpeed = new Pointer(Operable.SPEED)
    fun.addStep(new SetStatement(guySpeed, new GetStatement(speedArg)))
  }

  def stop: FunctionDefinition = {
    val fun = new FunctionDefinition("stop")
    val pointer = new Pointer(Operable.SPEED)
    fun.addStep(new SetStatement(pointer, Value(0)))
  }

  def turn: FunctionDefinition = {
    val fun = new FunctionDefinition("turn").addParam("direction")
    val directionArg = new Pointer("direction")
    val myDirection = new Pointer(Operable.DIRECTION)
    fun.addStep(new SetStatement(myDirection, new GetStatement(directionArg)))
  }

  def turnRandom: Block = {
    val min = Value(1)
    val max = Value(360)
    val randomFun = new Block
    randomFun.addStep(
      new SetStatement(
        new Pointer(Operable.DIRECTION), new RandomNumber(min, max)
      )
    )
  }

  def turnRelative: FunctionDefinition = {
    val relativelyFun = new FunctionDefinition("turnRelative").addParam("degrees")
    val dirPointer = new Pointer(Operable.DIRECTION)
    val getCurrentDir = new GetStatement(dirPointer)
    val dirChangeArg = new Pointer("degrees")
    val dirChange = new GetStatement(dirChangeArg)
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new SetStatement(dirPointer, getNewDirection)
    relativelyFun.addStep(setNewDirection)
  }

  def voyage: FunctionDefinition = {
    val fun = new FunctionDefinition("voyage")
    val myDirection = new GetStatement(Operable.DIRECTION)
    val myX = new GetStatement(Operable.X)
    val myY = new GetStatement(Operable.Y)
    // Step #1: Turn South if I'm facing North
    val north = Value(270)
    val south = Value(90)
    val isNorth = new Evaluation(myDirection, Equals, north)
    val turnSouth = new FunctionReference("turn").addArg("direction", south)
    val turnSouthIfNorth = new Conditional(isNorth).addStep(turnSouth)
    fun.addStep(turnSouthIfNorth)
    // Step #2: Remember _Home_ is _Here_
    val homeXPointer = new Pointer("HomeX")
    val homeYPointer = new Pointer("HomeY")
    fun.addStep(new SetStatement(homeXPointer, myX))
    fun.addStep(new SetStatement(homeYPointer, myY))
    // Step #3: Set speed to 10
    fun.addStep(new FunctionReference("move").addArg("speed", Value(10)))
  }

  def printDirection: FunctionDefinition = {
    val fun = new FunctionDefinition("print dir")
    fun.addStep(new PrintStatement(new GetStatement(Operable.DIRECTION)))
  }

  def pointToward(x: Expression, y: Expression): Block = {
    val fun = new Block
    fun.addStep(new SetStatement(Operable.DIRECTION, getAngleTo(x, y)))
  }

  def getAngleTo(x: Expression, y: Expression): Block = {
    val mobX = new GetStatement(Operable.X)
    val mobY = new GetStatement(Operable.Y)
    getAngleBetween(mobX, mobY, x, y)
  }

  def getAngleBetween(fromX: Expression, fromY: Expression, toX: Expression, toY: Expression): Block = {
    val angleFunction = new Block
    val anglePointer = new Pointer("angle")
    // Build the angle calculation
    val deltaX = new Subtraction(toX, fromX)
    val deltaY = new Subtraction(toY, fromY)
    val radians = new ArcTan2(deltaY, deltaX)
    val calculateAngleStep = new Division(new Multiplication(radians, Value(180)), Value(scala.math.Pi))
    // Save the angle to memory
    val saveAngleStep = new SetStatement(anglePointer, calculateAngleStep)
    // Check if the angle is too small
    val tooSmallChecker = new Evaluation(new GetStatement(anglePointer), LessThan, Value(0))
    val cleanerFunction = new SetStatement(anglePointer, new Addition(new GetStatement(anglePointer), Value(360)))
    val tooSmallCleaner = new Conditional(tooSmallChecker).addStep(cleanerFunction)
    // Build the function
    angleFunction += calculateAngleStep
    angleFunction += saveAngleStep
    angleFunction += tooSmallCleaner
    angleFunction += new GetStatement(anglePointer)
    angleFunction
  }

}