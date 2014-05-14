package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.FunctionParameter

@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = PresenterBindingModule

  test("test name is set") {
    val presenter = new FunctionDefinitionPresenter(mock[FunctionDefinitionView])
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    verify(view).registerViewPresenter(presenter)
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
    val paramView = mock[FunctionParameterView]
    val paramPresenter = mock[FunctionParameterPresenter[FunctionParameterView]]
    when(view.createFunctionParameter()).thenReturn(paramView)
    presenter += new FunctionParameter("sean is cool")
    verify(view).createFunctionParameter()
    // TODO test isn't applicable as presenter comes from factory
    verify(paramPresenter).name = "sean is cool"
  }

  test("test param is added on view event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.requestAddParameter()
    assert(presenter.parameters.length == 1)
    presenter.requestAddParameter()
    assert(presenter.parameters.length == 2)
  }

  test("test param is not propagated to view on view event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.requestAddParameter()
    verify(view, never()).createFunctionParameter()
  }

  test("test name is set on view event") {
    val view = mock[FunctionDefinitionView]
    val presenter = new FunctionDefinitionPresenter(view)
    presenter.onNameChange("sean is cool")
    assert(presenter.name == "sean is cool")
  }

  test("test name is not propagated to view on view event") {
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
    presenter.name = "new name"
    val function = presenter.compile()
    assert(function.name == "new name")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    val function = presenter.compile()
    assert(function.paramCount == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
    val function = presenter.compile()
    assert(function.paramCount == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
    presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
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
    presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile()
    assert(function.stepCount == 1)
  }

  test("test compiled with 2 steps") {
    val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
    presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
    presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile()
    assert(function.stepCount == 2)
  }

}
