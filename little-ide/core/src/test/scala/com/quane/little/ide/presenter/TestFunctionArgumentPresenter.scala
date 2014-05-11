package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestFunctionArgumentPresenter extends FunSuite with MockitoSugar {

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
    val valuePresenter = mock[ValuePresenter[ValueView]]
    when[ValuePresenter[_]](view.createValueStatement()).thenReturn(valuePresenter)
    val value = mock[Value]
    presenter.value = value
    verify(view).createValueStatement()
    verify(valuePresenter).initialize(value)
  }

  test("test get expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[GetterPresenter[GetStatementView]]
    when[GetterPresenter[_]](view.createGetStatement()).thenReturn(valuePresenter)
    val getter = mock[GetStatement]
    presenter.value = getter
    verify(view).createGetStatement()
    verify(valuePresenter).initialize(getter)
  }

  test("test function reference expression propagates to view") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    val valuePresenter = mock[FunctionReferencePresenter[FunctionReferenceView]]
    when[FunctionReferencePresenter[_]](view.createFunctionReference()).thenReturn(valuePresenter)
    val function = mock[FunctionReference]
    presenter.value = function
    verify(view).createFunctionReference()
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
    when[GetterPresenter[_]](view.createGetStatement())
      .thenReturn(new GetterPresenter(new MockGetStatementView))
    when[ValuePresenter[_]](view.createValueStatement())
      .thenReturn(new ValuePresenter(new MockValueView))
    when[FunctionReferencePresenter[_]](view.createFunctionReference())
      .thenReturn(new FunctionReferencePresenter(new MockFunctionReferenceView))
    presenter.value = value
    val compiled = presenter.compile
    assert(compiled == value)
  }

  test("should error if compiled without expression") {
    val view = mock[FunctionArgumentView]
    val presenter = new FunctionArgumentPresenter(view)
    intercept[IllegalAccessException] {
      presenter.compile
    }
  }

}
