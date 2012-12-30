package com.quane.glass.core.language

import org.jbox2d.dynamics.Body
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import com.quane.glass.core.Guy
import com.quane.glass.core.language.data.Variable

/** Tests for the {@code Guy} class.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestGuy extends FunSuite {

    test("test guy: functions can access my memory") {
        val guy = new Guy(null)
        val fun1 = new Function(guy)
        guy.save(new Variable("MyA", "A"))
        val obj1 = fun1.fetch("MyA")
        assert(obj1.value == "A", "expected 'MyA' to be 'A' but is: " + obj1.value)
    }

    test("test guy: 'speed' is defined") {
        val guy = new Guy(null)
        val defined = guy.isDefined(Guy.VAR_SPEED);
        assert(defined, "expected '" + Guy.VAR_SPEED + "' to be defined")
    }

    test("test guy: 'direction' is defined") {
        val guy = new Guy(null)
        val defined = guy.isDefined(Guy.VAR_DIRECTION);
        assert(defined, "expected '" + Guy.VAR_DIRECTION + "' to be defined")
    }

    test("test guy: set direction") {
        val guy = new Guy(null)
        guy.setDirection(42)
        assert(guy.direction == 42, "expected direction to be 42 but it was " + guy.direction)
    }

    test("test guy: set direction: low limit") {
        val guy = new Guy(null)
        guy.setDirection(0) // A-Ok
        assert(guy.direction == 0, "expected direction to be 0 but it was " + guy.direction)
    }

    test("test guy: set direction: high limit") {
        val guy = new Guy(null)
        guy.setDirection(360) // is really 0 degrees
        assert(guy.direction == 0, "expected direction to be 0 but it was " + guy.direction)
    }

    test("test guy: set direction: very high") {
        val guy = new Guy(null)
        guy.setDirection(360 * 42 + 137) // modulus should remove the excess
        assert(guy.direction == 137, "expected direction to be 137 but it was " + guy.direction)
    }

    test("test guy: set direction: very low") {
        val guy = new Guy(null)
        guy.setDirection(0 - 137) // negative degrees should loop around
        assert(guy.direction == (360 - 137), "expected direction to be " + (360 - 137) + " but it was " + guy.direction)
    }
}