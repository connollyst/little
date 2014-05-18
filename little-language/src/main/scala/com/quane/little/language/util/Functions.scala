package com.quane.little.language.util

import com.quane.little.language.data.Value
import com.quane.little.language.math._
import com.quane.little.language._

/** A set functions used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Functions {

  def blank: FunctionDefinition = new FunctionDefinition("")

  def move: FunctionDefinition = {
    val fun = new FunctionDefinition("move").addParam("speed")
    fun.addStep(new Setter(Operable.SPEED, new Getter("speed")))
  }

  def stop: FunctionDefinition = {
    val fun = new FunctionDefinition("stop")
    fun.addStep(new Setter(Operable.SPEED, Value(0)))
  }

  def turn: FunctionDefinition = {
    val fun = new FunctionDefinition("turn").addParam("direction")
    fun.addStep(new Setter(Operable.DIRECTION, new Getter("direction")))
  }

  def turnRandom: Block = {
    val min = Value(1)
    val max = Value(360)
    val setter = new Setter(Operable.DIRECTION, new RandomNumber(min, max))
    val randomFun = new Block
    randomFun.addStep(setter)
  }

  def turnRelative: FunctionDefinition = {
    val relativelyFun = new FunctionDefinition("turnRelative").addParam("degrees")
    val getCurrentDir = new Getter(Operable.DIRECTION)
    val dirChange = new Getter("degrees")
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new Setter(Operable.DIRECTION, getNewDirection)
    relativelyFun.addStep(setNewDirection)
  }

  def voyage: FunctionDefinition = {
    val fun = new FunctionDefinition("voyage")
    val myDirection = new Getter(Operable.DIRECTION)
    val myX = new Getter(Operable.X)
    val myY = new Getter(Operable.Y)
    // Step #1: Turn South if I'm facing North
    val north = Value(270)
    val south = Value(90)
    val isNorth = new Logic(myDirection, LogicOperation.Equals, north)
    val turnSouth = new FunctionReference("turn").addArg("direction", south)
    val turnSouthIfNorth = new Conditional(isNorth).addStep(turnSouth)
    fun.addStep(turnSouthIfNorth)
    // Step #2: Remember _Home_ is _Here_
    fun.addStep(new Setter("HomeX", myX))
    fun.addStep(new Setter("HomeY", myY))
    // Step #3: Set speed to 10
    fun.addStep(new FunctionReference("move").addArg("speed", Value(10)))
  }

  def printDirection: FunctionDefinition = {
    val fun = new FunctionDefinition("print dir")
    fun.addStep(new Printer(new Getter(Operable.DIRECTION)))
  }

  def pointToward(x: Expression, y: Expression): Block = {
    val fun = new Block
    fun.addStep(new Setter(Operable.DIRECTION, getAngleTo(x, y)))
  }

  def getAngleTo(x: Expression, y: Expression): Block = {
    val mobX = new Getter(Operable.X)
    val mobY = new Getter(Operable.Y)
    getAngleBetween(mobX, mobY, x, y)
  }

  def getAngleBetween(fromX: Expression, fromY: Expression, toX: Expression, toY: Expression): Block = {
    val angleFunction = new Block
    val anglePointer = "angle"
    // Build the angle calculation
    val deltaX = new Subtraction(toX, fromX)
    val deltaY = new Subtraction(toY, fromY)
    val radians = new ArcTan2(deltaY, deltaX)
    val calculateAngleStep = new Division(new Multiplication(radians, Value(180)), Value(scala.math.Pi))
    // Save the angle to memory
    val saveAngleStep = new Setter(anglePointer, calculateAngleStep)
    // Check if the angle is too small
    val tooSmallChecker = new Logic(new Getter(anglePointer), LogicOperation.LessThan, Value(0))
    val cleanerFunction = new Setter(anglePointer, new Addition(new Getter(anglePointer), Value(360)))
    val tooSmallCleaner = new Conditional(tooSmallChecker).addStep(cleanerFunction)
    // Build the function
    angleFunction += calculateAngleStep
    angleFunction += saveAngleStep
    angleFunction += tooSmallCleaner
    angleFunction += new Getter(anglePointer)
    angleFunction
  }

}