package com.quane.little.ide.presenter

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends FunSuite {

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

  test("test compiled with 0 parameters") {
    val presenter = new FunctionDefinitionPresenter()
    val function = presenter.compile()
    assert(function.params.length == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter()
      .addParam(new FunctionParameterPresenter("x"))
    val function = presenter.compile()
    assert(function.params.length == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter()
      .addParam(new FunctionParameterPresenter("x"))
      .addParam(new FunctionParameterPresenter("y"))
    val function = presenter.compile()
    assert(function.params.length == 2)
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
