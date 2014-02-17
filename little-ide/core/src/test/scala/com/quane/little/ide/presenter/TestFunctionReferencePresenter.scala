package com.quane.little.ide.presenter

import com.quane.little.language.Runtime
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.quane.little.ide.view.{FunctionArgumentView, FunctionReferenceView}
import org.scalatest.mock.MockitoSugar


@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends FunSuite with MockitoSugar {

  test("test compiled scope") {
    val scope = new Runtime
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    val function = presenter.compile(scope)
    assert(function.scope == scope)
  }

  test("test compiled name (default)") {
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    val function = presenter.compile(new Runtime)
    assert(function.name == "name")
  }

  test("test compiled name") {
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    presenter.name = "newName"
    val function = presenter.compile(new Runtime)
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    val arg = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    arg.name = "x"
    presenter.add(arg)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionReferencePresenter(mock[FunctionReferenceView])
    val arg1 = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    val arg2 = new FunctionArgumentPresenter(mock[FunctionArgumentView])
    arg1.name = "x"
    arg2.name = "y"
    presenter.add(arg1)
    presenter.add(arg2)
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 2)
  }


}
