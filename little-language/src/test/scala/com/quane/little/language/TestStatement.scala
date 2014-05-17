package com.quane.little.language

import com.quane.little.language.TestStatement._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestStatement
  extends FunSuite {

  /* Set Statement */

  test("test setter with value") {
    val set = new Setter("Obj", Value("A"))
    assertSet("Obj", "A", set)
  }
  test("test setter with string") {
    val set = Setter("Obj", "A")
    assertSet("Obj", "A", set)
  }
  test("test setter with boolean") {
    val set = Setter("Obj", true)
    assertSet("Obj", true, set)
  }
  test("test setter with int") {
    val set = Setter("Obj", 1)
    assertSet("Obj", 1, set)
  }
  test("test setter with double") {
    val set = Setter("Obj", 1.0)
    assertSet("Obj", 1.0, set)
  }

  /* Get Statement */

  test("test getter") {
    val scope = new Runtime
    scope.save("Obj", Value("A"))
    val get = new Getter("Obj")
    assertGet("Obj", "A", scope, get)
  }

}

object TestStatement {

  private def assertSet(name: String, value: Any, set: Setter) = {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block += set
    block += snapshot
    block.evaluate(new Runtime)
    val obj = snapshot.scope.fetch(name)
    val objValue = obj.value.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

  /** Assert that the [[Getter]] returns the expected value.
    *
    * @param name the name of the variable
    * @param value the expected value
    * @param scope the scope in which to evaluate
    * @param get the get statement under test
    */
  private def assertGet(name: String, value: Any, scope: Scope, get: Getter) = {
    val block = new Block
    block += get
    val obj = block.evaluate(scope)
    val objValue = obj.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

}