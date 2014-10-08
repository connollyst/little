package com.quane.little.language

import com.quane.little.language.TestGetter._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestGetter extends FunSuite {
  /* Getter */

  test("test getter") {
    val scope = new Runtime
    scope.save("Obj", Value("A"))
    val get = new Getter("Obj")
    assertGet("Obj", "A", scope, get)
  }

}

object TestGetter {

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