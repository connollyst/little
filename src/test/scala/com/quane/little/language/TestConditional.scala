package com.quane.little.language

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import com.quane.little.language.data.True
import com.quane.little.language.data.Text

@RunWith(classOf[JUnitRunner])
class TestConditional extends FunSuite {

  test("test positive conditional") {
    val test = new True
    val func = new Function(null)
    func.addStep(new PrintStatement(new Text("Sean is Cool!")))
    val cond = new Conditional(test, func)
    cond.evaluate
  }

}