package com.quane.little.language

import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestBlock extends FunSuite {

  test("test new block") {
    new Block(new Runtime)
  }

  /** Test evaluating a block where two objects are created.
    *
    * Assert their stored values are accurate.
    */
  test("test block with new values") {
    val block = new Block(new Runtime)
    block.addStep(new SetStatement(block, "Obj1", "A"))
    block.addStep(new SetStatement(block, "Obj2", "B"))
    block.evaluate
    val obj1 = block.fetch("Obj1")
    val obj2 = block.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "A", "expected Obj1 to be 'A' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  /** Test evaluating a block where two objects are created and then the
    * value of one changed to that of the other.
    *
    * Assert their stored values are accurate.
    */
  test("test block with value assignment") {
    val block = new Block(new Runtime)
    val pointer1 = new Pointer(block, "Obj1")
    val pointer2 = new Pointer(block, "Obj2")
    block.addStep(new SetStatement(pointer1, new Value("A")))
    block.addStep(new SetStatement(pointer2, new Value("B")))
    block.addStep(new SetStatement(pointer1, new GetStatement(pointer2)))
    block.evaluate
    val obj1 = block.fetch("Obj1")
    val obj2 = block.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "B", "expected Obj1 to be 'B' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  test("test block with return from print statement") {
    val block = new Block(new Runtime)
    block.addStep(new PrintStatement(new Value("A")))
    val obj = block.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test block with return from set statement") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj")
    block.addStep(new SetStatement(pointer, new Value("A")))
    val obj = block.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test block with return from get statement") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj")
    block.addStep(new SetStatement(pointer, new Value("A")))
    block.addStep(new GetStatement(pointer))
    val obj = block.evaluate
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}
