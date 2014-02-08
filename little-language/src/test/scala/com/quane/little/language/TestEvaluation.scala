package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestEvaluation extends FunSuite with BeforeAndAfter {

  /* Evaluate Equals with String Values*/

  test("test evaluation with string values: A == B") {
    val a = new Value("A")
    val b = new Value("B")
    val e = new Evaluation(a, Equals, b).evaluate
    assert(e == false, "expected evaluation to false")
  }

  test("test evaluation with string values: A == A") {
    val a = new Value("A")
    val e = new Evaluation(a, Equals, a).evaluate
    assert(e == true, "expected evaluation to true")
  }

  test("test evaluation with string values: B == B") {
    val b = new Value("B")
    val e = new Evaluation(b, Equals, b).evaluate
    assert(e == true, "expected evaluation to true")
  }

  /* Evaluate NotEquals with String Values */

  test("test evaluation with string values: A != B") {
    val a = new Value("A")
    val b = new Value("B")
    val e = new Evaluation(a, NotEquals, b).evaluate
    assert(e == true, "expected evaluation to false")
  }

  test("test evaluation with string values: A != A") {
    val a = new Value("A")
    val e = new Evaluation(a, NotEquals, a).evaluate
    assert(e == false, "expected evaluation to true")
  }

  test("test evaluation with string values: B != B") {
    val b = new Value("B")
    val e = new Evaluation(b, NotEquals, b).evaluate
    assert(e == false, "expected evaluation to true")
  }

  /* Evaluate Equals with Boolean Values */

  test("test evaluation with boolean values: true == false") {
    val t = new Value(true)
    val f = new Value(false)
    val e = new Evaluation(t, Equals, f).evaluate
    assert(e == false, "expected evaluation to false")
  }

  test("test evaluation with boolean values: true == true") {
    val t = new Value(true)
    val e = new Evaluation(t, Equals, t).evaluate
    assert(e == true, "expected evaluation to true")
  }

  test("test evaluation with boolean values: false == false") {
    val f = new Value(false)
    val e = new Evaluation(f, Equals, f).evaluate
    assert(e == true, "expected evaluation to true")
  }

  /* Evaluate NotEquals with Boolean Values */

  test("test evaluation with boolean values: true != false") {
    val t = new Value(true)
    val f = new Value(false)
    val e = new Evaluation(t, NotEquals, f).evaluate
    assert(e == true, "expected evaluation to false")
  }

  test("test evaluation with boolean values: true != true") {
    val t = new Value(true)
    val e = new Evaluation(t, NotEquals, t).evaluate
    assert(e == false, "expected evaluation to true")
  }

  test("test evaluation with boolean values: false != false") {
    val f = new Value(false)
    val e = new Evaluation(f, NotEquals, f).evaluate
    assert(e == false, "expected evaluation to true")
  }

}
