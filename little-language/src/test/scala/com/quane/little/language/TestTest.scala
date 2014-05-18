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
    assertEvaluatesToTrue(new Logical(t, LogicalOperation.And, t))
  }
  test("f && f == f") {
    assertEvaluatesToFalse(new Logical(f, LogicalOperation.And, f))
  }
  test("t && f == f") {
    assertEvaluatesToFalse(new Logical(t, LogicalOperation.And, f))
  }
  test("f && t == f") {
    assertEvaluatesToFalse(new Logical(f, LogicalOperation.And, t))
  }

  // Test OR
  test("t || t == f") {
    assertEvaluatesToTrue(new Logical(t, LogicalOperation.Or, t))
  }
  test("f || f == t") {
    assertEvaluatesToFalse(new Logical(f, LogicalOperation.Or, f))
  }
  test("t || f == t") {
    assertEvaluatesToTrue(new Logical(t, LogicalOperation.Or, f))
  }
  test("f || t == t") {
    assertEvaluatesToTrue(new Logical(f, LogicalOperation.Or, t))
  }

  private def assertEvaluatesToTrue(operation: Logical) = {
    val result = operation.evaluate(new Runtime)
    assertTrue(result.asBool)
  }

  private def assertEvaluatesToFalse(operation: Logical) = {
    val result = operation.evaluate(new Runtime)
    assertFalse(result.asBool)
  }

}
