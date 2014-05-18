package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.Assert._

@RunWith(classOf[JUnitRunner])
class TestLogicWithBoolean extends FunSuite {

  val t = Value(true)
  val f = Value(false)

  // Test AND
  test("t && t == t") {
    assertEvaluatesToTrue(new Logic(t, LogicOperation.And, t))
  }
  test("f && f == f") {
    assertEvaluatesToFalse(new Logic(f, LogicOperation.And, f))
  }
  test("t && f == f") {
    assertEvaluatesToFalse(new Logic(t, LogicOperation.And, f))
  }
  test("f && t == f") {
    assertEvaluatesToFalse(new Logic(f, LogicOperation.And, t))
  }

  // Test OR
  test("t || t == f") {
    assertEvaluatesToTrue(new Logic(t, LogicOperation.Or, t))
  }
  test("f || f == t") {
    assertEvaluatesToFalse(new Logic(f, LogicOperation.Or, f))
  }
  test("t || f == t") {
    assertEvaluatesToTrue(new Logic(t, LogicOperation.Or, f))
  }
  test("f || t == t") {
    assertEvaluatesToTrue(new Logic(f, LogicOperation.Or, t))
  }

  private def assertEvaluatesToTrue(operation: Logic) = {
    val result = operation.evaluate(new Runtime)
    assertTrue(result.asBool)
  }

  private def assertEvaluatesToFalse(operation: Logic) = {
    val result = operation.evaluate(new Runtime)
    assertFalse(result.asBool)
  }

}
