package com.quane.little.language

import com.quane.little.language.data.{Nada, Value}
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
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new PrintStatement(Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }
  it should "return nada from set statement" in {
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new Setter("Obj", Value("A")))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }
  it should "return value from get expression" in {
    val fun = new FunctionDefinition("myFun")
    fun.addStep(new Setter("Obj", Value("A")))
    fun.addStep(new Getter("Obj"))
    val runtime = new Runtime
    runtime.saveFunction(fun)
    val ref = new FunctionReference("myFun")
    val obj = ref.evaluate(runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}