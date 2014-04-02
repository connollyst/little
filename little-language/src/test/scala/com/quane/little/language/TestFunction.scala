package com.quane.little.language

import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunction extends FunSuite {

  test("test function reference with return from print statement") {
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new PrintStatement(Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function reference with return from set statement") {
    val fun = new FunctionDefinition("myFun")
    val pointer = new Pointer("Obj")
    fun.addStep(new SetStatement(pointer, Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test function reference with return from get statement") {
    val fun = new FunctionDefinition("myFun")
    val pointer = new Pointer("Obj")
    fun.addStep(new SetStatement(pointer, Value("A")))
    fun.addStep(new GetStatement(pointer))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}