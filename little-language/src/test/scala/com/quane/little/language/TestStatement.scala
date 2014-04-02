package com.quane.little.language

import com.quane.little.language.TestStatement._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestStatement
  extends FunSuite {

  /* Set Statement with implicit Pointer */

  test("test set simple value") {
    val set = new SetStatement("Obj1", Value("A"))
    assertSet("Obj1", "A", set)
  }
  test("test set simple string") {
    val set = new SetStatement("Obj1", "A")
    assertSet("Obj1", "A", set)
  }
  test("test set simple boolean") {
    val set = new SetStatement("Obj1", true)
    assertSet("Obj1", true, set)
  }
  test("test set simple int") {
    val set = new SetStatement("Obj1", 1)
    assertSet("Obj1", 1, set)
  }
  test("test set simple double") {
    val set = new SetStatement("Obj1", 1.0)
    assertSet("Obj1", 1.0, set)
  }

  /* Set Statement with explicit Pointer */

  test("test set pointer with value") {
    val pointer = new Pointer("Obj1")
    val set = new SetStatement(pointer, Value("A"))
    assertSet("Obj1", "A", set)
  }
  test("test set pointer with string value") {
    val pointer = new Pointer("Obj1")
    val set = new SetStatement(pointer, "A")
    assertSet("Obj1", "A", set)
  }
  test("test set pointer with boolean value") {
    val pointer = new Pointer("Obj1")
    val set = new SetStatement(pointer, true)
    assertSet("Obj1", true, set)
  }
  test("test set pointer with int value") {
    val pointer = new Pointer("Obj1")
    val set = new SetStatement(pointer, 1)
    assertSet("Obj1", 1, set)
  }
  test("test set pointer with double value") {
    val pointer = new Pointer("Obj1")
    val set = new SetStatement(pointer, 1.0)
    assertSet("Obj1", 1.0, set)
  }

  /* Get Statement with implicit Pointer */

  test("test get with implicit pointer") {
    val scope = new Scope(None)
    scope.save("Obj1", Value("A"))
    val get = new GetStatement("Obj1")
    assertGet("Obj1", "A", scope, get)
  }

  /* Get Statement with explicit Pointer */

  test("test get with explicit pointer") {
    val scope = new Scope(None)
    scope.save("Obj1", Value("A"))
    val pointer = new Pointer("Obj1")
    val get = new GetStatement(pointer)
    assertGet("Obj1", "A", scope, get)
  }

}

object TestStatement {

  private def assertSet(name: String, value: Any, set: SetStatement) = {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block += set
    block += snapshot
    block.evaluate(new Runtime)
    val obj = snapshot.scope.fetch(name)
    val objValue = obj.value.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

  /** Assert that the [[GetStatement]] returns the expected value.
    *
    * @param name the name of the variable
    * @param value the expected value
    * @param scope the scope in which to evaluate
    * @param get the get statement under test
    */
  private def assertGet(name: String, value: Any, scope: Scope, get: GetStatement) = {
    val block = new Block
    block += get
    val obj = block.evaluate(scope)
    val objValue = obj.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

}