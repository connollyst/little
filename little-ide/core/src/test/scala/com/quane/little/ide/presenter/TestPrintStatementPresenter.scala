package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.{FunctionReference, Getter, Expression}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value
import com.quane.little.ide.MockIDEBindingModule

@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  test("test value is set") {
    val view = new MockPrinterView
    val presenter = new PrinterPresenter(view)
    presenter.value = Value("text")
    val valuePresenter = presenter.value.asInstanceOf[ValuePresenter[_ <: ValueView]]
    assert(valuePresenter.value == "text")
  }

  /* Test View interaction */

  test("test listener registered") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("test value expression propagates to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
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
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val getterView = mock[GetterView]
    val getterPresenter = mock[GetterPresenter[GetterView]]
    when(view.createGetStatement()).thenReturn(getterView)
    val getter = mock[Getter]
    presenter.value = getter
    verify(view).createGetStatement()
    // TODO test isn't applicable as presenter comes from factory
    verify(getterPresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
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
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    intercept[IllegalArgumentException] {
      presenter.value = mock[Expression]
    }
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
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    when(view.createGetStatement()).thenReturn(new MockGetterView)
    when(view.createValueStatement()).thenReturn(new MockValueView)
    when(view.createFunctionReference()).thenReturn(new MockFunctionReferenceView)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  test("should error if compiled without expression") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile()
    }
  }

}
