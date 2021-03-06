package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.util.Functions

@RunWith(classOf[JUnitRunner])
class TestFunctions extends FunSuite {

  test("test function move") {
    val runtime = new Runtime
    runtime.saveFunction(Functions.move)
    val guy = new Operator(runtime, new StubOperable)
    new FunctionReference("move")
      .addArg("speed", Value(42))
      .evaluate(guy)
    assertSpeed(42, guy)
  }

  test("test function stop") {
    val runtime = new Runtime
    runtime.saveFunction(Functions.stop)
    val guy = new Operator(runtime, new StubOperable)
    guy.speed = Value(42)
    new FunctionReference("stop")
      .evaluate(guy)
    assertSpeed(0, guy)
  }

  test("test function turn") {
    val runtime = new Runtime
    runtime.saveFunction(Functions.turn)
    val guy = new Operator(runtime, new StubOperable)
    guy.direction = Value(137)
    new FunctionReference("turn")
      .addArg("direction", Value(42))
      .evaluate(guy)
    val dir = guy.direction.asNumber
    assert(dir == 42, "guy should have turned to 42 degrees, actual=" + dir)
  }

  test("test function turn relative") {
    val runtime = new Runtime
    runtime.saveFunction(Functions.turnRelative)
    val guy = new Operator(runtime, new StubOperable)
    guy.direction = Value(137)
    val fun = new FunctionReference("turnRelative")
      .addArg("degrees", Value(60))
    fun.evaluate(guy) // 137 + 60 = 197
    fun.evaluate(guy) // 197 + 60 = 257
    fun.evaluate(guy) // 257 + 60 = 317
    val dir = guy.direction.asNumber
    assert(dir == 317, "guy should have turned to 317 degrees, actual=" + dir)
  }

  test("get angle from (1,1) to (2,1) should be 0 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(Value(2), Value(1)).evaluate(guy).asNumber
    assert(angle == 0, "expected 0, actual=" + angle)
  }

  test("get angle from (1,1) to (1,2) should be 90 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(Value(1), Value(2)).evaluate(guy).asNumber
    assert(angle == 90, "expected 90, actual=" + angle)
  }

  test("get angle from (0,0) to (1,1) should be 45 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(0f, 0f))
    val angle = Functions.getAngleTo(Value(1), Value(1)).evaluate(guy).asNumber
    assert(angle == 45, "expected 45, actual=" + angle)
  }

  test("get angle from (1,1) to (2,2) should be 45 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(Value(2), Value(2)).evaluate(guy).asNumber
    assert(angle == 45, "expected 45, actual=" + angle)
  }

  test("get angle from (1,1) to (-1,-1) should be 225 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(Value(-1), Value(-1)).evaluate(guy).asNumber
    assert(angle == 225, "expected 225, actual=" + angle)
  }

  test("point from (0,0) to (1,0) should set direction to 0 degrees") {
    testPointTowardFunction(1, 0, 0)
  }

  test("point from (0,0) to (1,1) should set direction to 45 degrees") {
    testPointTowardFunction(1, 1, 45)
  }

  test("point from (0,0) to (0,1) should set direction to 90 degrees") {
    testPointTowardFunction(0, 1, 90)
  }

  test("point from (0,0) to (-1,1) should set direction to 135 degrees") {
    testPointTowardFunction(-1, 1, 135)
  }

  test("point from (0,0) to (-1,0) should set direction to 180 degrees") {
    testPointTowardFunction(-1, 0, 180)
  }

  test("point from (0,0) to (-1,-1) should set direction to 225 degrees") {
    testPointTowardFunction(-1, -1, 225)
  }

  test("point from (0,0) to (0,-1) should set direction to 270 degrees") {
    testPointTowardFunction(0, -1, 270)
  }

  test("point from (0,0) to (1,-1) should set direction to 315 degrees") {
    testPointTowardFunction(1, -1, 315)
  }

  private def testPointTowardFunction(x: Int, y: Int, expectedDir: Int) {
    val guy = new Operator(new Runtime, new StubOperable)
    Functions.pointToward(Value(x), Value(y)).evaluate(guy)
    val dir = guy.direction.asNumber
    assert(dir == expectedDir, "guy should have turned to " + expectedDir + " degrees, actual=" + dir)
  }

  private def assertSpeed(expected: Int, guy: Operator) = {
    val speed = guy.speed
    assert(speed == expected, "guy should have speed of " + expected + ", actual=" + speed)
  }
}