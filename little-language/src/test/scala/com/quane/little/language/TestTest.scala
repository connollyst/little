package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.Assert._

@RunWith(classOf[JUnitRunner])
class TestTest extends FunSuite {

  val t = Value(true)
  val f = Value(false)

  // Test AND
  test("t && t == t") {
    assertEvaluatesToTrue(new LogicalOperation(t, EvaluationOperator.And, t))
  }
  test("f && f == f") {
    assertEvaluatesToFalse(new LogicalOperation(f, EvaluationOperator.And, f))
  }
  test("t && f == f") {
    assertEvaluatesToFalse(new LogicalOperation(t, EvaluationOperator.And, f))
  }
  test("f && t == f") {
    assertEvaluatesToFalse(new LogicalOperation(f, EvaluationOperator.And, t))
  }

  // Test OR
  test("t || t == f") {
    assertEvaluatesToTrue(new LogicalOperation(t, EvaluationOperator.Or, t))
  }
  test("f || f == t") {
    assertEvaluatesToFalse(new LogicalOperation(f, EvaluationOperator.Or, f))
  }
  test("t || f == t") {
    assertEvaluatesToTrue(new LogicalOperation(t, EvaluationOperator.Or, f))
  }
  test("f || t == t") {
    assertEvaluatesToTrue(new LogicalOperation(f, EvaluationOperator.Or, t))
  }

  private def assertEvaluatesToTrue(operation: LogicalOperation) = {
    val result = operation.evaluate(new Runtime)
    assertTrue(result.asBool)
  }

  private def assertEvaluatesToFalse(operation: LogicalOperation) = {
    val result = operation.evaluate(new Runtime)
    assertFalse(result.asBool)
  }

}
