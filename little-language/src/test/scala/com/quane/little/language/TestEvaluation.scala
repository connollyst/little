package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestLogicWithStrings extends FunSuite {

  /* Evaluate Equals with String Values*/

  test("test evaluation with string values: A == B") {
    val a = Value("A")
    val b = Value("B")
    val e = new Logic(a, LogicOperation.Equals, b).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: A == A") {
    val a = Value("A")
    val e = new Logic(a, LogicOperation.Equals, a).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: B == B") {
    val b = Value("B")
    val e = new Logic(b, LogicOperation.Equals, b).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with String Values */

  test("test evaluation with string values: A != B") {
    val a = Value("A")
    val b = Value("B")
    val e = new Logic(a, LogicOperation.NotEquals, b).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: A != A") {
    val a = Value("A")
    val e = new Logic(a, LogicOperation.NotEquals, a).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: B != B") {
    val b = Value("B")
    val e = new Logic(b, LogicOperation.NotEquals, b).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithBooleans extends FunSuite {

  /* Evaluate Equals with Boolean Values */

  test("test evaluation with boolean values: true == false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logic(t, LogicOperation.Equals, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true == true") {
    val t = Value(true)
    val e = new Logic(t, LogicOperation.Equals, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false == false") {
    val f = Value(false)
    val e = new Logic(f, LogicOperation.Equals, f).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Boolean Values */

  test("test evaluation with boolean values: true != false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logic(t, LogicOperation.NotEquals, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true != true") {
    val t = Value(true)
    val e = new Logic(t, LogicOperation.NotEquals, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false != false") {
    val f = Value(false)
    val e = new Logic(f, LogicOperation.NotEquals, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Boolean Values */

  test("test evaluation with boolean values: true < false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logic(t, LogicOperation.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Logic(f, LogicOperation.LessThan, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true < true") {
    val t = Value(true)
    val e = new Logic(t, LogicOperation.LessThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < false") {
    val f = Value(false)
    val e = new Logic(f, LogicOperation.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Boolean Values */

  test("test evaluation with boolean values: true > false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logic(t, LogicOperation.GreaterThan, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false > true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Logic(f, LogicOperation.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true > true") {
    val t = Value(true)
    val e = new Logic(t, LogicOperation.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false > false") {
    val f = Value(false)
    val e = new Logic(f, LogicOperation.GreaterThan, f).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithIntegers extends FunSuite {

  /* Evaluate Equals with Integer Values */

  test("test evaluation with int values: 1 == 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(one, LogicOperation.Equals, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 == 1") {
    val one = Value(1)
    val e = new Logic(one, LogicOperation.Equals, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 == 2") {
    val two = Value(2)
    val e = new Logic(two, LogicOperation.Equals, two).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Integer Values */

  test("test evaluation with int values: 1 != 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(one, LogicOperation.NotEquals, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 != 1") {
    val one = Value(1)
    val e = new Logic(one, LogicOperation.NotEquals, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 != 2") {
    val two = Value(2)
    val e = new Logic(two, LogicOperation.NotEquals, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Integer Values */

  test("test evaluation with int values: 1 < 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(one, LogicOperation.LessThan, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 < 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(two, LogicOperation.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 < 1") {
    val one = Value(1)
    val e = new Logic(one, LogicOperation.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 < 2") {
    val two = Value(2)
    val e = new Logic(two, LogicOperation.LessThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Integer Values */

  test("test evaluation with int values: 1 > 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(one, LogicOperation.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logic(two, LogicOperation.GreaterThan, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 > 1") {
    val one = Value(1)
    val e = new Logic(one, LogicOperation.GreaterThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 2") {
    val two = Value(2)
    val e = new Logic(two, LogicOperation.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

}