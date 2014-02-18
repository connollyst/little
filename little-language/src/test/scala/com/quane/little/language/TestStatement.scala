package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import com.quane.little.language.memory.Pointer
import com.quane.little.language.data.Value
import com.quane.little.language.TestStatement._

@RunWith(classOf[JUnitRunner])
class TestStatement extends FunSuite {

  /* Set Statement with implicit Pointer */

  test("test set simple value") {
    val block = new Block(new Runtime)
    val set = new SetStatement(block, "Obj1", new Value("A"))
    assertSet("Obj1", "A", block, set)
  }
  test("test set simple string") {
    val block = new Block(new Runtime)
    val set = new SetStatement(block, "Obj1", "A")
    assertSet("Obj1", "A", block, set)
  }
  test("test set simple boolean") {
    val block = new Block(new Runtime)
    val set = new SetStatement(block, "Obj1", true)
    assertSet("Obj1", true, block, set)
  }
  test("test set simple int") {
    val block = new Block(new Runtime)
    val set = new SetStatement(block, "Obj1", 1)
    assertSet("Obj1", 1, block, set)
  }
  test("test set simple double") {
    val block = new Block(new Runtime)
    val set = new SetStatement(block, "Obj1", 1.0)
    assertSet("Obj1", 1.0, block, set)
  }

  /* Set Statement with explicit Pointer */

  test("test set pointer with value") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj1")
    val set = new SetStatement(pointer, new Value("A"))
    assertSet("Obj1", "A", block, set)
  }
  test("test set pointer with string value") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj1")
    val set = new SetStatement(pointer, "A")
    assertSet("Obj1", "A", block, set)
  }
  test("test set pointer with boolean value") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj1")
    val set = new SetStatement(pointer, true)
    assertSet("Obj1", true, block, set)
  }
  test("test set pointer with int value") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj1")
    val set = new SetStatement(pointer, 1)
    assertSet("Obj1", 1, block, set)
  }
  test("test set pointer with double value") {
    val block = new Block(new Runtime)
    val pointer = new Pointer(block, "Obj1")
    val set = new SetStatement(pointer, 1.0)
    assertSet("Obj1", 1.0, block, set)
  }

  /* Get Statement with implicit Pointer */

  test("test get with implicit pointer") {
    val block = new Block(new Runtime)
    block.scope.save("Obj1", new Value("A"))
    val get = new GetStatement(block, "Obj1")
    assertGet("Obj1", "A", block, get)
  }

  /* Get Statement with explicit Pointer */

  test("test get with explicit pointer") {
    val block = new Block(new Runtime)
    block.scope.save("Obj1", new Value("A"))
    val pointer = new Pointer(block, "Obj1")
    val get = new GetStatement(pointer)
    assertGet("Obj1", "A", block, get)
  }

}

object TestStatement {

  private def assertSet(name: String, value: Any, block: Block, set: SetStatement) = {
    block.addStep(set)
    block.evaluate
    val obj = block.fetch(name)
    val objValue = obj.value.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

  private def assertGet(name: String, value: Any, block: Block, get: GetStatement) = {
    block.addStep(get)
    val obj = block.evaluate
    val objValue = obj.primitive
    assert(objValue == value, "expected " + value + ", found " + objValue)
  }

}