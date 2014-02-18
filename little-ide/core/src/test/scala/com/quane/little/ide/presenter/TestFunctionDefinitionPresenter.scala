package com.quane.little.ide.presenter

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view.{PrintStatementView, FunctionReferenceView, FunctionParameterView, FunctionDefinitionView}
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
    assert(function.paramCount == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .add(new FunctionParameterPresenter(mock[FunctionParameterView]))
    val function = presenter.compile()
    assert(function.paramCount == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .add(new FunctionParameterPresenter(mock[FunctionParameterView]))
      .add(new FunctionParameterPresenter(mock[FunctionParameterView]))
    val function = presenter.compile()
    assert(function.paramCount == 2)
  }

  test("test compiled with 0 steps") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    val function = presenter.compile()
    assert(function.stepCount == 0)
  }

  test("test compiled with 1 step") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .add(new FunctionReferencePresenter(mock[FunctionReferenceView]))
    val function = presenter.compile()
    assert(function.stepCount == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
      .add(new FunctionReferencePresenter(mock[FunctionReferenceView]))
      .add(new PrintPresenter(mock[PrintStatementView]))
    val function = presenter.compile()
    assert(function.stepCount == 2)
  }


}
