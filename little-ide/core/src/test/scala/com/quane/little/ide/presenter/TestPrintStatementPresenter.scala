package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.data.Value
import com.quane.little.language.{Expression, FunctionReference, GetStatement}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  test("test value is set") {
    val view = new MockPrintStatementView
    val presenter = new PrintStatementPresenter(view)
    presenter.value = Value("text")
    val valuePresenter = presenter.value.asInstanceOf[ValuePresenter[_ <: ValueView]]
    assert(valuePresenter.value == "text")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("test value expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    val value = mock[Value]
    presenter.value = value
    verify(view).createValueStatement()
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    val getter = mock[GetStatement]
    presenter.value = getter
    verify(view).createGetStatement()
    verify(valuePresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    val function = mock[FunctionReference]
    presenter.value = function
    verify(view).createFunctionReference()
    verify(valuePresenter).initialize(function)
  }

  /* Test setting expressions for the value */

  test("should error when adding unknown expression") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.value = mock[Expression]
    }
  }

  /* Test Compilation */

  test("should compile print(value expression)") {
    assertCompiledValue(Value("text"))
  }

  test("should compile print(get expression)") {
    assertCompiledValue(new GetStatement("varName"))
  }

  test("should compile print(function reference)") {
    assertCompiledValue(new FunctionReference("funName"))
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
    presenter.value = value
    val compiled = presenter.compile
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile
    }
  }

}
