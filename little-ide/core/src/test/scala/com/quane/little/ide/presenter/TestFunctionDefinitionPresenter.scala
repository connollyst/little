package com.quane.little.ide.presenter

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import com.quane.little.language.FunctionParameter

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends FunSuite with MockitoSugar {

  test("test name is set") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test name propagates to view") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.name = "sean is cool"
    verify(view).setName("sean is cool")
  }

  test("test param name propagates to view") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    val paramPresenter = mock[FunctionParameterPresenter[FunctionParameterView]]
    when[FunctionParameterPresenter[_]](view.createFunctionParameter())
      .thenReturn(paramPresenter)
    presenter.addParameter(new FunctionParameter("sean is cool"))
    verify(view).createFunctionParameter()
    verify(paramPresenter).name = "sean is cool"
  }

  test("test param is added on listen event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.onParamAdded(mock[FunctionParameterPresenter[_]])
    assert(presenter.parameters.length == 1)
    presenter.onParamAdded(mock[FunctionParameterPresenter[_]])
    assert(presenter.parameters.length == 2)
  }

  test("test param is not propagated to view on listen event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.onParamAdded(mock[FunctionParameterPresenter[_]])
    verify(view, never()).createFunctionParameter()
  }

  test("test name is set on listen event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.onNameChange("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on listen event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.onNameChange("sean is cool")
    verify(view, never()).setName("sean is cool")
  }

  /* Test Compilation */

  test("test compiled name (default)") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.name == "???", "expected '???' but got '" + function.name + "'")
  }

  test("test compiled name") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    presenter.name = "newName"
    val function = presenter.compile()
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.paramCount == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
    val function = presenter.compile()
    assert(function.paramCount == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
      .add(new FunctionParameterPresenter(new MockFunctionParameterView))
    val function = presenter.compile()
    assert(function.paramCount == 2)
  }

  test("test compiled with 0 steps") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.stepCount == 0)
  }

  test("test compiled with 1 step") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionReferencePresenter(new MockFunctionReferenceView))
    val function = presenter.compile()
    assert(function.stepCount == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      .add(new FunctionReferencePresenter(new MockFunctionReferenceView))
      .add(new FunctionReferencePresenter(new MockFunctionReferenceView))
    val function = presenter.compile()
    assert(function.stepCount == 2)
  }

}
