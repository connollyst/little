package com.quane.little.language

import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite with BeforeAndAfter {

  private val name = "VariableName"
  private val value = "VariableValue"
  private val positive = new Value(true)
  private val negative = new Value(false)

  /** The conditional test evaluates to true; the expression should be evaluated.
    */
  test("test condition evaluation: positive") {
    val fun = createTestBlock
    new Conditional(positive, fun).evaluate
    assert(fun.fetch(name).value.asText == value)
  }

  /** The conditional test evaluates to false; the expression should not be evaluated.
    */
  test("test condition evaluation: negative") {
    val fun = createTestBlock
    new Conditional(negative, fun).evaluate
    assert(fun.fetch(name).value.asText != value)
  }

  private def createTestBlock: Block = {
    val fun = new Block(new Runtime)
    val pointer = new Pointer(fun, name)
    fun.addStep(new Set(pointer, new Value(value)))
  }

}