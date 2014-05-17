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
    val e = new LogicalOperation(a, EvaluationOperator.Equals, b).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: A == A") {
    val a = Value("A")
    val e = new LogicalOperation(a, EvaluationOperator.Equals, a).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: B == B") {
    val b = Value("B")
    val e = new LogicalOperation(b, EvaluationOperator.Equals, b).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with String Values */

  test("test evaluation with string values: A != B") {
    val a = Value("A")
    val b = Value("B")
    val e = new LogicalOperation(a, EvaluationOperator.NotEquals, b).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with string values: A != A") {
    val a = Value("A")
    val e = new LogicalOperation(a, EvaluationOperator.NotEquals, a).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with string values: B != B") {
    val b = Value("B")
    val e = new LogicalOperation(b, EvaluationOperator.NotEquals, b).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithBooleans extends FunSuite {

  /* Evaluate Equals with Boolean Values */

  test("test evaluation with boolean values: true == false") {
    val t = Value(true)
    val f = Value(false)
    val e = new LogicalOperation(t, EvaluationOperator.Equals, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true == true") {
    val t = Value(true)
    val e = new LogicalOperation(t, EvaluationOperator.Equals, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false == false") {
    val f = Value(false)
    val e = new LogicalOperation(f, EvaluationOperator.Equals, f).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Boolean Values */

  test("test evaluation with boolean values: true != false") {
    val t = Value(true)
    val f = Value(false)
    val e = new LogicalOperation(t, EvaluationOperator.NotEquals, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true != true") {
    val t = Value(true)
    val e = new LogicalOperation(t, EvaluationOperator.NotEquals, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false != false") {
    val f = Value(false)
    val e = new LogicalOperation(f, EvaluationOperator.NotEquals, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Boolean Values */

  test("test evaluation with boolean values: true < false") {
    val t = Value(true)
    val f = Value(false)
    val e = new LogicalOperation(t, EvaluationOperator.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < true") {
    val f = Value(false)
    val t = Value(true)
    val e = new LogicalOperation(f, EvaluationOperator.LessThan, t).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: true < true") {
    val t = Value(true)
    val e = new LogicalOperation(t, EvaluationOperator.LessThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false < false") {
    val f = Value(false)
    val e = new LogicalOperation(f, EvaluationOperator.LessThan, f).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Boolean Values */

  test("test evaluation with boolean values: true > false") {
    val t = Value(true)
    val f = Value(false)
    val e = new LogicalOperation(t, EvaluationOperator.GreaterThan, f).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with boolean values: false > true") {
    val f = Value(false)
    val t = Value(true)
    val e = new LogicalOperation(f, EvaluationOperator.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: true > true") {
    val t = Value(true)
    val e = new LogicalOperation(t, EvaluationOperator.GreaterThan, t).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with boolean values: false > false") {
    val f = Value(false)
    val e = new LogicalOperation(f, EvaluationOperator.GreaterThan, f).evaluate(new Runtime)
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithIntegers extends FunSuite {

  /* Evaluate Equals with Integer Values */

  test("test evaluation with int values: 1 == 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(one, EvaluationOperator.Equals, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 == 1") {
    val one = Value(1)
    val e = new LogicalOperation(one, EvaluationOperator.Equals, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 == 2") {
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.Equals, two).evaluate(new Runtime)
    assert(e == true)
  }

  /* Evaluate NotEquals with Integer Values */

  test("test evaluation with int values: 1 != 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(one, EvaluationOperator.NotEquals, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 != 1") {
    val one = Value(1)
    val e = new LogicalOperation(one, EvaluationOperator.NotEquals, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 != 2") {
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.NotEquals, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate LessThan with Integer Values */

  test("test evaluation with int values: 1 < 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(one, EvaluationOperator.LessThan, two).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 2 < 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 1 < 1") {
    val one = Value(1)
    val e = new LogicalOperation(one, EvaluationOperator.LessThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 < 2") {
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.LessThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  /* Evaluate GreaterThan with Integer Values */

  test("test evaluation with int values: 1 > 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(one, EvaluationOperator.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.GreaterThan, one).evaluate(new Runtime)
    assert(e == true)
  }

  test("test evaluation with int values: 1 > 1") {
    val one = Value(1)
    val e = new LogicalOperation(one, EvaluationOperator.GreaterThan, one).evaluate(new Runtime)
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 2") {
    val two = Value(2)
    val e = new LogicalOperation(two, EvaluationOperator.GreaterThan, two).evaluate(new Runtime)
    assert(e == false)
  }

}