package com.quane.little.language

import com.quane.little.language.TestSetter._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestSetter extends FunSuite {

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
}

object TestSetter {

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

}