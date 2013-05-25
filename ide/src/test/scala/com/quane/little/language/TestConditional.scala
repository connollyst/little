package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import com.quane.little.language.data.{Value, True, Text}

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite {

  test("test positive conditional") {
    val test = new Value(true)
    val func = new Function(null).addStep(
      new PrintStatement(new Value("Sean is Cool!"))
    )
    val cond = new Conditional(test, func)
    cond.evaluate
  }

}