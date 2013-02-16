package com.quane.glass.core.language

import org.jbox2d.dynamics.Body
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.Guy
import com.quane.glass.language.Programs
import com.quane.glass.language.data.Variable
import com.quane.glass.language.data.Text

@RunWith(classOf[JUnitRunner])
class TestPrograms extends FunSuite {

    test("test programs: turn relative") {
        val guy = new Guy(null, null)
        val program = Programs.turnRelative(guy, 60)
        program.evaluate // 0 + 60 = 60
        program.evaluate // 60 + 60 = 120
        program.evaluate // 120 + 60 = 180
        assert(guy.direction == 180, "guy should have turned to 180 degrees")
    }

}