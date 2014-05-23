package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language.FunctionReference
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value
import com.quane.little.ide.MockIDEBindingModule
import com.escalatesoft.subcut.inject.Injectable
import org.scalatest.matchers.ShouldMatchers

/** Tests for the [[com.quane.little.ide.presenter.PrinterPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestPrintStatementPresenter
  extends FunSuite
  with ShouldMatchers
  with MockitoSugar
  with Injectable {

  implicit val bindingModule = MockIDEBindingModule

  test("test value is set") {
    val view = new MockPrinterView
    val presenter = new PrinterPresenter(view)
    presenter.value = Value("abc")
    val valuePresenter = presenter.value.asInstanceOf[ValuePresenter]
    assert(valuePresenter.value == "abc")
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
    val expectedValueView = mock[ValueView]
    when(view.createValueStatement()).thenReturn(expectedValueView)
    presenter.value = Value("abc")
    verify(view).createValueStatement()
  }

  test("test value expression presenter wired to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val expectedValueView = mock[ValueView]
    when(view.createValueStatement()).thenReturn(expectedValueView)
    presenter.value = Value("abc")
    presenter.value.getClass should be(classOf[ValuePresenter])
    val valuePresenter = presenter.value.asInstanceOf[ValuePresenter]
    valuePresenter.view should be(expectedValueView)
  }

  test("test get expression propagates to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val getterView = mock[GetterView]
    when(view.createGetStatement()).thenReturn(getterView)
    val getter = new Getter("TestValue")
    presenter.value = getter
    verify(view).createGetStatement()
  }

  test("test get expression presenter wired to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val getterView = mock[GetterView]
    when(view.createGetStatement()).thenReturn(getterView)
    presenter.value = new Getter("TestValue")
    presenter.value.getClass should be(classOf[GetterPresenter])
    val getterPresenter = presenter.value.asInstanceOf[GetterPresenter]
    getterPresenter.view should be(getterView)
  }

  test("test function reference expression propagates to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val functionView = mock[FunctionReferenceView]
    when(view.createFunctionReference()).thenReturn(functionView)
    val function = new FunctionReference("TestFunction")
    presenter.value = function
    verify(view).createFunctionReference()
  }

  test("test function reference expression presenter wired to view") {
    val view = mock[PrinterView]
    val presenter = new PrinterPresenter(view)
    val functionView = mock[FunctionReferenceView]
    when(view.createFunctionReference()).thenReturn(functionView)
    presenter.value = new FunctionReference("TestFunction")
    presenter.value.getClass should be(classOf[FunctionReferencePresenter])
    val functionPresenter = presenter.value.asInstanceOf[FunctionReferencePresenter]
    functionPresenter.view should be(functionView)
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
