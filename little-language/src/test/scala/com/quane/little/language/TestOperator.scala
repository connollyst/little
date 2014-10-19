package com.quane.little.language


import com.quane.little.language.data.{Value, Variable}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

/** Tests for the [[com.quane.little.language.Operator]] class.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestOperator extends FunSuite {


  test("blocks (sub scope) can access operator memory") {
    val operator = new Operator(new Runtime, new StubOperable)
    operator.save(new Variable("x", Value(1234)))
    val subScope = new Block
    subScope += new Getter("x")
    val x = subScope.evaluate(operator)
    assert(x.primitive == 1234, "expected 'x' to be '1234' but is: " + x)
  }

  test("'speed' is defined") {
    val guy = new Operator(new Runtime, new StubOperable)
    val defined = guy.contains(Operable.SPEED)
    assert(defined, "expected '" + Operable.SPEED + "' to be defined")
  }

  test("'direction' is defined") {
    val guy = new Operator(new Runtime, new StubOperable)
    val defined = guy.contains(Operable.DIRECTION)
    assert(defined, "expected '" + Operable.DIRECTION + "' to be defined")
  }

  test("set direction") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction = Value(42)
    assert(guy.direction.asNumber == 42, "expected direction to be 42 but it was " + guy.direction)
  }

  test("set direction: low limit") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction = Value(0) // A-Ok
    assert(guy.direction.asNumber == 0, "expected direction to be 0 but it was " + guy.direction)
  }

  test("set direction: high limit") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction = Value(360) // is really 0 degrees
    assert(guy.direction.asNumber == 0, "expected direction to be 0 but it was " + guy.direction)
  }

  test("set direction: very high") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction = Value(360 * 42 + 137) // modulus should remove the excess
    assert(guy.direction.asNumber == 137, "expected direction to be 137 but it was " + guy.direction)
  }

  test("set direction: very low") {
    val guy = new Operator(new Runtime, new StubOperable)
    guy.direction = Value(0 - 137) // negative degrees should loop around
    assert(guy.direction.asNumber == (360 - 137), "expected direction to be " + (360 - 137) + " but it was " + guy.direction)
  }

}