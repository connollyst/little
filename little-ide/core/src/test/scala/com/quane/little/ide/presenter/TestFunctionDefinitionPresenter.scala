package com.quane.little.ide.presenter

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view.FunctionDefinitionView
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends FunSuite with MockitoSugar {

  test("test compiled name (default)") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    val function = presenter.compile()
    assert(function.name == "name")
  }

  test("test compiled name") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    presenter.name = "newName"
    val function = presenter.compile()
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    val function = presenter.compile()
    assert(function.params.length == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .addParam(new FunctionParameterPresenter("x"))
    val function = presenter.compile()
    assert(function.params.length == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .addParam(new FunctionParameterPresenter("x"))
      .addParam(new FunctionParameterPresenter("y"))
    val function = presenter.compile()
    assert(function.params.length == 2)
  }

  test("test compiled with 0 steps") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    val function = presenter.compile()
    assert(function.block.length == 0)
  }

  test("test compiled with 1 step") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .addStep(new FunctionReferencePresenter)
    val function = presenter.compile()
    assert(function.block.length == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .addStep(new FunctionReferencePresenter)
      .addStep(new PrintPresenter)
    val function = presenter.compile()
    assert(function.block.length == 2)
  }


}
