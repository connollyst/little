package com.quane.little.language

import com.quane.little.language.data.{Value, Nada}
import org.junit.runner.RunWith
import org.scalatest.FlatSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

/** Test cases for the [[Block]] expression.
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestBlock extends FlatSpec with ShouldMatchers {

  "Block" should "should create without arguments" in {
    new Block
  }
  it should "returns Nada when evaluated with no steps" in {
    val output = new Block().evaluate(new Runtime)
    output.getClass should be(classOf[Nada])
  }
  /* Test evaluating a block where two objects are created.
    * Assert their stored values are accurate.
    */
  it should "store and fetch values accurately" in {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block.addStep(Setter("Obj1", "A"))
    block.addStep(Setter("Obj2", "B"))
    block.addStep(snapshot)
    block.evaluate(new Runtime)
    val obj1 = snapshot.scope.fetch("Obj1")
    val obj2 = snapshot.scope.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "A", "expected Obj1 to be 'A' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }
  /* Test evaluating a block where two objects are created and then the
    * value of one changed to that of the other.
    * Assert their stored values are accurate.
    */
  it should "update values accurately" in {
    val snapshot = new ScopeSnapshot
    val block = new Block
    block.addStep(new Setter("Obj1", Value("A")))
    block.addStep(new Setter("Obj2", Value("B")))
    block.addStep(new Setter("Obj1", new Getter("Obj2")))
    block.addStep(snapshot)
    block.evaluate(new Runtime)
    val obj1 = snapshot.scope.fetch("Obj1")
    val obj2 = snapshot.scope.fetch("Obj2")
    val obj1Value = obj1.value.primitive
    val obj2Value = obj2.value.primitive
    assert(obj1Value == "B", "expected Obj1 to be 'B' but is: " + obj1Value)
    assert(obj2Value == "B", "expected Obj2 to be 'B' but is: " + obj2Value)
  }
  it should "return value from get expression" in {
    val block = new Block
    block.addStep(new Setter("Obj", Value("A")))
    block.addStep(new Getter("Obj"))
    val obj = block.evaluate(new Runtime)
    assert(obj.asText == "A", "expected return value to be 'A' but is: " + obj)
  }
  it should "return nada when ending in a print statement" in {
    val block = new Block
    block.addStep(new Printer(Value("A")))
    val obj = block.evaluate(new Runtime)
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }
  it should "return nada when ending in a set statement" in {
    val block = new Block
    block.addStep(new Setter("Obj", Value("A")))
    val obj = block.evaluate(new Runtime)
    assert(obj.getClass == classOf[Nada], "expected return value to be 'Nada' but is: " + obj)
  }

}
