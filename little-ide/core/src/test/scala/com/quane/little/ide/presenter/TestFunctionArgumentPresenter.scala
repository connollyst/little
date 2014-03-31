package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

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
    verify(view).registerViewPresenter(presenter)
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
    val value = mock[Value]
    presenter.value = value
    verify(view).createValueStatement()
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    val getter = mock[GetStatement]
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

  test("should compile print(value expression)") {
    assertCompiledValue(Value("text"))
  }

  test("should compile print(get expression)") {
    assertCompiledValue(new GetStatement(mock[Scope], "varName"))
  }

  test("should compile print(function reference)") {
    assertCompiledValue(new FunctionReference(mock[Scope], "funName"))
  }

  private def assertCompiledValue(value: Expression) = {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    when[GetStatementPresenter[_]](view.createGetStatement())
      .thenReturn(new GetStatementPresenter(new MockGetStatementView))
    when[ValuePresenter[_]](view.createValueStatement())
      .thenReturn(new ValuePresenter(new MockValueView))
    when[FunctionReferencePresenter[_]](view.createFunctionReference())
      .thenReturn(new FunctionReferencePresenter(new MockFunctionReferenceView))
    presenter.value = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled == value)
  }

  test("should error if compiled without expression") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile(mock[Scope])
    }
  }

}
