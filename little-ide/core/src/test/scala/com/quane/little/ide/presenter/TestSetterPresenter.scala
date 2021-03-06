package com.quane.little.ide.presenter

import com.escalatesoft.subcut.inject.Injectable
import com.quane.little.data.model.CodeCategory
import com.quane.little.data.service.{FunctionService, UserService}
import com.quane.little.ide.MockIDEBindingModule
import com.quane.little.ide.view._
import com.quane.little.language._
import com.quane.little.language.data.Value
import com.quane.little.language.math.{BasicMathOperation, Division, Multiplication}
import org.junit.runner.RunWith
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfter, WordSpec}

/** Test cases for the [[com.quane.little.ide.presenter.SetterPresenter]].
  *
  * @author Sean Connolly
  */
@RunWith(classOf[JUnitRunner])
class TestSetterPresenter
  extends WordSpec with BeforeAndAfter with ShouldMatchers with MockitoSugar with Injectable {

  implicit val bindingModule = MockIDEBindingModule

  before({
    val mockUserService = inject[UserService]
    val mockFunctionService = inject[FunctionService]
    mockUserService.upsert("connollyst")
    mockFunctionService.insert("connollyst", CodeCategory.Basic, new FunctionDefinition("TestFunction"))
  })


  "SetterPresenter" should {
    "store name changes" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.name = "sean is cool"
      assert(presenter.name == "sean is cool")
    }

    /* Test View */

    "register itself with view immediately" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      verify(view).registerViewPresenter(presenter)
    }
    "set default name in its view immediately" in {
      val view = mockSetterView
      new SetterPresenter(view)
      verify(view).setName(anyString)
    }
    "initialize name in its view" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.name = "abc"
      verify(view).setName("abc")
    }
    "propagate name change to its view" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.name = "sean is cool"
      verify(view).setName("sean is cool")
    }
    "update name when view is modified" in {
      val presenter = new SetterPresenter(mockSetterView)
      presenter.onNameChange("sean is cool")
      assert(presenter.name == "sean is cool")
    }
    "not propagate name change to view when view is modified" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.onNameChange("sean is cool")
      verify(view, never()).setName("sean is cool")
    }

    /* Test setting expressions for the value */

    "error when adding unknown expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val error = intercept[IllegalArgumentException] {
        presenter.value = mock[Code]
      }
      error.getMessage should startWith("Not supported")
    }

    /* Assert views are created for new expression values.. */

    "create a view for a new Value expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = Value("abc")
      verify(view).createValueView()
    }
    "create a view for a new Getter expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Getter("x")
      verify(view).createGetterView()
    }
    "create a view for a new Logic expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Logic(Value("x"), LogicOperation.GreaterThan, Value("y"))
      verify(view).createLogicView()
    }
    "create a view for a new Math expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new Division(Value(137), Value(42))
      verify(view).createMathView()
    }
    "create a view for a new FunctionReference expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      presenter.value = new FunctionReference("TestFunction")
      verify(view).createFunctionReferenceView()
    }

    /* Assert views are initialized for new expression values.. */

    "initialize the view for a new Value expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[ValueView]
      when(view.createValueView()).thenReturn(valueView)
      presenter.value = Value("abc")
      verify(valueView).setValue("abc")
    }
    "initialize the view for a new Getter expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[GetterView]
      when(view.createGetterView()).thenReturn(valueView)
      presenter.value = new Getter("x")
      verify(valueView).setName("x")
    }
    "initialize the view for a new Logic expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[LogicView]
      when(view.createLogicView()).thenReturn(valueView)
      when(valueView.createLeftLiteral()).thenReturn(new MockValueView)
      when(valueView.createRightLiteral()).thenReturn(new MockValueView)
      presenter.value = new Logic(Value("x"), LogicOperation.GreaterThan, Value("y"))
      verify(valueView).createLeftLiteral()
      verify(valueView).setOperation(LogicOperation.GreaterThan)
      verify(valueView).createRightLiteral()
    }
    "initialize the view for a new Math expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[MathView]
      when(view.createMathView()).thenReturn(valueView)
      when(valueView.createLeftLiteral()).thenReturn(new MockValueView)
      when(valueView.createRightLiteral()).thenReturn(new MockValueView)
      presenter.value = new Multiplication(Value(137), Value(42))
      verify(valueView).createLeftLiteral()
      verify(valueView).setOperation(BasicMathOperation.Multiply)
      verify(valueView).createRightLiteral()
    }
    "initialize the view for a new FunctionReference expression" in {
      val view = mockSetterView
      val presenter = new SetterPresenter(view)
      val valueView = mock[FunctionReferenceView]
      when(view.createFunctionReferenceView()).thenReturn(valueView)
      presenter.value = new FunctionReference("TestFunction")
      verify(valueView).setName("TestFunction")
    }

    /* Assert compilation values.. */

    "compile with correct name" in {
      val presenter = new SetterPresenter(mockSetterView)
      presenter.name = "abc"
      presenter.value = Value("xyz")
      val compiled = presenter.compile()
      assert(compiled.name == "abc")
    }
    "compile with Value expression" in {
      assertCompiledValue(Value("text"))
    }
    "compile with Getter expression" in {
      assertCompiledValue(new Getter("TestValue"))
    }
    "compile with FunctionReference expression" in {
      assertCompiledValue(new FunctionReference("TestFunction"))
    }
    "error if compiled without expression" in {
      val presenter = new SetterPresenter(mockSetterView)
      val error = intercept[IllegalAccessException] {
        presenter.compile()
      }
      error.getMessage should be("No value expression set.")
    }

  }

  private def assertCompiledValue(value: Code) = {
    val presenter = new SetterPresenter(mockSetterView)
    presenter.value = value
    val compiled = presenter.compile()
    assert(compiled.value == value)
  }

  /** Utility function for mocking out a [[com.quane.little.ide.view.FunctionArgumentView]]
    * which returns instances of the appropriate [[com.quane.little.ide.view.MockView]]
    * when asked to.
    *
    * @return the mock conditional view
    */
  private def mockSetterView: SetterView = {
    val view = mock[SetterView]
    when(view.createMathView()).thenReturn(new MockMathView)
    when(view.createLogicView()).thenReturn(new MockLogicView)
    when(view.createGetterView()).thenReturn(new MockGetterView)
    when(view.createValueView()).thenReturn(new MockValueView)
    when(view.createFunctionReferenceView()).thenReturn(new MockFunctionReferenceView)
    view
  }


}
