package com.quane.little.language

import com.quane.little.language.data.Value
import com.quane.little.language.memory.Pointer
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite with BeforeAndAfter {

  private val name = "VariableName"
  private val value = "VariableValue"
  private val positive = Value(true)
  private val negative = Value(false)

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
    fun.addStep(new SetStatement(pointer, Value(value)))
  }

}