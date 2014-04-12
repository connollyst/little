package com.quane.little.language

import com.quane.little.language.data.{Nada, Value}
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/** Test cases for the [[Block]] expression.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlock
  extends FunSuite {

  test("test new block") {
    new Block
  }

  test("evaluate with no steps returns Nada") {
    val output = new Block().evaluate(new Runtime)
    assert(output.isInstanceOf[Nada])
  }

  /** Test evaluating a block where two objects are created.
    *
    * Assert their stored values are accurate.
    */
  test("test block with new values") {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block.addStep( SetStatement("Obj1", "A"))
    block.addStep( SetStatement("Obj2", "B"))
    block.addStep(snapshot)
    block.evaluate(new Runtime)
    val obj1 = snapshot.scope.fetch("Obj1")
    val obj2 = snapshot.scope.fetch("Obj2")
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
    val snapshot = new ScopeSnapshot
    val block = new Block
    block.addStep(new SetStatement("Obj1", Value("A")))
    block.addStep(new SetStatement("Obj2", Value("B")))
    block.addStep(new SetStatement("Obj1", new GetStatement("Obj2")))
    block.addStep(snapshot)
    block.evaluate(new Runtime)
    val obj1 = snapshot.scope.fetch("Obj1")
    val obj2 = snapshot.scope.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "B", "expected Obj1 to be 'B' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }

  test("test block with return from print statement") {
    val block = new Block
    block.addStep(new PrintStatement(Value("A")))
    val obj = block.evaluate(new Runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test block with return from set statement") {
    val block = new Block
    block.addStep(new SetStatement("Obj", Value("A")))
    val obj = block.evaluate(new Runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

  test("test block with return from get statement") {
    val block = new Block
    block.addStep(new SetStatement("Obj", Value("A")))
    block.addStep(new GetStatement("Obj"))
    val obj = block.evaluate(new Runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }

}
