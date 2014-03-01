package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Value
import com.quane.little.language.{Expression, FunctionReference, Scope, GetStatement}
import scala.Predef._
import org.mockito.Matchers.{eq => the}

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  test("test value is set") {
    val view = new MockPrintStatementView
    val presenter = new PrintStatementPresenter(view)
    presenter.expression = new Value("text")
    val valuePresenter = presenter.expression.asInstanceOf[ValuePresenter[_ <: ValueView]]
    assert(valuePresenter.value == "text")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test value expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    val value = new Value("text")
    presenter.expression = value
    verify(view).createValueStatement()
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    val getter = new GetStatement(mock[Scope], "varName")
    presenter.expression = getter
    verify(view).createGetStatement()
    verify(valuePresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    val function = mock[FunctionReference]
    presenter.expression = function
    verify(view).createFunctionReference()
    verify(valuePresenter).initialize(function)
  }

  test("test value is set on view event") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    assert(presenter.expression == value)
  }

  test("test value is not propagated to view on view event") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    verify(view, never()).createGetStatement()
    verify(view, never()).createValueStatement()
    verify(view, never()).createFunctionReference()
  }

  /* Test setting expressions for the value */

  test("should error when adding unknown expression") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.expression = mock[Expression]
    }
  }

  /* Test Compilation */

  test("should compile print(value expression)") {
    assertCompiledValue(new Value("text"))
  }

  test("should compile print(get expression)") {
    assertCompiledValue(new GetStatement(mock[Scope], "varName"))
  }

  test("should compile print(function reference)") {
    assertCompiledValue(new FunctionReference(mock[Scope], "funName"))
  }

  private def assertCompiledValue(value: Expression) = {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    when[GetStatementPresenter[_]](view.createGetStatement())
      .thenReturn(new GetStatementPresenter(new MockGetStatementView))
    when[ValuePresenter[_]](view.createValueStatement())
      .thenReturn(new ValuePresenter(new MockValueView))
    when[FunctionReferencePresenter[_]](view.createFunctionReference())
      .thenReturn(new FunctionReferencePresenter(new MockFunctionReferenceView))
    presenter.expression = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile(mock[Scope])
    }
  }

}
