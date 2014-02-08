package com.quane.little.language

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestFunctions extends FunSuite {

  test("test programs: move") {
    val guy = new Operator(new Runtime, new StubOperable)
    val program = Functions.move(guy, new Value(42))
    program.evaluate
    val speed = guy.speed
    assert(speed == 42, "guy should have speed of 42, actual=" + speed)
  }

  test("test programs: stop") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.speed(42)
    val program = Functions.stop(guy)
    program.evaluate
    val speed = guy.speed
    assert(speed == 0, "guy should have speed of 0, actual=" + speed)
  }

  test("test programs: turn") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction(new Value(137))
    val program = Functions.turn(guy, new Value(42))
    program.evaluate
    val dir = guy.direction.asNumber
    assert(dir == 42, "guy should have turned to 42 degrees, actual=" + dir)
  }

  test("test programs: turn relative") {
    val guy = new Operator(new Runtime, new StubOperable)
    val program = Functions.turnRelative(guy, 60)
    program.evaluate // 0 + 60 = 60
    program.evaluate // 60 + 60 = 120
    program.evaluate // 120 + 60 = 180
    val dir = guy.direction.asNumber
    assert(dir == 180, "guy should have turned to 180 degrees, actual=" + dir)
  }

}