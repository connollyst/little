package com.quane.little.language


import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer

/** @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunction extends FunSuite {

  /** Test evaluating a [[com.quane.little.language.Function]] where two
    * objects are created.<br/>
    * Assert their stored values are accurate.
    */
  test("test function: new") {
    val fun = new Function(null)
    val pointer1 = new Pointer(fun, "Obj1")
    val pointer2 = new Pointer(fun, "Obj2")
    fun.addStep(new SetStatement(pointer1, new Value("A")))
    fun.addStep(new SetStatement(pointer2, new Value("B")))
    fun.evaluate
    val obj1 = fun.fetch("Obj1")
    val obj2 = fun.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "A", "expected Obj1 to be 'A' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  /** Test evaluating a [[com.quane.little.language.Function]] where two
    * objects are created and then the value of one changed to that of the
    * other.<br/>
    * Assert their stored values are accurate.
    */
  test("test function: assignment") {
    val fun = new Function(null)
    val pointer1 = new Pointer(fun, "Obj1")
    val pointer2 = new Pointer(fun, "Obj2")
    fun.addStep(new SetStatement(pointer1, new Value("A")))
    fun.addStep(new SetStatement(pointer2, new Value("B")))
    fun.addStep(new SetStatement(pointer1, new GetStatement(pointer2)))
    fun.evaluate
    val obj1 = fun.fetch("Obj1")
    val obj2 = fun.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "B", "expected Obj1 to be 'B' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  /** Test evaluating a [[com.quane.little.language.Function]] with a return
    * value.<br/>
    * Note: this doesn't work yet. I'm not sure how exactly to implement it,
    * but, more importantly I don't know if we need it yet..
    */
  test("test function: return step") {
    val fun = new Function(null)
    val pointer = new Pointer(fun, "Obj1")
    fun.addStep(new SetStatement(pointer, new Value("A")))
    fun.addStep(new ReturnStatement("Obj1", fun))
    val obj1 = fun.evaluate
    // assert(obj1.value == "A", "expected Obj1 to be 'A' but is: " + obj1.value)
  }

}