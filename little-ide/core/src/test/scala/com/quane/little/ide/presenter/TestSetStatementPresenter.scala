package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value
import com.quane.little.language.{FunctionReference, Getter, Expression}
import com.quane.little.ide.IDEBindingModule

@RunWith(classOf[JUnitRunner])
class TestSetStatementPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = IDEBindingModule

  test("test name is set") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("test name initialized in view") {
    val view = mock[SetterView]
    new SetterPresenter(view)
    verify(view).setName(anyString())
  }

  test("test name propagates to view") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test name is set on view event") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    presenter.onNameChange("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on view event") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    presenter.onNameChange("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

  /* Test setting expressions for the value */

  test("should error when adding unknown expression") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.value = mock[Expression]
    }
  }

  test("test value expression added to view") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val valueView = mock[ValueView]
    when(view.createValueExpression()).thenReturn(valueView)
    presenter.value = Value("x")
    verify(view).createValueExpression()
  }

  test("test value expression added to presenter") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val valueView = mock[ValueView]
    val valuePresenter = new ValuePresenter(valueView)
    when(view.createValueExpression()).thenReturn(valueView)
    presenter.value = Value("x")
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.value == valuePresenter)
  }

  test("test value expression initialized") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val valueView = mock[ValueView]
    val valuePresenter = new ValuePresenter(valueView)
    when(view.createValueExpression()).thenReturn(valueView)
    val value = Value("x")
    presenter.value = value
    // TODO test isn't applicable as presenter comes from factory
    verify(valuePresenter).initialize(value)
  }

  test("test get statement added to view") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.createGetExpression()).thenReturn(mock[GetterView])
    presenter.value = new Getter("x")
    verify(view).createGetExpression()
  }

  test("test get statement added to presenter") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val getterView = mock[GetterView]
    val getterPresenter = new GetterPresenter(getterView)
    when(view.createGetExpression()).thenReturn(getterView)
    presenter.value = new Getter("x")
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.value == getterPresenter)
  }

  test("test get statement initialized") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val getterView = mock[GetterView]
    val getterPresenter = mock[GetterPresenter[GetterView]]
    when(view.createGetExpression()).thenReturn(getterView)
    val statement = new Getter("x")
    presenter.value = statement
    // TODO test isn't applicable as presenter comes from factory
    verify(getterPresenter).initialize(statement)
  }

  test("test function reference added to view") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.createFunctionReference()).thenReturn(mock[FunctionReferenceView])
    presenter.value = new FunctionReference("funName")
    verify(view).createFunctionReference()
  }

  test("test function reference added to presenter") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val functionView = new MockFunctionReferenceView
    val functionPresenter = new FunctionReferencePresenter(functionView)
    when(view.createFunctionReference()).thenReturn(functionView)
    presenter.value = new FunctionReference("funName")
    // TODO test isn't applicable as presenter comes from factory
    assert(presenter.value == functionPresenter)
  }

  test("test function reference initialized with value") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    val functionView = new MockFunctionReferenceView
    val functionPresenter = new FunctionReferencePresenter(functionView)
    when(view.createFunctionReference()).thenReturn(functionView)
    val statement = new FunctionReference("funName")
    presenter.value = statement
    // TODO test isn't applicable as presenter comes from factory
    verify(functionPresenter).initialize(statement)
  }

  /* Test Compilation */

  test("should compile print(value expression)") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.createValueExpression()).thenReturn(mock[ValueView])
    val value = Value("text")
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  test("should compile print(get expression)") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.createGetExpression()).thenReturn(mock[GetterView])
    val value = new Getter("varName")
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  test("should compile print(function reference)") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    when(view.createFunctionReference()).thenReturn(mock[FunctionReferenceView])
    val value = new FunctionReference("funName")
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[SetterView]
    val presenter = new SetterPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile()
    }
  }

}
