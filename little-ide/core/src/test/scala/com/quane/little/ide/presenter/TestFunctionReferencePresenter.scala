package com.quane.little.ide.presenter

import com.quane.little.ide.view.{FunctionReferenceView, MockFunctionArgumentView, MockFunctionReferenceView}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar

@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends FunSuite with MockitoSugar {

  implicit val bindingModule = PresenterInjector

  test("should set name") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    presenter.name = "sean is cool"
    assert(presenter.name == "sean is cool")
  }

  test("should initialize name") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    val function = new FunctionReference("funName")
    presenter.initialize(function)
    assert(presenter.name == "funName")
  }

  test("should initialize arguments") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    val function = new FunctionReference("funName")
      .addArg("a", Value(42))
      .addArg("b", Value("x"))
      .addArg("c", Value(true))
    when(view.createArgument()).thenReturn(mock[MockFunctionArgumentView])
    presenter.initialize(function)
    verify(view, times(3)).createArgument()
  }

  /* Test View */

  test("should register self as view listener") {
    val view = mock[FunctionReferenceView]
    val presenter = new FunctionReferencePresenter(view)
    verify(view).registerViewPresenter(presenter)
  }

  test("should compile with name (default)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile
    assert(function.name == "???", "expected '???' but got '" + function.name + "'")
  }

  test("should compile with name") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    presenter.name = "newName"
    val function = presenter.compile
    assert(function.name == "newName")
  }


  test("should compile with 0 parameters (initialized)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
      .initialize(
        new FunctionReference("my name")
      )
    val function = presenter.compile
    assert(function.args.size == 0)
  }

  test("should compile with 1 parameters (initialized)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
      .initialize(
        new FunctionReference("my name")
          .addArg("x", Value("y"))
      )
    val function = presenter.compile
    assert(function.args.size == 1)
  }

  test("should compile with 2 parameters (initialized)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
      .initialize(
        new FunctionReference("my name")
          .addArg("a", Value("x"))
          .addArg("b", Value("y"))
      )
    val function = presenter.compile
    assert(function.args.size == 2)
  }

  test("should compile with 0 parameters (added)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    val function = presenter.compile
    assert(function.args.size == 0)
  }

  test("should compile with 1 parameters (added)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    presenter.add(
      new FunctionArgumentPresenter(new MockFunctionArgumentView)
        .initialize("x", Value("y"))
    )
    val function = presenter.compile
    assert(function.args.size == 1)
  }

  test("should compile with 2 parameters (added)") {
    val presenter = new FunctionReferencePresenter(new MockFunctionReferenceView)
    presenter.add(
      new FunctionArgumentPresenter(new MockFunctionArgumentView)
        .initialize("a", Value("x"))
    )
    presenter.add(
      new FunctionArgumentPresenter(new MockFunctionArgumentView)
        .initialize("b", Value("y"))
    )
    val function = presenter.compile
    assert(function.args.size == 2)
  }

}
