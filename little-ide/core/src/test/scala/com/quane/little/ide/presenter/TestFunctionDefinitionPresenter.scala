package com.quane.little.ide.presenter

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name propagates to view") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  /* Test Compilation */

  test("test compiled name (default)") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.name == "???", "expected '???' but got '" + function.name + "'")
  }

  test("test compiled name") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    presenter.name = "newName"
    val function = presenter.compile()
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.paramCount == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
    val function = presenter.compile()
    assert(function.paramCount == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
    val function = presenter.compile()
    assert(function.paramCount == 2)
  }

  test("test compiled with 0 steps") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.stepCount == 0)
  }

  test("test compiled with 1 step") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionReferencePresenter(new MockFunctionReferenceView))
    val function = presenter.compile()
    assert(function.stepCount == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionReferencePresenter(new MockFunctionReferenceView))
      .add(new PrintPresenter(new MockPrintStatementView))
    val function = presenter.compile()
    assert(function.stepCount == 2)
  }

}
