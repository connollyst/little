package com.quane.little.language.util

import com.quane.little.language._
import com.quane.little.language.data.{Value, ValueType}
import com.quane.little.language.math._

/** A set functions used during development - mostly to sanity check the
  * language and how it compiles.
  *
  * @author Sean Connolly
  */
object Functions {

  def blank: FunctionDefinition = new FunctionDefinition("")

  def move: FunctionDefinition =
    new FunctionDefinition("move", ValueType.Nothing)
      .addParam("speed", ValueType.Integer)
      .addStep(new Setter(Operable.SPEED, new Getter("speed")))

  def stop: FunctionDefinition =
    new FunctionDefinition("stop", ValueType.Nothing)
      .addStep(new Setter(Operable.SPEED, Value(0)))

  def turn: FunctionDefinition =
    new FunctionDefinition("turn", ValueType.Nothing)
      .addParam("direction", ValueType.Integer)
      .addStep(new Setter(Operable.DIRECTION, new Getter("direction")))

  def turnRandom: Block = {
    val min = Value(1)
    val max = Value(360)
    val setter = new Setter(Operable.DIRECTION, new RandomNumber(min, max))
    new Block().addStep(setter)
  }

  def turnRelative: FunctionDefinition = {
    val getCurrentDir = new Getter(Operable.DIRECTION)
    val dirChange = new Getter("degrees")
    val getNewDirection = new Addition(getCurrentDir, dirChange)
    val setNewDirection = new Setter(Operable.DIRECTION, getNewDirection)
    new FunctionDefinition("turnRelative", ValueType.Nothing)
      .addParam("degrees", ValueType.Integer)
      .addStep(setNewDirection)
  }

  def voyage: FunctionDefinition = {
    val myDirection = new Getter(Operable.DIRECTION)
    val myX = new Getter(Operable.X)
    val myY = new Getter(Operable.Y)
    // Step #1: Turn South if I'm facing North
    val north = Value(270)
    val south = Value(90)
    val isNorth = new Logic(myDirection, LogicOperation.Equals, north)
    val turnSouth = new FunctionReference("turn").addArg("direction", south)
    val turnSouthIfNorth = new Conditional(isNorth).addStep(turnSouth)
    new FunctionDefinition("voyage")
      .addStep(turnSouthIfNorth)
      // Step #2: Remember _Home_ is _Here_
      .addStep(new Setter("HomeX", myX))
      .addStep(new Setter("HomeY", myY))
      // Step #3: Set speed to 10
      .addStep(new FunctionReference("move").addArg("speed", Value(10)))
  }

  def printDirection: FunctionDefinition =
    new FunctionDefinition("print dir")
      .addStep(new Printer(new Getter(Operable.DIRECTION)))

  def pointToward(x: Code, y: Code): Block =
    new Block()
      .addStep(new Setter(Operable.DIRECTION, getAngleTo(x, y)))

  def getAngleTo(x: Code, y: Code): Block = {
    val mobX = new Getter(Operable.X)
    val mobY = new Getter(Operable.Y)
    getAngleBetween(mobX, mobY, x, y)
  }

  def getAngleBetween(fromX: Code, fromY: Code, toX: Code, toY: Code): Block = {
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