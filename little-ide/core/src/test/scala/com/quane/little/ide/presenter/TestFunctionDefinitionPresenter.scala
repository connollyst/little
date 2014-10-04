package com.quane.little.ide.presenter

import com.quane.little.ide.view._
import org.junit.runner.RunWith
import org.mockito.Mockito._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import com.quane.little.language.FunctionParameter
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.language.data.ValueType

/** Test cases for the [[com.quane.little.ide.presenter.FunctionDefinitionPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestFunctionDefinitionPresenter extends WordSpec with MockitoSugar {

  implicit val bindingModule = MockIDEBindingModule

  "FunctionDefinitionPresenter" should {
    "store name changes" in {
      val view = new MockFunctionDefinitionView
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.name = "sean is cool"
      assert(presenter.name == "sean is cool")
    }

    /* Test View */

    "register itself with view immediately" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "propagate name change to its view" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.name = "sean is cool"
      verify(view).setName("sean is cool")
    }
    "create new parameter view when requested" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.requestAddParameter()
      verify(view).createFunctionParameter()
    }
    "propagate parameter name change to its view" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      val paramView = mock[FunctionParameterView]
      when(view.createFunctionParameter()).thenReturn(paramView)
      presenter += new FunctionParameter("sean is cool", ValueType.String)
      verify(paramView).setName("sean is cool")
    }
    "add new parameter presenter when requested" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.requestAddParameter()
      assert(presenter.parameters.length == 1)
      presenter.requestAddParameter()
      assert(presenter.parameters.length == 2)
    }
    "test name is set on view event" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.onNameChange("sean is cool")
      assert(presenter.name == "sean is cool")
    }
    "test name is not propagated to view on view event" in {
      val view = MockFunctionDefinitionView.mocked()
      val presenter = new FunctionDefinitionPresenter(view)
      presenter.onNameChange("sean is cool")
      verify(view, never()).setName("sean is cool")
    }

    /* Test Compilation */

    "compile name (default)" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      val function = presenter.compile()
      assert(function.name == "", "expected '' but got '" + function.name + "'")
    }
    "compile name" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      presenter.name = "new name"
      val function = presenter.compile()
      assert(function.name == "new name")
    }
    "compile with 0 parameters" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      val function = presenter.compile()
      assert(function.paramCount == 0)
    }
    "compile with 1 parameters" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
      val function = presenter.compile()
      assert(function.paramCount == 1)
    }
    "compile with 2 parameters" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
      presenter += new FunctionParameterPresenter(new MockFunctionParameterView)
      val function = presenter.compile()
      assert(function.paramCount == 2)
    }
    "compile with 0 steps" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      val function = presenter.compile()
      assert(function.stepCount == 0)
    }
    "compile with 1 step" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
      val function = presenter.compile()
      assert(function.stepCount == 1)
    }
    "compile with 2 steps" in {
      val presenter = new FunctionDefinitionPresenter(new MockFunctionDefinitionView)
      presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
      presenter += new FunctionReferencePresenter(new MockFunctionReferenceView)
      val function = presenter.compile()
      assert(function.stepCount == 2)
    }
  }

}
