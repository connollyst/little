package com.quane.little.ide.presenter

import com.quane.little.ide.view.{MockFunctionArgumentView, MockFunctionReferenceView}
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.data.Value
import com.quane.little.language.FunctionReference
import com.quane.little.ide.MockIDEBindingModule
import org.mockito.Matchers._

/** Test cases for the [[com.quane.little.ide.presenter.FunctionReferencePresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionReferencePresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "FunctionReferencePresenter" should {
    "store name changes" in {
      val presenter = new FunctionReferencePresenter(MockFunctionReferenceView.mocked())
      presenter.name = "sean is cool"
      assert(presenter.name == "sean is cool")
    }

    /* Test View */

    "register itself with view immediately" in {
      val view = MockFunctionReferenceView.mocked()
      val presenter = new FunctionReferencePresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "set default name in its view immediately" in {
      val view = MockFunctionReferenceView.mocked()
      new FunctionReferencePresenter(view)
      verify(view).setName(anyString)
    }
    "initialize name in its view" in {
      val view = MockFunctionReferenceView.mocked()
      val presenter = new FunctionReferencePresenter(view)
      presenter.name = "abc"
      verify(view).setName("abc")
    }
    "initialize arguments in its view" in {
      val view = MockFunctionReferenceView.mocked()
      val presenter = new FunctionReferencePresenter(view)
      val function = new FunctionReference("funName")
        .addArg("a", Value(42))
        .addArg("b", Value("x"))
        .addArg("c", Value(true))
      presenter.initialize(function)
      verify(view, times(3)).createArgument()
    }

    /* Assert compiled function references.. */

    "compile with name (default)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
      val function = presenter.compile()
      assert(function.name == "", "expected '' but got '" + function.name + "'")
    }
    "compile with name" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
      presenter.name = "newName"
      val function = presenter.compile()
      assert(function.name == "newName")
    }
    "compile with 0 parameters (initialized)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
        .initialize(
          new FunctionReference("my name")
        )
      val function = presenter.compile()
      assert(function.args.size == 0)
    }
    "compile with 1 parameters (initialized)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
        .initialize(
          new FunctionReference("my name")
            .addArg("x", Value("y"))
        )
      val function = presenter.compile()
      assert(function.args.size == 1)
    }
    "compile with 2 parameters (initialized)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
        .initialize(
          new FunctionReference("my name")
            .addArg("a", Value("x"))
            .addArg("b", Value("y"))
        )
      val function = presenter.compile()
      assert(function.args.size == 2)
    }
    "compile with 0 parameters (added)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
      val function = presenter.compile()
      assert(function.args.size == 0)
    }
    "compile with 1 parameters (added)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
      presenter.add(
        new FunctionArgumentPresenter(new MockFunctionArgumentView)
          .initialize("x", Value("y"))
      )
      val function = presenter.compile()
      assert(function.args.size == 1)
    }
    "compile with 2 parameters (added)" in {
      val view = MockFunctionReferenceView()
      val presenter = new FunctionReferencePresenter(view)
      presenter.add(
        new FunctionArgumentPresenter(new MockFunctionArgumentView)
          .initialize("a", Value("x"))
      )
      presenter.add(
        new FunctionArgumentPresenter(new MockFunctionArgumentView)
          .initialize("b", Value("y"))
      )
      val function = presenter.compile()
      assert(function.args.size == 2)
    }
  }

}
