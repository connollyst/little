package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, Scope, GetStatement}

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  test("test value is set") {
    val view = new MockPrintStatementView
    val presenter = new PrintStatementPresenter(view)
    presenter.expression = new Value("sean is cool")
    val valuePresenter = presenter.expression.asInstanceOf[ValuePresenter[_ <: ValueView]]
    assert(valuePresenter.value == "sean is cool")
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
    presenter.expression = new Value("sean is cool")
    verify(view).createValueStatement()
    verify(valuePresenter).value = "sean is cool"
  }

  test("test get expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    presenter.expression = new GetStatement(mock[Scope], "sean is cool")
    verify(view).createGetStatement()
    verify(valuePresenter).name = "sean is cool"
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    presenter.expression = new FunctionReference(mock[Scope], "sean is cool")
    verify(view).createFunctionReference()
    verify(valuePresenter).name = "sean is cool"
  }

}
