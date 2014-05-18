package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.{Expression, FunctionReference, Getter}
import com.quane.little.language.data.Value
import com.quane.little.ide.IDEBindingModule

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = IDEBindingModule

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
    when(view.createValueExpression()).thenReturn(valueView)
    val value = mock[Value]
    presenter.value = value
    verify(view).createValueExpression()
    // TODO test isn't applicable as presenter comes from factory
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val getterView = mock[GetterView]
    val getterPresenter = mock[GetterPresenter[GetterView]]
    when(view.createGetExpression()).thenReturn(getterView)
    val getter = mock[Getter]
    presenter.value = getter
    verify(view).createGetExpression()
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
    assertCompiledValue(new Getter("varName"))
  }

  test("should compile print(function reference)") {
    assertCompiledValue(new FunctionReference("funName"))
  }

  private def assertCompiledValue(value: Expression) = {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    when(view.createGetExpression()).thenReturn(new MockGetterView)
    when(view.createValueExpression()).thenReturn(new MockValueView)
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
