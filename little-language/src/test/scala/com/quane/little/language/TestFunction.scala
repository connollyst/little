package com.quane.little.language

import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class TestFunction extends FunSuite {

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