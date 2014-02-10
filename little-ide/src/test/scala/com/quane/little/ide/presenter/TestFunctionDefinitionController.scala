package com.quane.little.ide.presenter

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionController extends FunSuite {

  test("test compiled name (default)") {
    val presenter = new FunctionDefinitionPresenter
    val function = presenter.compile()
    assert(function.name == "name")
  }

  test("test compiled name") {
    val presenter = new FunctionDefinitionPresenter
    presenter.name = "newName"
    val function = presenter.compile()
    assert(function.name == "newName")
  }

  test("test compiled with 0 steps") {
    val presenter = new FunctionDefinitionPresenter()
    val function = presenter.compile()
    assert(function.block.length == 0)
  }

  test("test compiled with 1 step") {
    val presenter = new FunctionDefinitionPresenter()
      .addStep(new FunctionReferencePresenter)
    val function = presenter.compile()
    assert(function.block.length == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter()
      .addStep(new FunctionReferencePresenter)
      .addStep(new PrintPresenter)
    val function = presenter.compile()
    assert(function.block.length == 2)
  }


}
