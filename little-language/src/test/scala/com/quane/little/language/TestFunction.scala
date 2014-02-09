package com.quane.little.language

import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class TestFunction extends FunSuite {

  test("test new function") {
    new Block(new Runtime)
  }

  /** Test evaluating a function where two objects are created.
    *
    * Assert their stored values are accurate.
    */
  test("test function with new values") {
    val fun = new Block(new Runtime)
    val pointer1 = new Pointer(fun, "Obj1")
    val pointer2 = new Pointer(fun, "Obj2")
    fun.addStep(new Set(pointer1, new Value("A")))
    fun.addStep(new Set(pointer2, new Value("B")))
    fun.evaluate
    val obj1 = fun.fetch("Obj1")
    val obj2 = fun.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "A", "expected Obj1 to be 'A' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  /** Test evaluating a function where two objects are created and then the
    * value of one changed to that of the other.
    *
    * Assert their stored values are accurate.
    */
  test("test function with value assignment") {
    val fun = new Block(new Runtime)
    val pointer1 = new Pointer(fun, "Obj1")
    val pointer2 = new Pointer(fun, "Obj2")
    fun.addStep(new Set(pointer1, new Value("A")))
    fun.addStep(new Set(pointer2, new Value("B")))
    fun.addStep(new Set(pointer1, new Get(pointer2)))
    fun.evaluate
    val obj1 = fun.fetch("Obj1")
    val obj2 = fun.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "B", "expected Obj1 to be 'B' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  test("test function with return from print statement") {
    val fun = new Block(new Runtime)
    fun.addStep(new Print(new Value("A")))
    val obj = fun.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function with return from set statement") {
    val fun = new Block(new Runtime)
    val pointer = new Pointer(fun, "Obj")
    fun.addStep(new Set(pointer, new Value("A")))
    val obj = fun.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function with return from get statement") {
    val fun = new Block(new Runtime)
    val pointer = new Pointer(fun, "Obj")
    fun.addStep(new Set(pointer, new Value("A")))
    fun.addStep(new Get(pointer))
    val obj = fun.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function reference with return from print statement") {
    val fun = new FunctionDefinition(new Runtime, "myFun")
    fun.addStep(new Print(new Value("A")))
    fun.runtime.saveFunction(fun)
    val ref = new FunctionReference(fun, "myFun")
    val obj = ref.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function reference with return from set statement") {
    val fun = new FunctionDefinition(new Runtime, "myFun")
    val pointer = new Pointer(fun, "Obj")
    fun.addStep(new Set(pointer, new Value("A")))
    fun.runtime.saveFunction(fun)
    val ref = new FunctionReference(fun, "myFun")
    val obj = ref.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function reference with return from get statement") {
    val fun = new FunctionDefinition(new Runtime, "myFun")
    val pointer = new Pointer(fun, "Obj")
    fun.addStep(new Set(pointer, new Value("A")))
    fun.addStep(new Get(pointer))
    fun.runtime.saveFunction(fun)
    val ref = new FunctionReference(fun, "myFun")
    val obj = ref.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}