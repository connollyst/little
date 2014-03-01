package com.quane.little.ide.presenter

import com.quane.little.language.{Scope, FunctionReference, Runtime}
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view.{FunctionReferenceView, MockFunctionArgumentView, MockFunctionReferenceView}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value

@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends FunSuite with MockitoSugar {

  test("should set name") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  test("should initialize name") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    val function = new FunctionReference(mock[Scope], "funName")
    presenter.initialize(function)
    assert(presenter.name == "funName")
  }

  test("should initialize arguments") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    val function = new FunctionReference(mock[Scope], "funName")
      .addArg("a", new Value(42))
      .addArg("b", new Value("x"))
      .addArg("c", new Value(true))
    when[FunctionArgumentPresenter[_]](view.createArgument())
      .thenReturn(mock[FunctionArgumentPresenter[_]])
    presenter.initialize(function)
    verify(view, times(3)).createArgument()
  }

  /* Test View */

  test("test listener registered") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    verify(view).addViewListener(presenter)
  }

  test("test compiled scope") {
    val scope = new Runtime
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile(scope)
    assert(function.scope == scope)
  }

  test("test compiled name (default)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile(new Runtime)
    assert(function.name == "???", "expected '???' but got '" + function.name + "'")
  }

  test("test compiled name") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    presenter.name = "newName"
    val function = presenter.compile(new Runtime)
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val arg = new FunctionArgumentPresenter(new MockFunctionArgumentView).initialize("x", new Value("y"))
    presenter.add(arg)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val arg1 = new FunctionArgumentPresenter(new MockFunctionArgumentView).initialize("a", new Value("x"))
    val arg2 = new FunctionArgumentPresenter(new MockFunctionArgumentView).initialize("b", new Value("y"))
    presenter.add(arg1)
    presenter.add(arg2)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 2)
  }


}
