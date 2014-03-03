package com.quane.little.ide.presenter

import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.quane.little.ide.view._
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.quane.little.language._
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestSetStatementPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("test name initialized in view") {
    val view = mock[SetStatementView]
    new SetStatementPresenter(view)
    verify(view).setName(anyString())
  }

  test("test name propagates to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test name is set on view event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.onNameChange("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on view event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    presenter.onNameChange("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

  test("test value is set on view event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    assert(presenter.value == value)
  }

  test("test value is not propagated to view on view event") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val value = mock[ExpressionPresenter]
    presenter.onValueChange(value)
    verify(view, never()).createGetStatement()
    verify(view, never()).createValueStatement()
    verify(view, never()).createFunctionReference()
  }

  /* Test setting expressions for the value */

  test("should error when adding unknown expression") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.value = mock[Expression]
    }
  }

  test("test value expression added to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = new ValuePresenter(new MockValueView)
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    presenter.value = new Value("x")
    verify(view).createValueStatement()
  }

  test("test value expression added to presenter") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = new ValuePresenter(new MockValueView)
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    presenter.value = new Value("x")
    assert(presenter.value == valuePresenter)
  }

  test("test value expression initialized") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    val value = new Value("x")
    presenter.value = value
    verify(valuePresenter).initialize(value)
  }

  test("test get statement added to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val getPresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(getPresenter)
    presenter.value = new GetStatement(mock[Scope], "x")
    verify(view).createGetStatement()
  }

  test("test get statement added to presenter") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val getPresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(getPresenter)
    presenter.value = new GetStatement(mock[Scope], "x")
    assert(presenter.value == getPresenter)
  }

  test("test get statement initialized") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val getPresenter = mock[GetStatementPresenter[GetStatementView]]
    when[GetStatementPresenter[_]](view.createGetStatement()).thenReturn(getPresenter)
    val statement = new GetStatement(mock[Scope], "x")
    presenter.value = statement
    verify(getPresenter).initialize(statement)
  }

  test("test function reference added to view") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val functionPresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(functionPresenter)
    presenter.value = new FunctionReference(mock[Scope], "funName")
    verify(view).createFunctionReference()
  }

  test("test function reference added to presenter") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val functionPresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(functionPresenter)
    presenter.value = new FunctionReference(mock[Scope], "funName")
    assert(presenter.value == functionPresenter)
  }

  test("test function reference initialized with value") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(functionPresenter)
    val statement = new FunctionReference(mock[Scope], "funName")
    presenter.value = statement
    verify(functionPresenter).initialize(statement)
  }

  /* Test Compilation */

  test("should compile print(value expression)") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = new ValuePresenter(new MockValueView)
    when[ValuePresenter[_]](view.createValueStatement())
      .thenReturn(valuePresenter)
    val value = new Value("text")
    presenter.value = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should compile print(get expression)") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = new GetStatementPresenter(new MockGetStatementView)
    when[GetStatementPresenter[_]](view.createGetStatement())
      .thenReturn(valuePresenter)
    val value = new GetStatement(mock[Scope], "varName")
    presenter.value = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should compile print(function reference)") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    val valuePresenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    when[FunctionReferencePresenter[_]](view.createFunctionReference())
      .thenReturn(valuePresenter)
    val value = new FunctionReference(mock[Scope], "funName")
    presenter.value = value
    val compiled = presenter.compile(mock[Scope])
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[SetStatementView]
    val presenter = new SetStatementPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile(mock[Scope])
    }
  }

}
