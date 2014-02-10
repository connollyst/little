package com.quane.little.ide.presenter

import com.quane.little.language.Runtime
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

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


}
