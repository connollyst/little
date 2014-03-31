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
    val e = new Evaluation(a, Equals, b).evaluate
    assert(e == false)
  }

  test("test evaluation with string values: A == A") {
    val a = Value("A")
    val e = new Evaluation(a, Equals, a).evaluate
    assert(e == true)
  }

  test("test evaluation with string values: B == B") {
    val b = Value("B")
    val e = new Evaluation(b, Equals, b).evaluate
    assert(e == true)
  }

  /* Evaluate NotEquals with String Values */

  test("test evaluation with string values: A != B") {
    val a = Value("A")
    val b = Value("B")
    val e = new Evaluation(a, NotEquals, b).evaluate
    assert(e == true)
  }

  test("test evaluation with string values: A != A") {
    val a = Value("A")
    val e = new Evaluation(a, NotEquals, a).evaluate
    assert(e == false)
  }

  test("test evaluation with string values: B != B") {
    val b = Value("B")
    val e = new Evaluation(b, NotEquals, b).evaluate
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithBooleans extends FunSuite {

  /* Evaluate Equals with Boolean Values */

  test("test evaluation with boolean values: true == false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Evaluation(t, Equals, f).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: true == true") {
    val t = Value(true)
    val e = new Evaluation(t, Equals, t).evaluate
    assert(e == true)
  }

  test("test evaluation with boolean values: false == false") {
    val f = Value(false)
    val e = new Evaluation(f, Equals, f).evaluate
    assert(e == true)
  }

  /* Evaluate NotEquals with Boolean Values */

  test("test evaluation with boolean values: true != false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Evaluation(t, NotEquals, f).evaluate
    assert(e == true)
  }

  test("test evaluation with boolean values: true != true") {
    val t = Value(true)
    val e = new Evaluation(t, NotEquals, t).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: false != false") {
    val f = Value(false)
    val e = new Evaluation(f, NotEquals, f).evaluate
    assert(e == false)
  }

  /* Evaluate LessThan with Boolean Values */

  test("test evaluation with boolean values: true < false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Evaluation(t, LessThan, f).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: false < true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Evaluation(f, LessThan, t).evaluate
    assert(e == true)
  }

  test("test evaluation with boolean values: true < true") {
    val t = Value(true)
    val e = new Evaluation(t, LessThan, t).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: false < false") {
    val f = Value(false)
    val e = new Evaluation(f, LessThan, f).evaluate
    assert(e == false)
  }

  /* Evaluate GreaterThan with Boolean Values */

  test("test evaluation with boolean values: true > false") {
    val t = Value(true)
    val f = Value(false)
    val e = new Evaluation(t, GreaterThan, f).evaluate
    assert(e == true)
  }

  test("test evaluation with boolean values: false > true") {
    val f = Value(false)
    val t = Value(true)
    val e = new Evaluation(f, GreaterThan, t).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: true > true") {
    val t = Value(true)
    val e = new Evaluation(t, GreaterThan, t).evaluate
    assert(e == false)
  }

  test("test evaluation with boolean values: false > false") {
    val f = Value(false)
    val e = new Evaluation(f, GreaterThan, f).evaluate
    assert(e == false)
  }

}

@RunWith(classOf[JUnitRunner])
class TestEvaluationWithIntegers extends FunSuite {

  /* Evaluate Equals with Integer Values */

  test("test evaluation with int values: 1 == 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(one, Equals, two).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 1 == 1") {
    val one = Value(1)
    val e = new Evaluation(one, Equals, one).evaluate
    assert(e == true)
  }

  test("test evaluation with int values: 2 == 2") {
    val two = Value(2)
    val e = new Evaluation(two, Equals, two).evaluate
    assert(e == true)
  }

  /* Evaluate NotEquals with Integer Values */

  test("test evaluation with int values: 1 != 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(one, NotEquals, two).evaluate
    assert(e == true)
  }

  test("test evaluation with int values: 1 != 1") {
    val one = Value(1)
    val e = new Evaluation(one, NotEquals, one).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 2 != 2") {
    val two = Value(2)
    val e = new Evaluation(two, NotEquals, two).evaluate
    assert(e == false)
  }

  /* Evaluate LessThan with Integer Values */

  test("test evaluation with int values: 1 < 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(one, LessThan, two).evaluate
    assert(e == true)
  }

  test("test evaluation with int values: 2 < 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(two, LessThan, one).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 1 < 1") {
    val one = Value(1)
    val e = new Evaluation(one, LessThan, one).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 2 < 2") {
    val two = Value(2)
    val e = new Evaluation(two, LessThan, two).evaluate
    assert(e == false)
  }

  /* Evaluate GreaterThan with Integer Values */

  test("test evaluation with int values: 1 > 2") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(one, GreaterThan, two).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 1") {
    val one = Value(1)
    val two = Value(2)
    val e = new Evaluation(two, GreaterThan, one).evaluate
    assert(e == true)
  }

  test("test evaluation with int values: 1 > 1") {
    val one = Value(1)
    val e = new Evaluation(one, GreaterThan, one).evaluate
    assert(e == false)
  }

  test("test evaluation with int values: 2 > 2") {
    val two = Value(2)
    val e = new Evaluation(two, GreaterThan, two).evaluate
    assert(e == false)
  }

}