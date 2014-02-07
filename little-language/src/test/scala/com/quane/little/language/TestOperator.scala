package com.quane.little.language


import com.quane.little.language.data.{Value, Variable}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/** Tests for the [[com.quane.little.language.Operator]] class.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestOperator extends FunSuite {


  test("test guy: functions can access my memory") {
    val guy = new Operator(new StubOperable)
    val fun1 = new Function(guy)
    guy.save(new Variable("MyA", new Value("A")))
    val obj1 = fun1.fetch("MyA")
    assert(obj1.value.primitive == "A", "expected 'MyA' to be 'A' but is: " + obj1.value)
  }

  test("test guy: 'speed' is defined") {
    val guy = new Operator(new StubOperable)
    val defined = guy.isDefined(Operable.VAR_SPEED)
    assert(defined, "expected '" + Operable.VAR_SPEED + "' to be defined")
  }

  test("test guy: 'direction' is defined") {
    val guy = new Operator(new StubOperable)
    val defined = guy.isDefined(Operable.VAR_DIRECTION)
    assert(defined, "expected '" + Operable.VAR_DIRECTION + "' to be defined")
  }

  test("test guy: set direction") {
    val guy = new Operator(new StubOperable)
    guy.direction(new Value(42))
    assert(guy.direction == 42, "expected direction to be 42 but it was " + guy.direction)
  }

  test("test guy: set direction: low limit") {
    val guy = new Operator(new StubOperable)
    guy.direction(new Value(0)) // A-Ok
    assert(guy.direction == 0, "expected direction to be 0 but it was " + guy.direction)
  }

  test("test guy: set direction: high limit") {
    val guy = new Operator(new StubOperable)
    guy.direction(new Value(360)) // is really 0 degrees
    assert(guy.direction == 0, "expected direction to be 0 but it was " + guy.direction)
  }

  test("test guy: set direction: very high") {
    val guy = new Operator(new StubOperable)
    guy.direction(new Value(360 * 42 + 137)) // modulus should remove the excess
    assert(guy.direction == 137, "expected direction to be 137 but it was " + guy.direction)
  }

  test("test guy: set direction: very low") {
    val guy = new Operator(new StubOperable)
    guy.direction(new Value(0 - 137)) // negative degrees should loop around
    assert(guy.direction == (360 - 137), "expected direction to be " + (360 - 137) + " but it was " + guy.direction)
  }

}