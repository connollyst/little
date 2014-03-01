package com.quane.little.ide.presenter

import com.quane.little.language.{FunctionReference, Scope, GetStatement, Runtime}
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.ide.view._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val presenter = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test value expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    val value = new Value("text")
    presenter.value = value
    verify(view).createValueStatement()
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    val getter = new GetStatement(mock[Scope], "varName")
    presenter.value = getter
    verify(view).createGetStatement()
    verify(valuePresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    val function = mock[FunctionReference]
    presenter.value = function
    verify(view).createFunctionReference()
    verify(valuePresenter).initialize(function)
  }

  test("test value is set on view event") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    assert(presenter.value == value)
  }

  test("test value is not propagated to view on view event") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    verify(view, never()).createGetStatement()
    verify(view, never()).createValueStatement()
    verify(view, never()).createFunctionReference()
  }

  /* Test Compilation */

  test("test compiled string value (default)") {
    val view = new MockFunctionArgumentView
    val presenter = new FunctionArgumentPresenter(view)
    val scope = new Runtime
    val argument = presenter.compile(scope)
    val value = argument.evaluate
    assert(value == "", "expected '' but got '" + argument + "'")
  }

  test("test compiled boolean value (default)") {
    val view = new MockFunctionArgumentView
    val presenter = new FunctionArgumentPresenter(view)
    val scope = new Runtime
    val argument = presenter.compile(scope)
    val value = argument.evaluate
    assert(value == false, "expected '' but got '" + argument + "'")
  }

  test("test compiled int value (default)") {
    val view = new MockFunctionArgumentView
    val presenter = new FunctionArgumentPresenter(view)
    val scope = new Runtime
    val argument = presenter.compile(scope)
    val value = argument.evaluate
    assert(value == 0, "expected '' but got '" + argument + "'")
  }

  test("test compiled double value (default)") {
    val view = new MockFunctionArgumentView
    val presenter = new FunctionArgumentPresenter(view)
    val scope = new Runtime
    val argument = presenter.compile(scope)
    val value = argument.evaluate
    assert(value == 0.0, "expected '' but got '" + argument + "'")
  }

}
