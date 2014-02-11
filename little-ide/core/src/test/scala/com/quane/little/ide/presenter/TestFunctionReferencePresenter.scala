package com.quane.little.ide.presenter

import com.quane.little.language.Runtime
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite


@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends FunSuite {

  test("test compiled scope") {
    val scope = new Runtime
    val presenter = new FunctionReferencePresenter
    val function = presenter.compile(scope)
    assert(function.scope == scope)
  }

  test("test compiled name (default)") {
    val presenter = new FunctionReferencePresenter
    val function = presenter.compile(new Runtime)
    assert(function.name == "name")
  }

  test("test compiled name") {
    val presenter = new FunctionReferencePresenter
    presenter.name = "newName"
    val function = presenter.compile(new Runtime)
    assert(function.name == "newName")
  }

  test("test compiled with 0 parameters") {
    val presenter = new FunctionReferencePresenter
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 0)
  }

  test("test compiled with 1 parameters") {
    val presenter = new FunctionReferencePresenter()
      .addArg(new FunctionArgumentPresenter("x"))
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 1)
  }

  test("test compiled with 2 parameters") {
    val presenter = new FunctionReferencePresenter()
      .addArg(new FunctionArgumentPresenter("x"))
      .addArg(new FunctionArgumentPresenter("y"))
    val function = presenter.compile(new Runtime)
    assert(function.args.size == 2)
  }


}
