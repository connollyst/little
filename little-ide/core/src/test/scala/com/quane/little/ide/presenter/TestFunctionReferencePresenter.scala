package com.quane.little.ide.presenter

import com.quane.little.language.Runtime
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view.{FunctionReferenceView, MockFunctionArgumentView, MockFunctionReferenceView}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar


@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends FunSuite with MockitoSugar {

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
    val arg = new FunctionArgumentPresenter(new MockFunctionArgumentView)
    arg.name = "x"
    presenter.add(arg)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val arg1 = new FunctionArgumentPresenter(new MockFunctionArgumentView)
    val arg2 = new FunctionArgumentPresenter(new MockFunctionArgumentView)
    arg1.name = "x"
    arg2.name = "y"
    presenter.add(arg1)
    presenter.add(arg2)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 2)
  }


}
