package com.quane.little.language

import com.quane.little.language.data.{ValueType, Nada, Value}
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test cases for both the [[FunctionDefinition]] and [[FunctionReference]]
  * classes, asserting aspects of how functions behave in general.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunction extends FlatSpec with ShouldMatchers {

  "FunctionReference" should "return nada from print statement" in {
    // Given
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new Printer(Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    // When
    val obj = new FunctionReference("myFun").evaluate(runtime)
    // Then
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }
  it should "return nada from set statement" in {
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new Setter("Obj", Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    // When
    val obj = new FunctionReference("myFun").evaluate(runtime)
    // Then
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }
  it should "return value from get expression" in {
    // Given
    val fun = new FunctionDefinition("myFun", ValueType.Something)
    fun.addStep(new Setter("Obj", Value("A")))
    fun.addStep(new Getter("Obj"))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    // When
    val obj = new FunctionReference("myFun").evaluate(runtime)
    // Then
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}