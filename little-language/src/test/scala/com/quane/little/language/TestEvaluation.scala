package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithStrings extends FunSuite {

  /* Evaluate Equals with String Values*/

  test("test evaluation with string values: A == B") {
    val a = Value("A")
    val b = Value("B")
    val e = new Logical(a, LogicalOperation.Equals, b).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: A == A") {
    val a = Value("A")
    val e = new Logical(a, LogicalOperation.Equals, a).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: B == B") {
    val b = Value("B")
    val e = new Logical(b, LogicalOperation.Equals, b).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with String Values */

  test("test evaluation with string values: A != B") {
    val a = Value("A")
    val b = Value("B")
    val e = new Logical(a, LogicalOperation.NotEquals, b).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: A != A") {
    val a = Value("A")
    val e = new Logical(a, LogicalOperation.NotEquals, a).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: B != B") {
    val b = Value("B")
    val e = new Logical(b, LogicalOperation.NotEquals, b).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithBooleans extends FunSuite {

  /* Evaluate Equals with Boolean Values */

  test("test evaluation with boolean values: true == false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logical(t, LogicalOperation.Equals, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true == true") {
    val t = Value(true)
    val e = new Logical(t, LogicalOperation.Equals, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false == false") {
    val f = Value(false)
    val e = new Logical(f, LogicalOperation.Equals, f).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Boolean Values */

  test("test evaluation with boolean values: true != false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logical(t, LogicalOperation.NotEquals, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true != true") {
    val t = Value(true)
    val e = new Logical(t, LogicalOperation.NotEquals, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false != false") {
    val f = Value(false)
    val e = new Logical(f, LogicalOperation.NotEquals, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Boolean Values */

  test("test evaluation with boolean values: true < false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logical(t, LogicalOperation.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Logical(f, LogicalOperation.LessThan, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true < true") {
    val t = Value(true)
    val e = new Logical(t, LogicalOperation.LessThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < false") {
    val f = Value(false)
    val e = new Logical(f, LogicalOperation.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Boolean Values */

  test("test evaluation with boolean values: true > false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Logical(t, LogicalOperation.GreaterThan, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false > true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Logical(f, LogicalOperation.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true > true") {
    val t = Value(true)
    val e = new Logical(t, LogicalOperation.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false > false") {
    val f = Value(false)
    val e = new Logical(f, LogicalOperation.GreaterThan, f).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithIntegers extends FunSuite {

  /* Evaluate Equals with Integer Values */

  test("test evaluation with int values: 1 == 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(one, LogicalOperation.Equals, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 == 1") {
    val one = Value(1)
    val e = new Logical(one, LogicalOperation.Equals, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 == 2") {
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.Equals, two).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Integer Values */

  test("test evaluation with int values: 1 != 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(one, LogicalOperation.NotEquals, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 != 1") {
    val one = Value(1)
    val e = new Logical(one, LogicalOperation.NotEquals, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 != 2") {
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.NotEquals, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Integer Values */

  test("test evaluation with int values: 1 < 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(one, LogicalOperation.LessThan, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 < 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 < 1") {
    val one = Value(1)
    val e = new Logical(one, LogicalOperation.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 < 2") {
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.LessThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Integer Values */

  test("test evaluation with int values: 1 > 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(one, LogicalOperation.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.GreaterThan, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 > 1") {
    val one = Value(1)
    val e = new Logical(one, LogicalOperation.GreaterThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 2") {
    val two = Value(2)
    val e = new Logical(two, LogicalOperation.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

}