package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, Scope, GetStatement}
import scala.Predef._

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
    presenter.expression = new Value("text")
    verify(view).createValueStatement()
    verify(valuePresenter).value = "text"
  }

  test("test get expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    presenter.expression = new GetStatement(mock[Scope], "varName")
    verify(view).createGetStatement()
    verify(valuePresenter).name = "varName"
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    presenter.expression = new FunctionReference(mock[Scope], "funName")
    verify(view).createFunctionReference()
    verify(valuePresenter).name = "funName"
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

  /* Test Compilation */

  test("should compile print(value expression)") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = new ValuePresenter(new MockValueView)
    when[ValuePresenter[_]](view.createValueStatement())
      .thenReturn(valuePresenter)
    val value = new Value("text")
    presenter.expression = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should compile print(get expression)") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.createGetStatement())
      .thenReturn(valuePresenter)
    val value = new GetStatement(mock[Scope], "varName")
    presenter.expression = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should compile print(function reference)") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.createFunctionReference())
      .thenReturn(valuePresenter)
    val value = new FunctionReference(mock[Scope], "funName")
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
