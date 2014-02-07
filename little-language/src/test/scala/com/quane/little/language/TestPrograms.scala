package com.quane.little.language

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalamock.scalatest.MockFactory

@RunWith(classOf[JUnitRunner])
class TestPrograms extends FunSuite with MockFactory {

  test("test programs: turn relative") {
    val guy = new Operator(new StubOperable)
    val program = Programs.turnRelative(guy, 60)
    program.evaluate // 0 + 60 = 60
    program.evaluate // 60 + 60 = 120
    program.evaluate // 120 + 60 = 180
    val dir = guy.direction.asNumber
    assert(dir == 180, "guy should have turned to 180 degrees, actual=" + dir)
  }

}