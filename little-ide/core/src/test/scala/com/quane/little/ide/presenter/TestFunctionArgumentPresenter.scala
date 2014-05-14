package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.{Expression, FunctionReference, GetStatement}
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = PresenterBindingModule

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
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
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
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valueView = mock[FunctionReferenceView]
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when(view.createFunctionReference()).thenReturn(valueView)
    val function = mock[FunctionReference]
    presenter.value = function
    verify(view).createFunctionReference()
    // TODO test isn't applicable as presenter comes from factory
    verify(valuePresenter).initialize(function)
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
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    when(view.createGetStatement()).thenReturn(new MockGetStatementView)
    when(view.createValueStatement()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled == value)
  }

  test("should error if compiled without expression") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile()
    }
  }

}
