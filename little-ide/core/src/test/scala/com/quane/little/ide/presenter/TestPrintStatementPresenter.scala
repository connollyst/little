package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.{FunctionReference, GetStatement, Expression}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = PresenterBindingModule

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
    val valueView = mock[ValueView]
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when(view.createValueStatement()).thenReturn(valueView)
    val value = mock[Value]
    presenter.value = value
    verify(view).createValueStatement()
    // TODO test isn't applicable as presenter comes from factory
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val getterView = mock[GetStatementView]
    val getterPresenter = mock[GetterPresenter[GetStatementView]]
    when(view.createGetStatement()).thenReturn(getterView)
    val getter = mock[GetStatement]
    presenter.value = getter
    verify(view).createGetStatement()
    // TODO test isn't applicable as presenter comes from factory
    verify(getterPresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    val functionView = mock[FunctionReferenceView]
    val functionPresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when(view.createFunctionReference()).thenReturn(functionView)
    val function = mock[FunctionReference]
    presenter.value = function
    verify(view).createFunctionReference()
    // TODO test isn't applicable as presenter comes from factory
    verify(functionPresenter).initialize(function)
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
    when(view.createGetStatement()).thenReturn(new MockGetStatementView)
    when(view.createValueStatement()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[PrintStatementView]
    val presenter = new PrintStatementPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile()
    }
  }

}
