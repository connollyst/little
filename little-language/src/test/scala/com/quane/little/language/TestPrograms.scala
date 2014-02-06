package com.quane.little.language

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite

@RunWith(classOf[JUnitRunner])
class TestPrograms extends FunSuite {

  test("test programs: turn relative") {
    val guy = new Operator(null)
    val program = Programs.turnRelative(guy, 60)
    program.evaluate // 0 + 60 = 60
    program.evaluate // 60 + 60 = 120
    program.evaluate // 120 + 60 = 180
    assert(guy.direction.asNumber == 180, "guy should have turned to 180 degrees")
  }

}