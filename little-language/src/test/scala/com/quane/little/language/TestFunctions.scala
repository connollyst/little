package com.quane.little.language

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestFunctions extends FunSuite {

  test("test programs: move") {
    val guy = new Operator(new Runtime, new StubOperable)
    Functions.move(guy, new Value(42)).evaluate
    val speed = guy.speed
    assert(speed == 42, "guy should have speed of 42, actual=" + speed)
  }

  test("test programs: stop") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.speed(42)
    Functions.stop(guy).evaluate
    val speed = guy.speed
    assert(speed == 0, "guy should have speed of 0, actual=" + speed)
  }

  test("test programs: turn") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction(new Value(137))
    Functions.turn(guy, new Value(42)).evaluate
    val dir = guy.direction.asInt
    assert(dir == 42, "guy should have turned to 42 degrees, actual=" + dir)
  }

  test("test programs: turn relative") {
    val guy = new Operator(new Runtime, new StubOperable)
    val fun = Functions.turnRelative(guy, 60)
    fun.evaluate // 0 + 60 = 60
    fun.evaluate // 60 + 60 = 120
    fun.evaluate // 120 + 60 = 180
    val dir = guy.direction.asInt
    assert(dir == 180, "guy should have turned to 180 degrees, actual=" + dir)
  }

  test("get angle to from (1,1) to (2,1) should be 0 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(guy, new Value(2), new Value(1)).evaluate
    assert(angle == 0, "expected 0, actual=" + angle.primitive)
  }

  test("get angle to from (1,1) to (1,2) should be 90 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(guy, new Value(1), new Value(2)).evaluate
    assert(angle == 90, "expected 90, actual=" + angle.primitive)
  }

  test("get angle to from (0,0) to (1,1) should be 45 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(0f, 0f))
    val angle = Functions.getAngleTo(guy, new Value(1), new Value(1)).evaluate
    assert(angle == 45, "expected 45, actual=" + angle.primitive)
  }

  test("get angle to from (1,1) to (2,2) should be 45 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(guy, new Value(2), new Value(2)).evaluate
    assert(angle == 45, "expected 45, actual=" + angle.primitive)
  }

  test("get angle to from (1,1) to (-1,-1) should be 225 degrees") {
    val guy = new Operator(new Runtime, new StubOperable(1f, 1f))
    val angle = Functions.getAngleTo(guy, new Value(-1), new Value(-1)).evaluate
    assert(angle == 225, "expected 225, actual=" + angle.primitive)
  }


}