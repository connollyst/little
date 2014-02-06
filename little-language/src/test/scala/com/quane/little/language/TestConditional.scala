package com.quane.little.language

import com.quane.little.language.data.Value
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite {

  val pos = new Value(true)
  val neg = new Value(false)

  test("test positive conditional") {
    val func = new Function().addStep(
      new PrintStatement(new Value("Sean is Cool!"))
    )
    val cond = new Conditional(pos, func)
    cond.evaluate
    // TODO assert function was evaluated
    fail("TODO implement test")
  }

  test("test negative conditional") {
    val func = new Function().addStep(
      new PrintStatement(new Value("Sean is Cool!"))
    )
    val cond = new Conditional(neg, func)
    cond.evaluate
    // TODO assert function was NOT evaluated
    fail("TODO implement test")
  }

}